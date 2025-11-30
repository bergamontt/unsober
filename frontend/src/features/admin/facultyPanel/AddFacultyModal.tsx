import { Button, Modal, Stack, Textarea, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import { notifications } from "@mantine/notifications";
import { addFaculty } from "../../../services/FacultyService.ts";
import type { FacultyDto } from "../../../models/Faculty.ts";
import axios from "axios";
import { validateFacultyDto } from "../../../validation/facultyValidator.ts";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddFacultyModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageFaculties");
    const { t: errT } = useTranslation("facultyErrors");

    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");

    const [isAdding, setIsAdding] = useState(false);

    const resetForm = () => {
        setName("");
        setDescription("");
    };

    const handleSubmit = useCallback(async () => {
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

        setIsAdding(true);
        try {
            await addFaculty(dto);
            notifications.show({
                title: t("success"),
                message: t("facultyAdded", { name }),
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
    }, [t, errT, close, name, description]);

    return (
        <Modal centered title={t("addFaculty")} opened={opened} onClose={close}>
            <Stack p="xs" pt="0">
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
                    onClick={handleSubmit}
                    loading={isAdding}
                    disabled={isAdding}
                >
                    {t("addFaculty")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default AddFacultyModal;
