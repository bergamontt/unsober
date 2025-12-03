import { Button, Modal, NumberInput, Select, Stack } from "@mantine/core";
import { useCallback, useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import { getSubjects } from "../../../services/SubjectService.ts";
import useFetch from "../../../hooks/useFetch.ts";
import { getAppState } from "../../../services/AppStateService.ts";
import type { CourseDto } from "../../../models/Course.ts";
import { validateCourseDto } from "../../../validation/courseValidator.ts";
import { getCourseById, updateCourse } from "../../../services/CourseService.ts";

type AddModalProps = {
    opened: boolean;
    close: () => void;
    courseId: string;
}

function EditCourseModal({ opened, close, courseId }: AddModalProps) {
    const { data: course } = useFetch(getCourseById, [courseId]);
    const { t } = useTranslation("coursePanel");
    const { t: errT } = useTranslation("courseErrors");
    const { data: state } = useFetch(getAppState, []);
    const infoParams = useMemo(() => ({
        page: 0,
        size: 0
    }), []);
    const { data: infoRes } = useFetch(getSubjects, [infoParams]);
    const params = useMemo(() => (
        { page: 0, size: infoRes?.page.totalElements ?? 0 }), [infoRes]
    );
    const { data } = useFetch(getSubjects, [params]);
    const subjects = data?.content ?? [];

    const [subjectId, setSubjectId] = useState<string | undefined>(course?.subject.id);
    const [maxStudents, setMaxStudents] = useState<number | undefined>(course?.maxStudents);

    const [isAdding, setIsAdding] = useState(false);

    useEffect(() => {
        if (!course)
            return;
        setSubjectId(course.subject.id);
        setMaxStudents(course.maxStudents);
    }, [course]);

    const resetForm = () => {
        setSubjectId(undefined);
        setMaxStudents(undefined);
    };

    const handleSubmit = useCallback(async () => {
        const dto: CourseDto = {
            courseYear: state?.currentYear,
            subjectId,
            maxStudents
        }

        const errs = validateCourseDto(dto, (k, p) => errT(k, p));
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
            await updateCourse(courseId, dto);
            notifications.show({
                title: t("success"),
                message: t("courseUpdated"),
                color: "green",
            });
            close();
            resetForm();
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownUpdateError");
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
    }, [t, errT, close, courseId, state, subjectId, maxStudents]);

    return (
        <Modal
            centered
            title={t("editCourse")}
            opened={opened}
            onClose={close}
        >
            <Stack p="xs" pt="0">
                <Select
                    label={t("subject")}
                    withAsterisk
                    searchable
                    data={subjects.map((s: any) => ({ value: s.id, label: s.name }))}
                    value={subjectId}
                    onChange={(e) => setSubjectId(e ?? undefined)}
                />
                <NumberInput
                    label={t("maxStudentsOnCourse")}
                    description={t("numAllowed")}
                    value={maxStudents}
                    onChange={e => setMaxStudents(Number(e))}
                />
                <Button
                    variant="filled"
                    color="green"
                    onClick={handleSubmit}
                    loading={isAdding}
                    disabled={isAdding}
                >
                    {t("saveChanges")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default EditCourseModal;
