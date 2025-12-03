import { Button, Modal, NativeSelect, Stack, Textarea, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import { notifications } from "@mantine/notifications";
import { addSpeciality } from "../../../services/SpecialityService.ts";
import type { SpecialityDto } from "../../../models/Speciality.ts";
import axios from "axios";
import { validateSpecialityDto } from "../../../validation/specialityValidator.ts";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddCourseModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("coursePanel");
    const { t: errT } = useTranslation("specialityErrors");
    // const { data } = useFetch(getAllSubjects, []);
    // const subjects = data ?? [];
    //
    // const currentYear = new Date().getFullYear();

    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [departmentId, setDepartmentId] = useState<string | undefined>();

    const [isAdding, setIsAdding] = useState(false);

    const resetForm = () => {
        setName("");
        setDescription("");
        setDepartmentId(undefined);
    };

    const handleSubmit = useCallback(async () => {
        const dto: SpecialityDto = {
            name: name,
            description: description,
            departmentId: departmentId,
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
    }, [t, errT, close, name, description, departmentId]);

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
                        { value: "", label: t("department") }
                        // ...departments.map((d: any) => ({ value: d.id, label: d.name })),
                    ]}
                    value={departmentId ?? undefined}
                    onChange={(e) => setDepartmentId(e.currentTarget.value)}
                />
                <Button
                    variant="filled"
                    color="green"
                    onClick={handleSubmit}
                    loading={isAdding}
                    disabled={isAdding}
                >
                    {t("addSpeciality")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default AddCourseModal;
