import { Group, NativeSelect, Title } from "@mantine/core";
import PageWrapper from "../../common/pageWrapper/PageWrapper";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import IndividualPlan from "./IndividualPlan";
import { useAuthStore } from "../../hooks/authStore";
import { useNavigate } from "react-router";
import { getAllYearsByStudentId } from "../../services/StudentEnrollmentService";
import { useStudentStore } from "../../hooks/studentStore";
import useFetch from "../../hooks/useFetch";

function EnrollmentsPage() {
    const { isAuthenticated, loadingAuth, currentRoles } = useAuthStore();
    const navigate = useNavigate();
    useEffect(() => {
        if ((!isAuthenticated || !currentRoles.includes("STUDENT")) && !loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, loadingAuth, navigate]);
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
    );
}

export default EnrollmentsPage;