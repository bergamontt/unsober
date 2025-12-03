import { Blockquote, Stack, Table } from "@mantine/core";
import { useTranslation } from "react-i18next";
import infoCircle from '../../assets/infoCircle.svg'
import Icon from "../../common/icon/Icon.tsx";
import type { Course } from "../../models/Course.ts";
import { getAllGroupsByCourseId } from "../../services/CourseGroupService.ts";
import useFetch from "../../hooks/useFetch.ts";

interface CourseDetailsProps {
    course: Course;
}

function CourseDetails({ course }: CourseDetailsProps) {
    const { t } = useTranslation("coursePreview");
    const { data: groups } = useFetch(getAllGroupsByCourseId, [course.id]);
    return (
        <Stack align="stretch" justify="center" gap={0} >
            <Blockquote color="indigo" cite={t('annotation')} iconSize={35} icon={<Icon src={infoCircle} />} mt="0.8em" w="100%">
                {course.subject?.annotation || t('noAnnotation')}
            </Blockquote>

            <Table mt="1em" variant="vertical" withTableBorder highlightOnHover captionSide="top">
                <Table.Caption>{t("genericCourseDetails")}</Table.Caption>
                <Table.Tbody>
                    <Table.Tr>
                        <Table.Th w={300}>{t('faculty')}</Table.Th>
                        <Table.Td>{course.subject.facultyName ?? "-"}</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('department')}</Table.Th>
                        <Table.Td>{course.subject.departmentName ?? "-"}</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('educationlevel')}</Table.Th>
                        <Table.Td>{t(course.subject.educationLevel)}</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('academicYear')}</Table.Th>
                        <Table.Td>{course.courseYear}</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('semester')}</Table.Th>
                        <Table.Td>{t(course.subject.term)}</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('numOfcredits')}</Table.Th>
                        <Table.Td>{course.subject.credits}</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('weeklyHours')}</Table.Th>
                        <Table.Td>{course.subject.hoursPerWeek + " " + t("hoursPerWeek")}.</Table.Td>
                    </Table.Tr>

                </Table.Tbody>
            </Table>

            <Table mt="1em" variant="vertical" withTableBorder highlightOnHover captionSide="top">
                <Table.Caption>{t('limitations')}</Table.Caption>
                <Table.Tbody>

                    <Table.Tr>
                        <Table.Th w={300}>{t('maxNumOfStudents')}</Table.Th>
                        <Table.Td>{course.maxStudents ?? t("unlimited")}</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('numOfEnrolledStudents')}</Table.Th>
                        <Table.Td>{course.numEnrolled}</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('numOfGroups')}</Table.Th>
                        <Table.Td>{groups?.length ?? 0}</Table.Td>
                    </Table.Tr>

                </Table.Tbody>
            </Table>
        </Stack>
    );
}

export default CourseDetails