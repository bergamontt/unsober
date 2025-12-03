import {Button, Group, MultiSelect, NativeSelect, Select, Stack, TextInput} from "@mantine/core";
import {useCallback, useState} from "react";
import {useTranslation} from "react-i18next";
import {notifications} from "@mantine/notifications";
import axios from "axios";
import {modals} from "@mantine/modals";
import {ClassType, type CourseClass, type CourseClassDto, WeekDay} from "../../../models/CourseClass";
import type {Building} from "../../../models/Building";
import type {Teacher} from "../../../models/Teacher";
import {addCourseClass, updateCourseClass} from "../../../services/CourseClassService";
import {getAllTeachers} from "../../../services/TeacherService";
import useFetch from "../../../hooks/useFetch";
import {getAllBuildings} from "../../../services/BuildingService";
import {validateCourseClassDto} from "../../../validation/courseClassValidator";

type ClassModalProps = {
    groupIds: string[];
    isLecture: boolean;
    courseTitle: string;
    courseClass: CourseClass | null;
}

function ClassModal({groupIds, isLecture, courseTitle, courseClass}: ClassModalProps) {
    const {t} = useTranslation(["coursePanel", "schedule"]);

    const [title, setTitle] = useState<string | undefined>(courseClass ? courseClass.title : courseTitle);
    const [classType, setClassType] = useState<ClassType | undefined>(isLecture ? ClassType.LECTURE : (courseClass ? courseClass.type : ClassType.PRACTICE));
    const [weeksList, setWeeksList] = useState<number[] | undefined>(courseClass ? courseClass.weeksList : undefined);
    const [weekDay, setWeekDay] = useState<WeekDay | undefined>(courseClass ? courseClass.weekDay : WeekDay.MONDAY);
    const [classNumber, setClassNumber] = useState<number | undefined>(courseClass ? courseClass.classNumber : 1);
    const [location, setLocation] = useState<string | undefined>(courseClass ? courseClass.location : undefined);
    const [building, setBuilding] = useState<string | undefined>(courseClass?.building?.id ?? undefined);
    const [teacher, setTeacher] = useState<string | undefined>(courseClass?.teacher?.id ?? undefined);

    const {data: teachers} = useFetch(getAllTeachers, []);
    const {data: buildings} = useFetch(getAllBuildings, []);

    const [isAdding, setIsAdding] = useState(false);

    const handleSubmit = useCallback(async () => {
        let dtos: CourseClassDto[];
        if (isLecture) {
            dtos = groupIds.map(id => {
                const dto: CourseClassDto = {
                    groupId: id,
                    title: title,
                    type: classType,
                    weeksList: weeksList,
                    weekDay: weekDay,
                    classNumber: classNumber,
                    location: location,
                    buildingId: building,
                    teacherId: teacher
                }
                return dto;
            });
        } else {
            dtos = [{
                groupId: groupIds[0],
                title: title,
                type: classType,
                weeksList: weeksList,
                weekDay: weekDay,
                classNumber: classNumber,
                location: location,
                buildingId: building,
                teacherId: teacher
            }];
        }

        const errs = validateCourseClassDto(dtos[0], (k, p) => t(k, p));
        if (errs.length > 0) {
            notifications.show({
                title: t("adminMenu:error"),
                message: errs.join('\n'),
                color: "red",
            });
            return;
        }

        setIsAdding(true);
        try {
            if (isLecture) {
                for (const dto of dtos) {
                    if (courseClass) {
                        await updateCourseClass(courseClass.id, dto);
                    } else {
                        await addCourseClass(dto);
                    }
                }
            } else {
                if (courseClass) {
                    await updateCourseClass(courseClass.id, dtos[0]);
                } else {
                    await addCourseClass(dtos[0]);
                }
            }

            notifications.show({
                title: t("success"),
                message: courseClass ? t("courseEdited") : t("courseAdded"),
                color: "green",
            });
            modals.closeAll();
        } catch (err: unknown) {
            let errorMessage = t("unknownAddError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim()) {
                    errorMessage = data;
                }
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: "red",
            });
        } finally {
            setIsAdding(false);
        }
    }, [isLecture, groupIds, title, classType, weeksList, weekDay, classNumber, location, building, teacher, t, courseClass]);

    return (
        <Stack p="xs" pt="0">
            <TextInput
                label={t("titleLabel")}
                placeholder={t("titlePlaceholder")}
                withAsterisk
                value={title}
                onChange={(e) => {
                    setTitle(e.currentTarget.value);
                }}
            />
            <Group justify={"space-between"}>
                <NativeSelect flex={1}
                              label={t("classTypeLabel")}
                              withAsterisk
                              data={getClassTypes(isLecture, t)}
                              value={classType}
                              disabled={isLecture}
                              onChange={(e) => {
                                  setClassType(e.currentTarget.value as ClassType);
                              }}
                />
                {
                    teachers &&
                    <Select flex={1}
                            searchable clearable
                            label={t("teacherLabel")}
                            placeholder={t("teacherPlaceholder")}
                            data={teachers.map((teacher: Teacher) => {
                                return {
                                    label: `${teacher.lastName} ${teacher.firstName.charAt(0)}. ${teacher.patronymic.charAt(0)}.`,
                                    value: teacher.id
                                };
                            })}
                            value={teacher}
                            onChange={(e) => {
                                setTeacher(e ?? undefined);
                            }}
                    />
                }
            </Group>
            <Group justify={"space-between"}>
                <NativeSelect flex={1}
                              label={t("weekDayLabel")}
                              withAsterisk
                              data={[WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY,
                                  WeekDay.THURSDAY, WeekDay.FRIDAY, WeekDay.SATURDAY, WeekDay.SUNDAY].map(d => {
                                  return {label: t("schedule:" + d.toLowerCase()), value: d}
                              })}
                              value={weekDay}
                              onChange={(e) => {
                                  setWeekDay(e.currentTarget.value as WeekDay);
                              }}
                />
                <NativeSelect flex={1}
                              label={t("classNumberLabel")}
                              withAsterisk
                              data={[
                                  {label: "8:30 - 9:50", value: "1"},
                                  {label: "10:00 - 11:20", value: "2"},
                                  {label: "11:40 - 13:00", value: "3"},
                                  {label: "13:30 - 14:50", value: "4"},
                                  {label: "15:00 - 16:20", value: "5"},
                                  {label: "16:30 - 17:50", value: "6"},
                                  {label: "18:00 - 19:20", value: "7"}
                              ]}
                              value={classNumber}
                              onChange={(e) => {
                                  setClassNumber(Number(e.currentTarget.value));
                              }}
                />
            </Group>
            <Group justify={"space-between"}>
                <TextInput flex={1}
                           label={t("locationLabel")}
                           placeholder={t("locationPlaceholder")}
                           withAsterisk
                           value={location}
                           onChange={(e) => {
                               setLocation(e.currentTarget.value);
                           }}
                />
                {
                    buildings &&
                    <Select flex={1}
                            searchable clearable
                            label={t("buildingLabel")}
                            placeholder={t("buildingPlaceholder")}
                            data={buildings.map((building: Building) => {
                                return {
                                    label: `${building.name}`,
                                    value: building.id
                                };
                            })}
                            value={building}
                            onChange={(e) => {
                                setBuilding(e ?? undefined);
                            }}
                    />
                }
            </Group>
            <MultiSelect withAsterisk
                label={t("weeksListLabel")}
                data={["1","2","3","4","5","6","7","8","9","10","11","12","13","14"]}
                value={weeksList ? weeksList.map((w) => w.toString()) : undefined}
                onChange={(e) => {setWeeksList(e.map(w => Number(w)).sort())}}
            />

            <Button
                variant="filled"
                color="green"
                onClick={handleSubmit}
                loading={isAdding}
                disabled={isAdding}
            >
                {courseClass ? t("editClass") : t("addClass")}
            </Button>
        </Stack>
    );
}

export default ClassModal;

function getClassTypes(isLecture: boolean, t: any) {
    if (isLecture) {
        return [{label: t("lecture"), value: ClassType.LECTURE}];
    } else {
        return [ClassType.PRACTICE, ClassType.SEMINAR, ClassType.CONSULTATION, ClassType.CREDIT, ClassType.EXAM].map(ct => {
            return {label: t("schedule:" + ct.toLowerCase()), value: ct}
        });
    }
}
