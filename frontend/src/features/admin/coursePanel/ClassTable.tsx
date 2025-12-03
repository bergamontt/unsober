import type {CourseClass} from "../../../models/CourseClass.ts";
import type {CourseGroup} from "../../../models/CourseGroup.ts";
import {ActionIcon, Button, Group, Stack, Table, Tooltip, Text} from "@mantine/core";
import {useTranslation} from "react-i18next";
import RowActions from "../RowActions.tsx";
import Icon from "../../../common/icon/Icon.tsx";
import plus from "../../../assets/plus_black.svg";
import editIcon from "../../../assets/edit.svg";
import delIcon from "../../../assets/delete.svg";
import type {Course} from "../../../models/Course.ts";
import {modals} from "@mantine/modals";
import GroupModal from "./GroupModal";
import {deleteCourseGroup} from "../../../services/CourseGroupService";
import {notifications} from "@mantine/notifications";
import ClassModal from "./ClassModal";
import {deleteCourseClass} from "../../../services/CourseClassService";

interface ClassTableProps {
    classes: CourseClass[];
    group?: CourseGroup;
    course: Course;
    groupNums?: number[];
    groupIds?: string[];
}

function ClassTable({
                        classes,
                        group = undefined,
                        course,
                        groupNums = undefined,
                        groupIds = undefined
                    }: ClassTableProps) {
    const {t} = useTranslation(["coursePanel", "schedule", "adminMenu"]);

    if (group) {
        return (
            <Stack gap={0}>
                <Group align={"center"} justify={"start"} fw={600} mb={5} gap={"xs"}>
                    {`${t("group")} ${group.groupNumber} (${group.numEnrolled}/${group.maxStudents})`}
                    <Tooltip label={t("adminMenu:edit")}>
                        <ActionIcon
                            variant="light"
                            color="indigo"
                            onClick={() => {
                                openGroupModal(course.id, groupNums!, group, t);
                            }}
                        >
                            <Icon src={editIcon}/>
                        </ActionIcon>
                    </Tooltip>
                    <Tooltip label={t("adminMenu:delete")}>
                        <ActionIcon
                            variant="light"
                            color="red"
                            onClick={() => confirmGroupDelete(group.id, t)}
                        >
                            <Icon src={delIcon}/>
                        </ActionIcon>
                    </Tooltip>
                </Group>
                {
                    classes.filter(c => c.group.id === group.id && c.type !== "LECTURE").length > 0 &&
                    <Table withTableBorder withRowBorders captionSide="top" key={group.id}>
                        <Table.Thead>
                            <Table.Tr>
                                <Table.Th>{t("type")}</Table.Th>
                                <Table.Th>{t("teacher")}</Table.Th>
                                <Table.Th>{t("weekday")}</Table.Th>
                                <Table.Th>{t("time")}</Table.Th>
                                <Table.Th>{t("weeks")}</Table.Th>
                                <Table.Th>{t("location")}</Table.Th>
                                <Table.Th>{t("building")}</Table.Th>
                                <Table.Th></Table.Th>
                            </Table.Tr>
                        </Table.Thead>
                        <Table.Tbody>
                            {
                                classes
                                    .filter(c => c.group.id === group.id && c.type !== "LECTURE")
                                    .map((c) => (
                                        <Table.Tr key={c.id}>
                                            <Table.Td>{t(`schedule:${c.type.toLowerCase()}`)}</Table.Td>
                                            <Table.Td>{c.teacher ? `${c.teacher.lastName} ${c.teacher.firstName.charAt(0)}. ${c.teacher.patronymic.charAt(0)}.` : "-"}</Table.Td>
                                            <Table.Td>{t(`schedule:${c.weekDay.toLowerCase()}`)}</Table.Td>
                                            <Table.Td>{toTime(c.classNumber)}</Table.Td>
                                            <Table.Td>{toWeeksList(c.weeksList)}</Table.Td>
                                            <Table.Td>{c.location}</Table.Td>
                                            <Table.Td>{c?.building?.name ?? "-"}</Table.Td>
                                            <Table.Td>
                                                <RowActions
                                                    onEdit={() => {
                                                        openClassModal([group.id], false, course.subject.name, c, t)
                                                    }}
                                                    onDelete={() => {
                                                        confirmClassDelete(c.id, t);
                                                    }}
                                                    editLabel={t("adminMenu:edit")}
                                                    deleteLabel={t("adminMenu:delete")}
                                                />
                                            </Table.Td>
                                        </Table.Tr>
                                    ))
                            }
                        </Table.Tbody>
                    </Table>
                }

                <Button bd={"1px dashed #DEE2E6"} leftSection={<Icon src={plus} size={"20px"}/>} color={"black"}
                        radius={0} variant={"outline"} fullWidth onClick={() =>
                    openClassModal([group.id], false, course.subject.name, null, t)
                }>
                    {t("addClass")}
                </Button>
            </Stack>
        )
    } else {
        return (
            <Stack gap={0}>
                <Group align={"center"} justify={"start"} fw={600} mb={5} gap={"xs"}>
                    {`${t("all")} (${getEnrollmentNums(course, t)})`}
                    <Tooltip label={t("adminMenu:edit")}>
                        <ActionIcon
                            variant="light"
                            color="indigo"
                            onClick={() => {
                                // openGroupModal(course.id, groupNums!, group, t);
                            }}
                        >
                            <Icon src={editIcon}/>
                        </ActionIcon>
                    </Tooltip>
                    <Tooltip label={t("adminMenu:delete")}>
                        <ActionIcon
                            variant="light"
                            color="red"
                            // onClick={() => confirmGroupDelete(group.id, t)}
                        >
                            <Icon src={delIcon}/>
                        </ActionIcon>
                    </Tooltip>
                </Group>
                {
                    classes.filter(c => c.type === "LECTURE").length > 0 &&
                    <Table withTableBorder withRowBorders captionSide="top" key={"i_love_gays"}>
                        <Table.Thead>
                            <Table.Tr>
                                <Table.Th>{t("type")}</Table.Th>
                                <Table.Th>{t("teacher")}</Table.Th>
                                <Table.Th>{t("weekday")}</Table.Th>
                                <Table.Th>{t("time")}</Table.Th>
                                <Table.Th>{t("weeks")}</Table.Th>
                                <Table.Th>{t("location")}</Table.Th>
                                <Table.Th>{t("building")}</Table.Th>
                                <Table.Th></Table.Th>
                            </Table.Tr>
                        </Table.Thead>
                        <Table.Tbody>
                            {
                                groupIds &&
                                classes
                                    .map((c) => (
                                        <Table.Tr key={c.id}>
                                            <Table.Td>{t(`schedule:${c.type.toLowerCase()}`)}</Table.Td>
                                            <Table.Td>{c?.teacher?.firstName ?? "-"}</Table.Td>
                                            <Table.Td>{t(`schedule:${c.weekDay.toLowerCase()}`)}</Table.Td>
                                            <Table.Td>{toTime(c.classNumber)}</Table.Td>
                                            <Table.Td>{toWeeksList(c.weeksList)}</Table.Td>
                                            <Table.Td>{c.location}</Table.Td>
                                            <Table.Td>{c?.building?.name ?? "-"}</Table.Td>
                                            <Table.Td>
                                                <RowActions
                                                    onEdit={() => {
                                                        openClassModal(groupIds, true, course.subject.name, c, t)
                                                    }}
                                                    onDelete={() => {
                                                        confirmClassDelete(c.id, t);
                                                    }}
                                                    editLabel={t("adminMenu:edit")}
                                                    deleteLabel={t("adminMenu:delete")}
                                                />
                                            </Table.Td>
                                        </Table.Tr>
                                    ))
                            }
                        </Table.Tbody>
                    </Table>
                }
                {
                    groupIds &&
                    <Button bd={"1px dashed #DEE2E6"} leftSection={<Icon src={plus} size={"20px"}/>} color={"black"}
                            radius={0} variant={"outline"} fullWidth onClick={() =>
                        openClassModal(groupIds, true, course.subject.name, null, t)
                    }>
                        {t("addClass")}
                    </Button>
                }
            </Stack>
        )
    }
}

export default ClassTable

function toWeeksList(weeksList: number[]) {
    if (weeksList.length === 0) return "";

    const result: string[] = [];
    let start = weeksList[0];
    let prev = weeksList[0];
    for (let i = 1; i < weeksList.length; i++) {
        const current = weeksList[i];
        if (current !== prev + 1) {
            if (start === prev) {
                result.push(String(start));
            } else {
                result.push(`${start}-${prev}`);
            }
            start = current;
        }
        prev = current;
    }
    if (start === prev) {
        result.push(String(start));
    } else {
        result.push(`${start}-${prev}`);
    }
    return result.join(",");
}

function toTime(classNumber: number) {
    switch (classNumber) {
        case 1:
            return "08:30 - 09:50";
        case 2:
            return "10:00 - 11:20";
        case 3:
            return "11:40 - 13:00";
        case 4:
            return "13:30 - 14:50";
        case 5:
            return "15:00 - 16:20";
        case 6:
            return "16:30 - 17:50";
        case 7:
            return "18:00 - 19:20";
    }
}

function openGroupModal(courseId: string, groupNums: number[], group: CourseGroup | null, t: any) {
    modals.open({
        title: group ? t("editGroup") : t("addGroup"),
        centered: true,
        onClose: () => {
            //TODO refresh
        },
        children: <GroupModal courseId={courseId} groupNums={groupNums} group={group}/>
    })
}

function openClassModal(groupIds: string[], isLecture: boolean, courseTitle: string, courseClass: CourseClass | null, t: any) {
    modals.open({
        title: courseClass ? t("editClass") : t("addClass"),
        centered: true,
        onClose: () => {
            //TODO refresh
        },
        children: <ClassModal groupIds={groupIds} isLecture={isLecture} courseTitle={courseTitle}
                              courseClass={courseClass}/>
    })
}

function confirmGroupDelete(groupId: string, t: any) {
    modals.openConfirmModal({
        title: t("deleteGroupTitle"),
        centered: true,
        children: (
            <Text>
                {t("deleteGroupBody")}
            </Text>
        ),
        labels: {confirm: `${t("adminMenu:delete")}`, cancel: `${t("adminMenu:cancel")}`},
        confirmProps: {color: 'red'},
        onCancel: () => {
        },
        onConfirm: () => {
            deleteCourseGroup(groupId)
                .then(() =>
                    notifications.show({
                        title: t("adminMenu:success"),
                        message: t("groupDeleted"),
                        color: 'green',
                    })
                )
                .catch(err =>
                    notifications.show({
                        title: t("adminMenu:error"),
                        message: err,
                        color: 'red',
                    })
                )
        }
    })
}

function confirmClassDelete(classId: string, t: any) {
    modals.openConfirmModal({
        title: t("deleteClassTitle"),
        centered: true,
        children: (
            <Text>
                {t("deleteClassBody")}
            </Text>
        ),
        labels: {confirm: `${t("adminMenu:delete")}`, cancel: `${t("adminMenu:cancel")}`},
        confirmProps: {color: 'red'},
        onCancel: () => {
        },
        onConfirm: () => {
            deleteCourseClass(classId)
                .then(() =>
                    notifications.show({
                        title: t("adminMenu:success"),
                        message: t("classDeleted"),
                        color: 'green',
                    })
                )
                .catch(err =>
                    notifications.show({
                        title: t("adminMenu:error"),
                        message: err,
                        color: 'red',
                    })
                )
        }
    })
}

function getEnrollmentNums(course: Course, t: any) {
    if (course.maxStudents) {
        return `${t("enrolled")}: ${course.numEnrolled}/${course.maxStudents}`
    } else {
        return `${t("enrolled")}: ${course.numEnrolled}`
    }
}
