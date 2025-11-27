import { Button, Group, Modal, Stack, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import { notifications } from "@mantine/notifications";
import { addTeacher } from "../../../services/TeacherService";
import { type TeacherDto } from "../../../models/Teacher.ts";
import axios from "axios";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddTeacherModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageTeachers");

    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [patronymic, setPatronymic] = useState<string>("");
    const [email, setEmail] = useState<string>("");

    const [isAdding, setIsAdding] = useState(false);

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

    const handleSubmit = useCallback(async () => {
        if (!validateInput())
            return;

        const dto: TeacherDto = {
            firstName: firstName,
            lastName: lastName,
            patronymic: patronymic,
            email: email
        };

        setIsAdding(true);
        try {
            await addTeacher(dto);
            notifications.show({
                title: t("success"),
                message: t("teacherAdded", { fstName: firstName, lstName: lastName }),
                color: 'green',
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
                color: 'red',
            });
        } finally {
            setIsAdding(false);
        }
    }, [validateInput, t, close, firstName, lastName, patronymic, email]);

    return (
        <Modal
            centered
            title={t("addTeacher")}
            opened={opened}
            onClose={close}
        >
            <Stack p="xs" pt="0">
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
                    onClick={handleSubmit}
                    loading={isAdding}
                    disabled={isAdding}
                >
                    {t("addTeacher")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default AddTeacherModal;
