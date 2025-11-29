import { Stack, Table, Title } from "@mantine/core";
import type { StudentEnrollment } from "../../models/StudentEnrollment";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { getRecommendationBySubjectAndSpeciality, recommendationExistsBySubjectAndSpeciality } from "../../services/SubjectRecommendationService";

interface EnrollmentGroupParams {
    name: string;
    enrollments: StudentEnrollment[];
}

function EnrollmentsGroup({ name, enrollments }: EnrollmentGroupParams) {
    const { t } = useTranslation("enrollments");
    const [recommendations, setRecommendations] = useState<Record<string, string>>({});
    useEffect(() => {
        if (!enrollments)
            return;
        const fetchRecommendations = async () => {
            const recMap: Record<string, string> = {};
            await Promise.all(
                enrollments.map(async (e) => {
                    const subjectId = e.course.subject.id;
                    const specialityId = e.student.speciality.id;
                    const recExists = await recommendationExistsBySubjectAndSpeciality(subjectId, specialityId);
                    if (recExists) {
                        const recomm = await getRecommendationBySubjectAndSpeciality(subjectId, specialityId);
                        recMap[e.id] = recomm?.recommendation ?? "FREE_CHOICE";
                    } else {
                        recMap[e.id] = "FREE_CHOICE";
                    }
                })
            );
            setRecommendations(recMap);
        };
        fetchRecommendations();
    }, [enrollments]);

    const totalCredits = enrollments.reduce((sum, e) =>
        sum + Number(e.course.subject.credits ?? 0), 0);
    const totalHours = enrollments.reduce((sum, e) =>
        sum + Number(e.course.subject.hoursPerWeek ?? 0), 0);

    const header = (
        <Table.Tr>
            <Table.Th>{t("courseName")}</Table.Th>
            <Table.Th>{t("group")}</Table.Th>
            <Table.Th>{t("type")}</Table.Th>
            <Table.Th>{t("credits")}</Table.Th>
            <Table.Th>{t("hoursPerWeek")}</Table.Th>
        </Table.Tr>
    );

    const rows = enrollments
        .sort((e1, e2) => e1.course.subject.name.localeCompare(e2.course.subject.name))
        .map(e =>
            <Table.Tr key={e.id}>
                <Table.Td>{e.course.subject.name}</Table.Td>
                <Table.Td>{e.group?.groupNumber ?? t("groupNotChosen")}</Table.Td>
                <Table.Td>{t(recommendations[e.id] ?? "FREE_CHOICE")}</Table.Td>
                <Table.Td>{e.course.subject.credits.toFixed(1)}</Table.Td>
                <Table.Td>{e.course.subject.hoursPerWeek + t("hoursWeek")}</Table.Td>
            </Table.Tr>
        );

    return (
        <Stack>
            <Title order={2}>{name}</Title>
            <Table
                striped
                withColumnBorders
                withTableBorder
                withRowBorders
            >
                <Table.Thead>
                    {header}
                </Table.Thead>
                <Table.Tbody>
                    {rows.length ? rows : (
                        <Table.Tr>
                            <Table.Td colSpan={4} style={{ textAlign: 'center', opacity: 0.6 }}>
                                {t("noEnrollments")}
                            </Table.Td>
                        </Table.Tr>
                    )}
                </Table.Tbody>
                <Table.Tfoot>
                    <Table.Tr>
                        <Table.Td colSpan={3} style={{ fontWeight: 700 }}>
                            {t("total", { defaultValue: "Total" })}
                        </Table.Td>
                        <Table.Td style={{ fontWeight: 700 }}>{totalCredits.toFixed(1)}</Table.Td>
                        <Table.Td style={{ fontWeight: 700 }}>{totalHours + t("hoursWeek")}</Table.Td>
                    </Table.Tr>
                </Table.Tfoot>
            </Table>
        </Stack>
    );
}

export default EnrollmentsGroup;