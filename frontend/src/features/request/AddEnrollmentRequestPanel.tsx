import { useTranslation } from "react-i18next";
import { getAllCoursesByYear } from "../../services/CourseService";
import { useCallback, useMemo, useState } from "react";
import { getAppState } from "../../services/AppStateService";
import useFetch from "../../hooks/useFetch";
import type { EnrollmentRequestDto } from "../../models/Request";
import { validateEnrollmentRequestDto } from "../../validation/enrollmentRequestValidator";
import { notifications } from "@mantine/notifications";
import { addEnrollmentRequest } from "../../services/EnrollmentRequestService";
import axios from "axios";
import { Button, Select, Stack, Textarea } from "@mantine/core";
import { useStudentStore } from "../../hooks/studentStore";

interface AddEnrollmentRequestPanelProps {
    close: () => void;
}

function AddEnrollmentRequestPanel({ close }: AddEnrollmentRequestPanelProps) {
    const { t } = useTranslation("studentRequests");
    const { t: errT } = useTranslation("enrollmentRequestErrors");
    const { user: student } = useStudentStore();
    const { data: state } = useFetch(getAppState, []);
    const infoParams = useMemo(() => ({ page: 0, size: 0 }), []);
    const { data: infoRes } = useFetch(getAllCoursesByYear, [infoParams, state?.currentYear ?? null]);
    const params = useMemo(() => (
        { page: 0, size: infoRes?.page.totalElements ?? 0 }), [infoRes]
    );
    const { data } = useFetch(getAllCoursesByYear, [params, state?.currentYear ?? null]);
    const courses = data?.content ?? [];

    const [courseId, setCourseId] = useState<string | undefined>();
    const [reason, setReason] = useState<string>("");

    const [isAdding, setIsAdding] = useState(false);

    const resetForm = () => {
        setCourseId(undefined);
        setReason("");
    };

    const handleSubmit = useCallback(async () => {
        const dto: EnrollmentRequestDto = {
            courseId,
            studentId: student?.id,
            reason
        };

        const errs = validateEnrollmentRequestDto(dto, (k, p) => errT(k, p));
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
            await addEnrollmentRequest(dto);
            notifications.show({
                title: t("success"),
                message: t("enrollmentRequestAdded"),
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
    }, [t, errT, close, courseId, reason]);

    return (
        <Stack>
            <Select
                label={t("selectCourse")}
                withAsterisk
                searchable
                data={courses.map((c: any) => ({ value: c.id, label: c.subject.name }))}
                value={courseId ?? undefined}
                onChange={(e) => setCourseId(e ?? undefined)}
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

export default AddEnrollmentRequestPanel;