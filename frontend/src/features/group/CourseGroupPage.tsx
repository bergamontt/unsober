import useFetch from "../../hooks/useFetch";
import { getAppState } from "../../services/AppStateService";
import { getAllEnrollmentsByStudentIdAndYear } from "../../services/StudentEnrollmentService";
import { useStudentStore } from "../../hooks/studentStore";
import PageWrapper from "../../common/pageWrapper/PageWrapper";
import { Group, Title } from "@mantine/core";
import { useTranslation } from "react-i18next";
import CourseGroupList from "./CourseGroupList";
import AuthGuard from "../../common/wrappers/AuthGuard";
import { UserRole } from "../../models/Auth";

function CourseGroupPage() {
    const { t } = useTranslation("groups");
    const { user: student } = useStudentStore();

    const { data: appState } = useFetch(getAppState, []);
    const state = appState ?? null;

    const { data: enrollmentsData } = useFetch(
        getAllEnrollmentsByStudentIdAndYear, [student?.id ?? null, state?.currentYear ?? null]);
    const enrollments = enrollmentsData ?? [];

    if (!state || !student || !enrollments)
        return <></>;

    return (
        <AuthGuard roles={[UserRole.STUDENT]}>
            <PageWrapper>
                <Group justify="space-between">
                    <Title>{t("chosenGroups")} </Title>
                    <Title order={4}>
                        {`${state.currentYear}-${state.currentYear + 1}, ${t(state.term)}`}
                    </Title>
                </Group>
                <CourseGroupList
                    enrollments={enrollments}
                    state={state}
                />
            </PageWrapper>
        </AuthGuard>
    );
}

export default CourseGroupPage;