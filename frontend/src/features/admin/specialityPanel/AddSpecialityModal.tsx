import { Button, Group, Modal, NativeSelect, Stack, Textarea, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getAllDepartments } from "../../../services/DepartmentService.ts";
import { notifications } from "@mantine/notifications";
import { addSpeciality } from "../../../services/SpecialityService.ts";
import type { SpecialityDto } from "../../../models/Speciality.ts";
import axios from "axios";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddSpecialityModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageSpecialities");
    const { data } = useFetch(getAllDepartments, []);
    const departments = data ?? [];

    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [departmentId, setDepartmentId] = useState<string | undefined>();

    const [isAdding, setIsAdding] = useState(false);

    const resetForm = () => {
        setName("");
        setDescription("");
        setDepartmentId(undefined);
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
        if (!departmentId) {
            notifications.show({
                title: t("error"),
                message: t("selectDepartment"),
                color: "red",
            });
            return false;
        }
        return true;
    }, [name, description, departmentId, t]);

    const handleSubmit = useCallback(async () => {
        if (!validateInput()) 
            return;

        const dto: SpecialityDto = {
            name: name,
            description: description,
            departmentId: departmentId,
        };

        setIsAdding(true);
        try {
            await addSpeciality(dto);
            notifications.show({
                title: t("success"),
                message: t("specialityAdded", { name }),
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
    }, [validateInput, t, close, name, description, departmentId]);

    return (
        <Modal
            centered
            title={t("addSpeciality")}
            opened={opened}
            onClose={close}
        >
            <Stack p="xs" pt="0">
                <TextInput
                    label={t("name")}
                    withAsterisk
                    placeholder={t("specialityName")}
                    value={name}
                    onChange={(e) => setName(e.currentTarget.value)}
                />
                <Textarea
                    label={t("description")}
                    withAsterisk
                    placeholder={t("specialityDescription")}
                    minRows={3}
                    value={description}
                    onChange={(e) => setDescription(e.currentTarget.value)}
                />
                <NativeSelect
                    label={t("department")}
                    withAsterisk
                    data={[
                        { value: "", label: t("department") },
                        ...departments.map((d: any) => ({ value: d.id, label: d.name })),
                    ]}
                    value={departmentId ?? undefined}
                    onChange={(e) => setDepartmentId(e.currentTarget.value)}
                />
                <Group grow>
                    <Button
                        variant="filled"
                        color="green"
                        onClick={handleSubmit}
                        loading={isAdding}
                        disabled={isAdding}
                    >
                        {t("addSpeciality")}
                    </Button>
                </Group>
            </Stack>
        </Modal>
    );
}

export default AddSpecialityModal;
