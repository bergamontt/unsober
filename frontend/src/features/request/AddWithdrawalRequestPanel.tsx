import { useTranslation } from "react-i18next";
import { useStudentStore } from "../../hooks/studentStore";
import { getAppState } from "../../services/AppStateService";
import useFetch from "../../hooks/useFetch";
import { getAllEnrollmentsByStudentIdAndYear } from "../../services/StudentEnrollmentService";
import { useCallback, useState } from "react";
import type { WithdrawalRequestDto } from "../../models/Request";
import { validateWithdrawalRequestDto } from "../../validation/withdrawalRequestValidator";
import { notifications } from "@mantine/notifications";
import { addWithdrawalRequest } from "../../services/WithdrawalRequestService";
import axios from "axios";
import { Button, Select, Stack, Textarea } from "@mantine/core";

interface AddWithdrawalRequestPanelProps {
    close: () => void;
}

function AddWithdrawalRequestPanel({ close }: AddWithdrawalRequestPanelProps) {
    const { t } = useTranslation("studentRequests");
    const { t: errT } = useTranslation("withdrawalRequestErrors");
    const { user: student } = useStudentStore();
    const { data: state } = useFetch(getAppState, []);
    const { data } = useFetch(getAllEnrollmentsByStudentIdAndYear,
        [student?.id ?? null, state?.currentYear ?? null]);
    const enrollments = data ?? [];

    const [enrollmentId, setEnrollmentId] = useState<string | undefined>();
    const [reason, setReason] = useState<string>("");

    const [isAdding, setIsAdding] = useState(false);

    const resetForm = () => {
        setEnrollmentId(undefined);
        setReason("");
    };

    const handleSubmit = useCallback(async () => {
        const dto: WithdrawalRequestDto = {
            studentEnrollmentId: enrollmentId,
            reason
        };

        const errs = validateWithdrawalRequestDto(dto, (k, p) => errT(k, p));
        if (errs.length > 0) {
            notifications.show({
                title: t("error"),
                message: errs.join('\n'),
                color: "red",
            });
            return;
        }

        setIsAdding(true);
        try {
            await addWithdrawalRequest(dto);
            notifications.show({
                title: t("success"),
                message: t("withdrawalRequestAdded"),
                color: "green",
            });
            close();
            resetForm();
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownAddError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim()) {
                    errorMessage = data;
                }
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: "red",
            });
        } finally {
            setIsAdding(false);
        }
    }, [t, errT, close, enrollmentId, reason]);

    return (
        <Stack>
            <Select
                label={t("selectEnrollment")}
                withAsterisk
                searchable
                data={enrollments.map((e: any) => ({ value: e.id, label: e.course.subject.name }))}
                value={enrollmentId ?? undefined}
                onChange={(e) => setEnrollmentId(e ?? undefined)}
            />
            <Textarea
                label={t("reason")}
                withAsterisk
                placeholder={t("writeReason")}
                autosize
                minRows={4}
                maxRows={8}
                value={reason}
                onChange={(e) => setReason(e.currentTarget.value)}
            />
            <Button
                variant="filled"
                color="green"
                onClick={handleSubmit}
                loading={isAdding}
                disabled={isAdding}
            >
                {t("submit")}
            </Button>
        </Stack>
    );
}

export default AddWithdrawalRequestPanel;