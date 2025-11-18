import { Button, Group, Modal, NativeSelect, PasswordInput, Stack, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getAllSpecialities } from "../../../services/SpecialityService.ts";
import { notifications } from "@mantine/notifications";
import { addStudent } from "../../../services/StudentService.ts";
import type { StudentDto } from "../../../models/Student.ts";
import axios from "axios";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddStudentModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageStudents");
    const { data } = useFetch(getAllSpecialities, []);
    const specialities = data ?? [];

    const [name, setName] = useState<string>("");
    const [surname, setSurname] = useState<string>("");
    const [patronymic, setPatronymic] = useState<string>("");
    const [studyYear, setStudyYear] = useState<number | undefined>();
    const [specialityId, setSpecialityId] = useState<string | undefined>();
    const [recordBookNum, setRecordBookNum] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const [isAdding, setIsAdding] = useState(false);

    const resetForm = () => {
        setName("");
        setSurname("");
        setPatronymic("");
        setStudyYear(undefined);
        setSpecialityId(undefined);
        setRecordBookNum("");
        setEmail("");
        setPassword("");
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
        if (surname.trim().length < 2) {
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
        if (!studyYear) {
            notifications.show({
                title: t("error"),
                message: t("selectStudyYear"),
                color: "red",
            });
            return false;
        }
        if (!specialityId) {
            notifications.show({
                title: t("error"),
                message: t("selectSpeciality"),
                color: "red",
            });
            return false;
        }
        if (recordBookNum.trim().length < 3) {
            notifications.show({
                title: t("error"),
                message: t("recordBookTooShort", { min: 3 }),
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
        if (password.length < 7) {
            notifications.show({
                title: t("error"),
                message: t("passwordTooShort", { min: 7 }),
                color: "red",
            });
            return false;
        }
        return true;
    }, [name, surname, studyYear, specialityId, recordBookNum, email, password, t]);

    const handleSubmit = useCallback(async () => {
        if (!validateInput())
            return;

        const dto: StudentDto = {
            firstName: name,
            lastName: surname,
            patronymic: patronymic,
            recordBookNumber: recordBookNum,
            email: email,
            password: password,
            specialityId: specialityId,
            studyYear: studyYear
        };

        setIsAdding(true);
        try {
            await addStudent(dto);
            notifications.show({
                title: t("success"),
                message: t("studentAdded", { fstName: name, lstName: surname }),
                color: 'green',
            });
            close();
            resetForm();
        } catch (err: unknown) {
            let errorMessage = t("unknownAddError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data == "string" && data.trim()) {
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
    }, [validateInput, t, close]);

    return (
        <Modal
            centered
            title={t("addStudent")}
            opened={opened}
            onClose={close}
        >
            <Stack p="xs" pt="0">
                <Group grow>
                    <TextInput
                        label={t("name")}
                        withAsterisk
                        placeholder={t("studentName")}
                        value={name}
                        onChange={(e) => setName(e.currentTarget.value)}
                    />
                    <TextInput
                        label={t("surname")}
                        withAsterisk
                        placeholder={t("studentSurname")}
                        value={surname}
                        onChange={(e) => setSurname(e.currentTarget.value)}
                    />
                </Group>
                <TextInput
                    label={t("patronymic")}
                    withAsterisk
                    placeholder={t("studentPatronymic")}
                    value={patronymic}
                    onChange={(e) => setPatronymic(e.currentTarget.value)}
                />
                <Group grow>
                    <NativeSelect
                        label={t("studyYear")}
                        withAsterisk
                        data={[
                            { value: '', label: t("studyYear") },
                            { value: '1', label: t("year1") },
                            { value: '2', label: t("year2") },
                            { value: '3', label: t("year3") },
                            { value: '4', label: t("year4") },
                        ]}
                        value={studyYear?.toString() ?? undefined}
                        onChange={(e) => setStudyYear(Number(e.currentTarget.value))}
                    />
                    <NativeSelect
                        label={t("speciality")}
                        withAsterisk
                        data={[
                            { value: '', label: t("speciality") },
                            ...specialities.map((s) => ({ value: s.id, label: s.name })),
                        ]}
                        value={specialityId ?? undefined}
                        onChange={(e) => setSpecialityId(e.currentTarget.value)}
                    />
                </Group>
                <TextInput
                    label={t("recordBookNum")}
                    withAsterisk
                    placeholder={t("studentRecordBookNum")}
                    value={recordBookNum}
                    onChange={(e) => setRecordBookNum(e.currentTarget.value)}
                />
                <TextInput
                    label={t("email")}
                    withAsterisk
                    placeholder={t("studentEmail")}
                    value={email}
                    onChange={(e) => setEmail(e.currentTarget.value)}
                />
                <PasswordInput
                    label={t("password")}
                    withAsterisk
                    placeholder={t("studentPassword")}
                    value={password}
                    onChange={(e) => setPassword(e.currentTarget.value)}
                />
                <Button
                    variant="filled"
                    color="green"
                    mt="xs"
                    onClick={handleSubmit}
                    loading={isAdding}
                    disabled={isAdding}
                >
                    {t("addStudent")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default AddStudentModal