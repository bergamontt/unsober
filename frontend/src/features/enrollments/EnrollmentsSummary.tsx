import { useTranslation } from "react-i18next";
import type { StudentEnrollment } from "../../models/StudentEnrollment";
import { Stack, Table, Title } from "@mantine/core";

interface EnrollmentSummaryParams {
    enrollments: StudentEnrollment[];
}

function EnrollmentsSummary({ enrollments }: EnrollmentSummaryParams) {
    const { t } = useTranslation("enrollments");
    const credits = enrollments.reduce((acc, val) => (acc + val.course.subject.credits), 0);
    return (
        <Stack>
            <Title order={3}>{t("summary")}</Title>
            <Table
                variant="vertical"
                withColumnBorders
            >
                <Table.Tbody>
                    <Table.Tr>
                        <Table.Th w={160}>{t("creditsPerYear")}</Table.Th>
                        <Table.Td>{credits}</Table.Td>
                    </Table.Tr>
                </Table.Tbody>
            </Table>
        </Stack>
    );
}

export default EnrollmentsSummary;