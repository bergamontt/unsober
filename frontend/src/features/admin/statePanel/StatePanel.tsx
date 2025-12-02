import { Button, Group, NativeSelect, Space, Stack, Title } from "@mantine/core";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { getAppState, setCurrentStage, setCurrentTerm, setCurrentYear } from "../../../services/AppStateService";
import useFetch from "../../../hooks/useFetch";
import { Term } from "../../../models/Subject";
import { EnrollmentStage } from "../../../models/AppState";
import { notifications } from "@mantine/notifications";
import axios from "axios";

function StatePanel() {
    const { t } = useTranslation("manageState");
    const { data: state } = useFetch(getAppState, []);
    const [year, setYear] = useState<number | undefined>(state?.currentYear);
    const [term, setTerm] = useState<Term | undefined>(state?.term);
    const [stage, setStage] = useState<EnrollmentStage | undefined>(state?.enrollmentStage);
    const [isSaving, setIsSaving] = useState(false);

    useEffect(() => {
        if (!state)
            return;
        setYear(state.currentYear);
        setTerm(state.term);
        setStage(state.enrollmentStage);
    }, [state, setYear, setTerm, setStage]);

    const handleSave = useCallback(async () => {
        if (!state)
            return;
        setIsSaving(true);
        try {
            if (year && year != state.currentYear) {
                await setCurrentYear(year);
                notifications.show({
                    title: t("success"),
                    message: t("currentYearUpdated"),
                    color: 'green'
                });
                state.currentYear = year;
            }
            if (term && term != state.term) {
                await setCurrentTerm(term);
                notifications.show({
                    title: t("success"),
                    message: t("currentTermUpdated"),
                    color: 'green'
                });
                state.term = term;
            }
            if (stage && stage != state.enrollmentStage) {
                await setCurrentStage(stage);
                notifications.show({
                    title: t("success"),
                    message: t("enrollmentStageUpdated"),
                    color: 'green'
                });
                state.enrollmentStage = stage;
            }
        } catch (err: unknown) {
            let errorMessage = t("unknownUpdateError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim())
                    errorMessage = data;
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: 'red'
            });
        } finally {
            setIsSaving(false);
        }
    }, [t, state, year, term, stage]);

    const range = (start: number, end: number): number[] =>
        Array.from({ length: end - start + 1 }, (_, i) => start + i);
    const years = range(2000, 2050);

    return (
        <Group >
            <Stack gap="xs">
                <Title order={4}>
                    {t("enrollmentStateManagement")}
                </Title>
                <Space />
                <Title order={4} c="dimmed">
                    {t("selectYear")}
                </Title>
                <NativeSelect
                    description={t("thisYearUsed")}
                    data={years.map((year) => ({
                        value: String(year),
                        label: year + "-" + (year + 1)
                    }))}
                    value={year}
                    onChange={(e) => setYear(Number(e.currentTarget.value))}
                />
                <Title order={4} c="dimmed">
                    {t("selectTerm")}
                </Title>
                <NativeSelect
                    description={t("thisTermUsed")}
                    data={[
                        { value: Term.AUTUMN, label: t(Term.AUTUMN) },
                        { value: Term.SPRING, label: t(Term.SPRING) },
                        { value: Term.SUMMER, label: t(Term.SUMMER) },
                    ]}
                    value={term}
                    onChange={(e) => setTerm(e.currentTarget.value as Term)}
                />
                <Title order={4} c="dimmed">
                    {t("selectStage")}
                </Title>
                <NativeSelect
                    data={[
                        { value: EnrollmentStage.CLOSED, label: t(EnrollmentStage.CLOSED) },
                        { value: EnrollmentStage.COURSES, label: t(EnrollmentStage.COURSES) },
                        { value: EnrollmentStage.GROUPS, label: t(EnrollmentStage.GROUPS) },
                        { value: EnrollmentStage.CORRECTION, label: t(EnrollmentStage.CORRECTION) },
                    ]}
                    value={stage}
                    onChange={(e) => setStage(e.currentTarget.value as EnrollmentStage)}
                />
                <Button
                    variant="filled"
                    color="green"
                    mt="xs"
                    onClick={handleSave}
                    loading={isSaving}
                    disabled={isSaving}
                >
                    {t("saveChanges")}
                </Button>
            </Stack>
        </Group>
    );
}

export default StatePanel;