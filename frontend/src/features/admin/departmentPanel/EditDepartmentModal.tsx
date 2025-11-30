import { Button, Modal, NativeSelect, Stack, Textarea, TextInput } from "@mantine/core";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getAllFaculties } from "../../../services/FacultyService.ts";
import { notifications } from "@mantine/notifications";
import { getDepartmentById, updateDepartment } from "../../../services/DepartmentService.ts";
import type { DepartmentDto } from "../../../models/Department.ts";
import axios from "axios";
import { validateDepartmentDto } from "../../../validation/departmentvalidator.ts";

type EditModalProps = {
    opened: boolean;
    close: () => void;
    departmentId: string | null;
}

function EditDepartmentModal({ opened, close, departmentId }: EditModalProps) {
    const { t } = useTranslation("manageDepartments");
    const { t: errT } = useTranslation("departmentErrors");
    const { data: faculties } = useFetch(getAllFaculties, []);
    const { data: department } = useFetch(getDepartmentById, [departmentId]);

    const [name, setName] = useState<string>(department?.name ?? "");
    const [description, setDescription] = useState<string>(department?.description ?? "");
    const [facultyId, setFacultyId] = useState<string | undefined>(department?.faculty?.id);

    const [isSaving, setIsSaving] = useState(false);

    useEffect(() => {
        if (!department)
            return;
        setName(department.name ?? "");
        setDescription(department.description ?? "");
        setFacultyId(department.faculty?.id);
    }, [department]);

    const resetForm = () => {
        setName("");
        setDescription("");
        setFacultyId(undefined);
    };

    const handleSave = useCallback(async () => {
        const dto: DepartmentDto = {
            name,
            description,
            facultyId,
        };

        const errs = validateDepartmentDto(dto, (k, p) => errT(k, p));
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
            if (!departmentId) return;
            await updateDepartment(departmentId, dto);
            notifications.show({
                title: t("success"),
                message: t("departmentUpdated"),
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
    }, [t, errT, close, departmentId, name, description, facultyId]);

    if (!departmentId)
        return <></>;

    return (
        <Modal
            centered
            title={t("editDepartment")}
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
                        ...(faculties ?? []).map((f: any) => ({ value: f.id, label: f.name })),
                    ]}
                    value={facultyId ?? undefined}
                    onChange={(e) => setFacultyId(e.currentTarget.value)}
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

export default EditDepartmentModal;
