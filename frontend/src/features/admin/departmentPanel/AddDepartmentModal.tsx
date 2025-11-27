import { Button, Group, Modal, NativeSelect, Stack, Textarea, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getAllFaculties } from "../../../services/FacultyService.ts";
import { notifications } from "@mantine/notifications";
import { addDepartment } from "../../../services/DepartmentService.ts";
import type { DepartmentDto } from "../../../models/Department.ts";
import axios from "axios";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddDepartmentModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageDepartments");
    const { data } = useFetch(getAllFaculties, []);
    const faculties = data ?? [];

    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [facultyId, setFacultyId] = useState<string | undefined>();

    const [isAdding, setIsAdding] = useState(false);

    const resetForm = () => {
        setName("");
        setDescription("");
        setFacultyId(undefined);
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
        if (description.trim().length < 3) {
            notifications.show({
                title: t("error"),
                message: t("descriptionTooShort", { min: 3 }),
                color: "red",
            });
            return false;
        }
        if (!facultyId) {
            notifications.show({
                title: t("error"),
                message: t("selectFaculty"),
                color: "red",
            });
            return false;
        }
        return true;
    }, [name, description, facultyId, t]);

    const handleSubmit = useCallback(async () => {
        if (!validateInput())
            return;

        const dto: DepartmentDto = {
            name,
            description,
            facultyId,
        };

        setIsAdding(true);
        try {
            await addDepartment(dto);
            notifications.show({
                title: t("success"),
                message: t("departmentAdded", { name }),
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
    }, [validateInput, t, close, name, description, facultyId]);

    return (
        <Modal
            centered
            title={t("addDepartment")}
            opened={opened}
            onClose={close}
        >
            <Stack p="xs" pt="0">
                <TextInput
                    label={t("name")}
                    withAsterisk
                    placeholder={t("departmentName")}
                    value={name}
                    onChange={(e) => setName(e.currentTarget.value)}
                />
                <Textarea
                    label={t("description")}
                    withAsterisk
                    placeholder={t("departmentDescription")}
                    minRows={3}
                    value={description}
                    onChange={(e) => setDescription(e.currentTarget.value)}
                />
                <NativeSelect
                    label={t("faculty")}
                    withAsterisk
                    data={[
                        { value: "", label: t("faculty") },
                        ...faculties.map((f: any) => ({ value: f.id, label: f.name })),
                    ]}
                    value={facultyId ?? undefined}
                    onChange={(e) => setFacultyId(e.currentTarget.value)}
                />
                <Group grow>
                    <Button
                        variant="filled"
                        color="green"
                        onClick={handleSubmit}
                        loading={isAdding}
                        disabled={isAdding}
                    >
                        {t("addDepartment")}
                    </Button>
                </Group>
            </Stack>
        </Modal>
    );
}

export default AddDepartmentModal;
