import { Button, Group, Modal, NativeSelect, NumberInput, Stack, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import { notifications } from "@mantine/notifications";
import { addSubject } from "../../../services/SubjectService.ts";
import { type SubjectDto, Term, EducationLevel } from "../../../models/Subject.ts";
import axios from "axios";
import { validateSubjectDto } from "../../../validation/subjectValidator.ts";

type AddModalProps = {
    opened: boolean;
    close: () => void;
};

function AddSubjectModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageSubjects");
    const { t: errT } = useTranslation("subjectErrors");

    const [name, setName] = useState<string>("");
    const [annotation, setAnnotation] = useState<string>("");
    const [facultyName, setFacultyName] = useState<string>("");
    const [departmentName, setDepartmentName] = useState<string>("");
    const [educationLevel, setEducationLevel] = useState<EducationLevel | undefined>();
    const [credits, setCredits] = useState<number | undefined>(undefined);
    const [hoursPerWeek, setHoursPerWeek] = useState<number | undefined>(undefined);
    const [term, setTerm] = useState<Term | undefined>();

    const [isAdding, setIsAdding] = useState(false);

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

    const handleSubmit = useCallback(async () => {
        const dto: SubjectDto = {
            name,
            annotation: annotation || undefined,
            facultyName: facultyName || undefined,
            departmentName: departmentName || undefined,
            educationLevel: educationLevel,
            credits: credits,
            hoursPerWeek: hoursPerWeek,
            term: term,
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

        setIsAdding(true);
        try {
            await addSubject(dto);
            notifications.show({
                title: t("success"),
                message: t("subjectAdded", { name }),
                color: "green"
            });
            close();
            resetForm();
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownAddError");
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
            setIsAdding(false);
        }
    }, [t, errT, close, name, annotation, facultyName,
        departmentName, educationLevel, credits, hoursPerWeek, term]);

    return (
        <Modal
            centered
            title={t("addSubject")}
            opened={opened}
            onClose={close}
        >
            <Stack p="xs" pt="0">
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
                    onClick={handleSubmit}
                    loading={isAdding}
                    disabled={isAdding}
                >
                    {t("addSubject")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default AddSubjectModal;
