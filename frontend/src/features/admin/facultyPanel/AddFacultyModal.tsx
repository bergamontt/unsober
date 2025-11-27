import { Button, Modal, Stack, Textarea, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import { notifications } from "@mantine/notifications";
import { addFaculty } from "../../../services/FacultyService.ts";
import type { FacultyDto } from "../../../models/Faculty.ts";
import axios from "axios";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddFacultyModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageFaculties");

    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");

    const [isAdding, setIsAdding] = useState(false);

    const resetForm = () => {
        setName("");
        setDescription("");
    };

    const validateInput = useCallback((): boolean => {
        if (name.trim().length < 2) {
            notifications.show({
                title: t("error"),
                message: t("nameTooShort", { min: 2 }),
                color: "red",
            });
            return false;
        }
        return true;
    }, [name, description, t]);

    const handleSubmit = useCallback(async () => {
        if (!validateInput()) 
            return;

        const dto: FacultyDto = {
            name,
            description,
        };

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
    }, [validateInput, t, close, name, description]);

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
