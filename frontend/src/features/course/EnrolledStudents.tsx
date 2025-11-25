import { Table } from "@mantine/core";
import { getAllEnrollmentsByCourseId } from "../../services/StudentEnrollmentService";
import useFetch from "../../hooks/useFetch";
import { useTranslation } from "react-i18next";
import { getRecommendationBySubjectAndSpeciality, recommendationExistsBySubjectAndSpeciality } from "../../services/SubjectRecommendationService";
import { useEffect, useState } from "react";

interface EnrolledStudentsProps {
    courseId: string;
}

function EnrolledStudents({ courseId }: EnrolledStudentsProps) {
    const { t } = useTranslation("studentEnrollment");
    const { data: enrollments } = useFetch(getAllEnrollmentsByCourseId, [courseId]);
    const [recommendations, setRecommendations] = useState<Record<string, string>>({});
    useEffect(() => {
        if (!enrollments) return;
        const fetchRecommendations = async () => {
            const recMap: Record<string, string> = {};
            await Promise.all(
                enrollments.map(async (e) => {
                    const subjectId = e.course.subject.id;
                    const specialityId = e.student.speciality.id;
                    const recExists = await recommendationExistsBySubjectAndSpeciality(subjectId, specialityId);
                    if(recExists){
                        const recomm = await getRecommendationBySubjectAndSpeciality(subjectId, specialityId);
                        recMap[e.student.id] = recomm?.recommendation ?? "FREE_CHOICE";
                    } else {
                        recMap[e.student.id] = "FREE_CHOICE";
                    }
                })
            );
            setRecommendations(recMap);
        };
        fetchRecommendations();
    }, [enrollments]);

    return (
        <Table highlightOnHover withTableBorder withColumnBorders captionSide="top">
            <Table.Caption>
                {t("studentList")}
            </Table.Caption>

            <Table.Thead>
                <Table.Tr bg="#F8F9FA">
                    <Table.Th fw="normal">№</Table.Th>
                    <Table.Th fw="normal">{t("fullName")}</Table.Th>
                    <Table.Th fw="normal">{t("course")}</Table.Th>
                    <Table.Th fw="normal">{t("speciality")}</Table.Th>
                    <Table.Th fw="normal">{t("type")}</Table.Th>
                    <Table.Th fw="normal">{t("status")}</Table.Th>
                    <Table.Th fw="normal">{t("group")}</Table.Th>
                </Table.Tr>
            </Table.Thead>

            <Table.Tbody>
                {enrollments?.map((e, index) => {
                    const s = e.student;
                    const name = `${s.lastName} ${s.firstName} ${s.patronymic}`;
                    const groupNum = e.courseGroup?.groupNumber ?? "-";
                    const recommendation = recommendations[s.id] ?? "FREE_CHOICE";

                    return (
                        <Table.Tr key={s.id}>
                            <Table.Td>{index + 1}</Table.Td>
                            <Table.Td>{name}</Table.Td>
                            <Table.Td>{s.studyYear}</Table.Td>
                            <Table.Td>{s.speciality.name}</Table.Td>
                            <Table.Td>{t(recommendation)}</Table.Td>
                            <Table.Td>{t(e.status)}</Table.Td>
                            <Table.Td>{groupNum}</Table.Td>
                        </Table.Tr>
                    );
                })}
            </Table.Tbody>
        </Table>
    );
}

export default EnrolledStudents