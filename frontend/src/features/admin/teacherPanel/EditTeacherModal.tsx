import { Button, Group, Modal, Stack, TextInput } from "@mantine/core";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { notifications } from "@mantine/notifications";
import { getTeacherById, updateTeacher } from "../../../services/TeacherService.ts";
import { type TeacherDto } from "../../../models/Teacher.ts";
import axios from "axios";

type EditModalProps = {
    opened: boolean;
    close: () => void;
    teacherId: string | null;
}

function EditTeacherModal({ opened, close, teacherId }: EditModalProps) {
    const { t } = useTranslation("manageTeachers");
    const { data: teacher } = useFetch(getTeacherById, [teacherId]);

    const [firstName, setFirstName] = useState<string>(teacher?.firstName ?? "");
    const [lastName, setLastName] = useState<string>(teacher?.lastName ?? "");
    const [patronymic, setPatronymic] = useState<string>(teacher?.patronymic ?? "");
    const [email, setEmail] = useState<string>(teacher?.email ?? "");

    const [isSaving, setIsSaving] = useState(false);

    useEffect(() => {
        if (!teacher)
            return;
        setFirstName(teacher.firstName ?? "");
        setLastName(teacher.lastName ?? "");
        setPatronymic(teacher.patronymic ?? "");
        setEmail(teacher.email ?? "");
    }, [teacher]);

    const resetForm = () => {
        setFirstName("");
        setLastName("");
        setPatronymic("");
        setEmail("");
    };

    const validateInput = useCallback((): boolean => {
        if (firstName.trim().length < 2) {
            notifications.show({
                title: t("error"),
                message: t("nameTooShort", { min: 2 }),
                color: "red",
            });
            return false;
        }
        if (lastName.trim().length < 2) {
            notifications.show({
                title: t("error"),
                message: t("surnameTooShort", { min: 2 }),
                color: "red",
            });
            return false;
        }
        if (patronymic.trim().length < 2) {
            notifications.show({
                title: t("error"),
                message: t("patronymicTooShort", { min: 2 }),
                color: "red",
            });
            return false;
        }
        if (email.trim().length < 5) {
            notifications.show({
                title: t("error"),
                message: t("emailTooShort", { min: 5 }),
                color: "red",
            });
            return false;
        }
        return true;
    }, [firstName, lastName, patronymic, email, t]);

    const handleSave = useCallback(async () => {
        if (!validateInput())
            return;

        const dto: TeacherDto = {
            firstName: firstName,
            lastName: lastName,
            patronymic: patronymic,
            email: email
        };

        setIsSaving(true);
        try {
            if (!teacherId)
                return;
            await updateTeacher(teacherId, dto);
            notifications.show({
                title: t("success"),
                message: t("teacherUpdated"),
                color: 'green'
            });
            close();
            resetForm();
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownUpdateError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim())
                    errorMessage = data;
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: 'red'
            });
        } finally {
            setIsSaving(false);
        }
    }, [validateInput, t, close, teacherId, firstName, lastName, patronymic, email]);

    if (!teacherId)
        return <></>;

    return (
        <Modal
            centered
            title={t("editTeacher")}
            opened={opened}
            onClose={() => {
                resetForm();
                close();
            }}
        >
            <Stack m="xs">
                <Group grow>
                    <TextInput
                        label={t("name")}
                        withAsterisk
                        placeholder={t("teacherName")}
                        value={firstName}
                        onChange={(e) => setFirstName(e.currentTarget.value)}
                    />
                    <TextInput
                        label={t("surname")}
                        withAsterisk
                        placeholder={t("teacherSurname")}
                        value={lastName}
                        onChange={(e) => setLastName(e.currentTarget.value)}
                    />
                </Group>
                <TextInput
                    label={t("patronymic")}
                    withAsterisk
                    placeholder={t("teacherPatronymic")}
                    value={patronymic}
                    onChange={(e) => setPatronymic(e.currentTarget.value)}
                />
                <TextInput
                    label={t("email")}
                    withAsterisk
                    placeholder={t("teacherEmail")}
                    value={email}
                    onChange={(e) => setEmail(e.currentTarget.value)}
                />
                <Button
                    variant="filled"
                    color="green"
                    mt="xs"
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

export default EditTeacherModal;
