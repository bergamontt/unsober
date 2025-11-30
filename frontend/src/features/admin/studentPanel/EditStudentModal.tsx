import { Button, Group, Modal, NativeSelect, PasswordInput, Stack, TextInput } from "@mantine/core";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getAllSpecialities } from "../../../services/SpecialityService.ts";
import { notifications } from "@mantine/notifications";
import { getStudentById, updateStudent } from "../../../services/StudentService.ts";
import { StudentStatus, type StudentDto } from "../../../models/Student.ts";
import axios from "axios";
import { validateStudentDto } from "../../../validation/studentValidator.ts";

type EditModalProps = {
    opened: boolean;
    close: () => void;
    studentId: string | null;
}

function EditStudentModal({ opened, close, studentId }: EditModalProps) {
    const { t } = useTranslation("manageStudents");
    const { t: errT } = useTranslation("studentErrors");
    const { data } = useFetch(getAllSpecialities, []);
    const specialities = data ?? [];
    const { data: student } = useFetch(getStudentById, [studentId]);

    const [name, setName] = useState<string>(student?.firstName ?? "");
    const [surname, setSurname] = useState<string>(student?.lastName ?? "");
    const [patronymic, setPatronymic] = useState<string>(student?.patronymic ?? "");
    const [studyYear, setStudyYear] = useState<number | undefined>(student?.studyYear);
    const [specialityId, setSpecialityId] = useState<string | undefined>(student?.speciality.id);
    const [recordBookNum, setRecordBookNum] = useState<string>(student?.recordBookNumber ?? "");
    const [email, setEmail] = useState<string>(student?.email ?? "");
    const [password, setPassword] = useState<string>("");
    const [status, setStatus] = useState<StudentStatus | undefined>();

    const [isSaving, setIsSaving] = useState(false);

    useEffect(() => {
        if (!student)
            return;
        setName(student.firstName ?? "");
        setSurname(student.lastName ?? "");
        setPatronymic(student.patronymic ?? "");
        setStudyYear(student.studyYear);
        setSpecialityId(student.speciality?.id);
        setRecordBookNum(student.recordBookNumber ?? "");
        setEmail(student.email ?? "");
        setStatus(student.status);
    }, [student]);

    const resetForm = () => {
        setName("");
        setSurname("");
        setPatronymic("");
        setStudyYear(undefined);
        setSpecialityId(undefined);
        setRecordBookNum("");
        setEmail("");
        setPassword("");
        setStatus(undefined);
    };

    const handleSave = useCallback(async () => {
        const dto: StudentDto = {
            firstName: name,
            lastName: surname,
            patronymic: patronymic,
            recordBookNumber: recordBookNum,
            email: email,
            password: password || undefined,
            specialityId: specialityId,
            studyYear: studyYear,
            status: status
        };

        const errs = validateStudentDto(dto, (k, p) => errT(k, p), !password);
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
            if (!studentId)
                return;
            await updateStudent(studentId, dto);
            notifications.show({
                title: t("success"),
                message: t("studentUpdated"),
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
    }, [t, errT, close, name, surname, patronymic,
        studyYear, specialityId, recordBookNum, email, password, status]);

    if (!studentId)
        return <></>;

    return (
        <Modal
            centered
            title={t("editStudent")}
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
                    label={t("studentEmail")}
                    withAsterisk
                    placeholder={t("studentEmail")}
                    value={email}
                    onChange={(e) => setEmail(e.currentTarget.value)}
                />
                <PasswordInput
                    label={t("password")}
                    placeholder={t("studentPassword")}
                    value={password}
                    onChange={(e) => setPassword(e.currentTarget.value)}
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

export default EditStudentModal;
