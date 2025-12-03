import {Stack, Button, Group, Loader, Center} from "@mantine/core";
import {useTranslation} from "react-i18next";
import type {Course} from "../../../models/Course.ts";
import {useNavigate} from "react-router";
import useFetch from "../../../hooks/useFetch.ts";
import {getAllClassesByCourseId} from "../../../services/CourseClassService.ts";
import {useEffect, useState} from "react";
import type {CourseGroup} from "../../../models/CourseGroup.ts";
import type {CourseClass} from "../../../models/CourseClass.ts";
import ClassTable from "./ClassTable.tsx";
import Icon from "../../../common/icon/Icon.tsx";
import plus from "../../../assets/plus.svg";
import GroupModal from "./GroupModal.tsx";
import {modals} from "@mantine/modals";
import {getAllGroupsByCourseId} from "../../../services/CourseGroupService";

interface CoursePanelProps {
    course: Course
}

function CoursePanelInfo({course}: CoursePanelProps) {
    const {t} = useTranslation(["coursePanel", "schedule"]);
    const {data: classes} = useFetch(getAllClassesByCourseId, [course.id]);
    const {data: groups} = useFetch(getAllGroupsByCourseId, [course.id]);
    const [lectures, setLectures] = useState([] as CourseClass[]);
    const navigate = useNavigate();

    useEffect(() => {
        if (!classes) return;
        setLectures(removeDuplicateClasses(classes.filter(c => c.type === "LECTURE")));
    }, [classes]);

    return (
        <Stack>
            {
                groups &&
                <Group justify="space-between">
                    <Button
                        variant="fill"
                        color="indigo"
                        flex={1}
                        onClick={() => {
                            openGroupModal(course.id, groups.map(g => g.groupNumber).sort(), null, t);
                        }}
                        leftSection={<Icon src={plus} size={"24px"}/>}
                    >
                        {t('addGroup')}
                    </Button>
                    <Button
                        variant="outline"
                        color="indigo"
                        flex={1}
                        onClick={() => navigate(`/course/${course.id}`)}
                    >
                        {t('details')}
                    </Button>
                </Group>
            }

            <Stack align="stretch" justify="center" gap={"2rem"}>
                {
                    lectures && groups &&
                    <ClassTable classes={lectures} course={course} groupIds={groups.map(g => g.id)} />
                    || <></>
                }
                {
                    groups && classes &&
                    groups
                        .sort((a, b) => a.groupNumber - b.groupNumber)
                        .map((group: CourseGroup) => (
                            <ClassTable classes={classes} group={group} key={group.id} course={course}
                                        groupNums={groups.map(g => g.groupNumber).sort()}/>
                    ))
                    || <Center><Loader color="gray" size="xl" mt={50} mb={50}/></Center>
                }
            </Stack>
        </Stack>
    );
}

export default CoursePanelInfo

function openGroupModal(courseId: string, groupNums: number[], group: CourseGroup | null, t: any) {
    modals.open({
        title: group ? t("editGroup") : t("addGroup"),
        centered: true,
        onClose: () => {

        },
        children: <GroupModal courseId={courseId} groupNums={groupNums} group={group}/>
    })
}

function removeDuplicateClasses(classes: CourseClass[]): CourseClass[] {
    const seen = new Map<string, CourseClass>();

    classes.forEach(c => {
        const key = [
            c.weeksList.join(''),
            c.weekDay,
            c.classNumber,
            c.location ?? '',
            c.teacher?.id ?? ''
        ].join('');

        seen.set(key, c);
    });

    return Array.from(seen.values());
}
