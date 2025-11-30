import { Button, Modal, Stack, Textarea, TextInput } from "@mantine/core";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { notifications } from "@mantine/notifications";
import { getFacultyById, updateFaculty } from "../../../services/FacultyService.ts";
import type { FacultyDto } from "../../../models/Faculty.ts";
import axios from "axios";
import { validateFacultyDto } from "../../../validation/facultyValidator.ts";

type EditModalProps = {
    opened: boolean;
    close: () => void;
    facultyId: string | null;
}

function EditFacultyModal({ opened, close, facultyId }: EditModalProps) {
    const { t } = useTranslation("manageFaculties");
    const { t: errT } = useTranslation("facultyErrors");
    const { data: faculty } = useFetch(getFacultyById, [facultyId]);

    const [name, setName] = useState<string>(faculty?.name ?? "");
    const [description, setDescription] = useState<string>(faculty?.description ?? "");

    const [isSaving, setIsSaving] = useState(false);

    useEffect(() => {
        if (!faculty)
            return;
        setName(faculty.name ?? "");
        setDescription(faculty.description ?? "");
    }, [faculty]);

    const resetForm = () => {
        setName("");
        setDescription("");
    };

    const handleSave = useCallback(async () => {
        const dto: FacultyDto = {
            name,
            description,
        };

        const errs = validateFacultyDto(dto, (k, p) => errT(k, p));
        if (errs.length > 0) {
            notifications.show({
                title: t("error"),
                message: errs.join('\n'),
                color: "red",
            });
            return;
        }

        setIsSaving(true);
        try {
            if (!facultyId) return;
            await updateFaculty(facultyId, dto);
            notifications.show({
                title: t("success"),
                message: t("facultyUpdated"),
                color: "green",
            });
            close();
            resetForm();
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownUpdateError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim()) errorMessage = data;
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: "red",
            });
        } finally {
            setIsSaving(false);
        }
    }, [t, errT, close, facultyId, name, description]);

    if (!facultyId)
        return <></>;

    return (
        <Modal
            centered
            title={t("editFaculty")}
            opened={opened}
            onClose={() => {
                resetForm();
                close();
            }}
        >
            <Stack m="xs">
                <TextInput
                    label={t("name")}
                    withAsterisk
                    placeholder={t("facultyName")}
                    value={name}
                    onChange={(e) => setName(e.currentTarget.value)}
                />
                <Textarea
                    label={t("description")}
                    withAsterisk
                    placeholder={t("facultyDescription")}
                    minRows={3}
                    value={description}
                    onChange={(e) => setDescription(e.currentTarget.value)}
                />
                <Button
                    variant="filled"
                    color="green"
                    onClick={handleSave}
                    loading={isSaving}
                    disabled={isSaving}
                >
                    {t("saveChanges")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default EditFacultyModal;
