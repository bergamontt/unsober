import { Group, NativeSelect, Title } from "@mantine/core";
import PageWrapper from "../../common/pageWrapper/PageWrapper";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import IndividualPlan from "./IndividualPlan";
import { getAllYearsByStudentId } from "../../services/StudentEnrollmentService";
import { useStudentStore } from "../../hooks/studentStore";
import useFetch from "../../hooks/useFetch";
import AuthGuard from "../../common/wrappers/AuthGuard";
import { UserRole } from "../../models/Auth";

function EnrollmentsPage() {
    const { user: student } = useStudentStore();
    const { t } = useTranslation("enrollments");
    const { data } = useFetch(getAllYearsByStudentId, [student?.id ?? null]);
    const years = data ?? [];
    const [year, setYear] = useState<number>(years[0]);
    useEffect(() => {
        if (years.length > 0) {
            setYear(years[0]);
        }
    }, [years, setYear]);
    return (
        <AuthGuard roles={[UserRole.STUDENT]}>
            <PageWrapper>
                <Group justify="space-between">
                    <Title>{t('individualPlan')} </Title>
                    <NativeSelect
                        label={t("studyYear")}
                        data={years.map(year => ({
                            label: String(year) + "-" + String(year + 1),
                            value: String(year)
                        }))}
                        value={year}
                        onChange={(e) => {
                            setYear(Number(e.currentTarget.value));
                        }}
                    />
                </Group>
                <IndividualPlan year={year} />
            </PageWrapper>
        </AuthGuard>
    );
}

export default EnrollmentsPage;