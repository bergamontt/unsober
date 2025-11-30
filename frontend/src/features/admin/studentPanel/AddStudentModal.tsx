import { Button, Group, Modal, NativeSelect, PasswordInput, Stack, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getAllSpecialities } from "../../../services/SpecialityService.ts";
import { notifications } from "@mantine/notifications";
import { addStudent } from "../../../services/StudentService.ts";
import { StudentStatus, type StudentDto } from "../../../models/Student.ts";
import axios from "axios";
import { validateStudentDto } from "../../../validation/studentValidator.ts";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddStudentModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageStudents");
    const { t: errT } = useTranslation("studentErrors");
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
    const [status, setStatus] = useState<StudentStatus | undefined>();

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
        setStatus(undefined)
    };

    const handleSubmit = useCallback(async () => {
        const dto: StudentDto = {
            firstName: name,
            lastName: surname,
            patronymic: patronymic,
            recordBookNumber: recordBookNum,
            email: email,
            password: password,
            specialityId: specialityId,
            studyYear: studyYear,
            status: status
        };

        const errs = validateStudentDto(dto, (k, p) => errT(k, p));
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
            await addStudent(dto);
            notifications.show({
                title: t("success"),
                message: t("studentAdded", { fstName: name, lstName: surname }),
                color: 'green',
            });
            close();
            resetForm();
            window.location.reload();
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
    }, [t, errT, close, name, surname, patronymic,
        studyYear, specialityId, recordBookNum, email, password, status]);

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
                <Group grow>
                    <TextInput
                        label={t("recordBookNum")}
                        withAsterisk
                        placeholder={t("studentRecordBookNum")}
                        value={recordBookNum}
                        onChange={(e) => setRecordBookNum(e.currentTarget.value)}
                    />
                    <NativeSelect
                        label={t("status")}
                        withAsterisk
                        data={[
                            { value: '', label: t("studentStatus") },
                            { value: StudentStatus.STUDYING, label: t(StudentStatus.STUDYING) },
                            { value: StudentStatus.EXPELLED, label: t(StudentStatus.EXPELLED) },
                            { value: StudentStatus.GRADUATED, label: t(StudentStatus.GRADUATED) },
                        ]}
                        value={status?.toString() ?? undefined}
                        onChange={(e) => {
                            const v = e.currentTarget.value;
                            setStatus(v === '' ? undefined : (v as StudentStatus));
                        }}
                    />
                </Group>
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