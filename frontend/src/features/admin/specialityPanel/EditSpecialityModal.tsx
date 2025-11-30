import { Button, Modal, NativeSelect, Stack, Textarea, TextInput } from "@mantine/core";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getAllDepartments } from "../../../services/DepartmentService.ts";
import { notifications } from "@mantine/notifications";
import { getSpecialityById, updateSpeciality } from "../../../services/SpecialityService.ts";
import type { SpecialityDto } from "../../../models/Speciality.ts";
import axios from "axios";
import { validateSpecialityDto } from "../../../validation/specialityValidator.ts";

type EditModalProps = {
    opened: boolean;
    close: () => void;
    specialityId: string | null;
}

function EditSpecialityModal({ opened, close, specialityId }: EditModalProps) {
    const { t } = useTranslation("manageSpecialities");
    const { t: errT } = useTranslation("specialityErrors");
    const { data: departments } = useFetch(getAllDepartments, []);
    const { data: speciality } = useFetch(getSpecialityById, [specialityId]);

    const [name, setName] = useState<string>(speciality?.name ?? "");
    const [description, setDescription] = useState<string>(speciality?.description ?? "");
    const [departmentId, setDepartmentId] = useState<string | undefined>(speciality?.department?.id);

    const [isSaving, setIsSaving] = useState(false);

    useEffect(() => {
        if (!speciality) return;
        setName(speciality.name ?? "");
        setDescription(speciality.description ?? "");
        setDepartmentId(speciality.department?.id);
    }, [speciality]);

    const resetForm = () => {
        setName("");
        setDescription("");
        setDepartmentId(undefined);
    };

    const handleSave = useCallback(async () => {
        const dto: SpecialityDto = {
            name,
            description,
            departmentId,
        };

        const errs = validateSpecialityDto(dto, (k, p) => errT(k, p));
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
            if (!specialityId)
                return;
            await updateSpeciality(specialityId, dto);
            notifications.show({
                title: t("success"),
                message: t("specialityUpdated"),
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
    }, [t, errT, close, specialityId, name, description, departmentId]);

    if (!specialityId)
        return <></>;

    return (
        <Modal
            centered
            title={t("editSpeciality")}
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
                        ...(departments ?? []).map((d: any) => ({ value: d.id, label: d.name })),
                    ]}
                    value={departmentId ?? undefined}
                    onChange={(e) => setDepartmentId(e.currentTarget.value)}
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

export default EditSpecialityModal;
