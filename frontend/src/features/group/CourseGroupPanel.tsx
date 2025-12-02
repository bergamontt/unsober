import useFetch from "../../hooks/useFetch";
import { getAppState } from "../../services/AppStateService";
import { getAllEnrollmentsByStudentIdAndYear } from "../../services/StudentEnrollmentService";
import { useStudentStore } from "../../hooks/studentStore";
import { Group, Stack, Title } from "@mantine/core";
import { useTranslation } from "react-i18next";
import CourseGroupList from "./CourseGroupList";

function CourseGroupPanel() {
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
        <Stack
            align="flex-end"
            justify="center"
            pl="6em"
        >
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
        </Stack>
    );
}

export default CourseGroupPanel;