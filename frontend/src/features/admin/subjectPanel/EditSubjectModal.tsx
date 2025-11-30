import { Button, Group, Modal, NativeSelect, NumberInput, Stack, TextInput } from "@mantine/core";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { notifications } from "@mantine/notifications";
import { getSubject, updateSubject } from "../../../services/SubjectService.ts";
import { type SubjectDto, Term, EducationLevel } from "../../../models/Subject.ts";
import axios from "axios";
import { validateSubjectDto } from "../../../validation/subjectValidator.ts";

type EditModalProps = {
    opened: boolean;
    close: () => void;
    subjectId: string | null;
};

function EditSubjectModal({ opened, close, subjectId }: EditModalProps) {
    const { t } = useTranslation("manageSubjects");
    const { t: errT } = useTranslation("subjectErrors");
    const { data: subject } = useFetch(getSubject, [subjectId]);

    const [name, setName] = useState<string>(subject?.name ?? "");
    const [annotation, setAnnotation] = useState<string>(subject?.annotation ?? "");
    const [facultyName, setFacultyName] = useState<string>(subject?.facultyName ?? "");
    const [departmentName, setDepartmentName] = useState<string>(subject?.departmentName ?? "");
    const [educationLevel, setEducationLevel] = useState<EducationLevel | undefined>(subject?.educationLevel);
    const [credits, setCredits] = useState<number | undefined>(subject?.credits);
    const [hoursPerWeek, setHoursPerWeek] = useState<number | undefined>(subject?.hoursPerWeek);
    const [term, setTerm] = useState<Term | undefined>(subject?.term);

    const [isSaving, setIsSaving] = useState(false);

    useEffect(() => {
        if (!subject)
            return;
        setName(subject.name ?? "");
        setAnnotation(subject.annotation ?? "");
        setFacultyName(subject.facultyName ?? "");
        setDepartmentName(subject.departmentName ?? "");
        setEducationLevel(subject.educationLevel);
        setCredits(subject.credits);
        setHoursPerWeek(subject.hoursPerWeek);
        setTerm(subject.term);
    }, [subject]);

    const resetForm = () => {
        setName("");
        setAnnotation("");
        setFacultyName("");
        setDepartmentName("");
        setEducationLevel(undefined);
        setCredits(undefined);
        setHoursPerWeek(undefined);
        setTerm(undefined);
    };

    const handleSave = useCallback(async () => {
        const dto: SubjectDto = {
            name,
            annotation: annotation || undefined,
            facultyName: facultyName || undefined,
            departmentName: departmentName || undefined,
            educationLevel,
            credits,
            hoursPerWeek,
            term,
        };

        const errs = validateSubjectDto(dto, (k, p) => errT(k, p));
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
            if (!subjectId) return;
            await updateSubject(subjectId, dto);
            notifications.show({
                title: t("success"),
                message: t("subjectUpdated"),
                color: "green"
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
                color: "red"
            });
        } finally {
            setIsSaving(false);
        }
    }, [t, errT, close, subjectId, name, annotation, facultyName, 
        departmentName, educationLevel, credits, hoursPerWeek, term]);

    if (!subjectId) return <></>;

    return (
        <Modal
            centered
            title={t("editSubject")}
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
                    placeholder={t("subjectName")}
                    value={name}
                    onChange={(e) => setName(e.currentTarget.value)}
                />
                <TextInput
                    label={t("annotation")}
                    placeholder={t("subjectAnnotation")}
                    value={annotation}
                    onChange={(e) => setAnnotation(e.currentTarget.value)}
                />
                <Group grow>
                    <TextInput
                        label={t("facultyName")}
                        placeholder={t("facultyName")}
                        value={facultyName}
                        onChange={(e) => setFacultyName(e.currentTarget.value)}
                    />
                    <TextInput
                        label={t("departmentName")}
                        placeholder={t("departmentName")}
                        value={departmentName}
                        onChange={(e) => setDepartmentName(e.currentTarget.value)}
                    />
                </Group>
                <Group grow>
                    <NativeSelect
                        label={t("educationLevel")}
                        withAsterisk
                        data={[
                            { value: "", label: t("educationLevel") },
                            { value: EducationLevel.BATCHELOR, label: t(EducationLevel.BATCHELOR) },
                            { value: EducationLevel.MASTER, label: t(EducationLevel.MASTER) },
                        ]}
                        value={educationLevel ?? undefined}
                        onChange={(e) => setEducationLevel((e.currentTarget.value as EducationLevel) || undefined)}
                    />
                    <NativeSelect
                        label={t("term")}
                        withAsterisk
                        data={[
                            { value: "", label: t("term") },
                            { value: Term.AUTUMN, label: t(Term.AUTUMN) },
                            { value: Term.SPRING, label: t(Term.SPRING) },
                            { value: Term.SUMMER, label: t(Term.SUMMER) },
                        ]}
                        value={term ?? undefined}
                        onChange={(e) => setTerm((e.currentTarget.value as Term) || undefined)}
                    />
                </Group>
                <Group grow>
                    <NumberInput
                        label={t("credits")}
                        withAsterisk
                        min={0}
                        step={1}
                        value={credits}
                        onChange={(v) => setCredits(Number(v) ?? undefined)}
                        placeholder={t("credits")}
                    />
                    <NumberInput
                        label={t("hoursPerWeek")}
                        withAsterisk
                        min={0}
                        step={1}
                        value={hoursPerWeek}
                        onChange={(v) => setHoursPerWeek(Number(v) ?? undefined)}
                        placeholder={t("hoursPerWeek")}
                    />
                </Group>
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

export default EditSubjectModal;
