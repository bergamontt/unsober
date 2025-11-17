import { Button, Group, Modal, NativeSelect, PasswordInput, Stack, TextInput } from "@mantine/core";
import { useTranslation } from "react-i18next";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddStudentModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageStudents");
    return (
        <Modal
            centered
            title={t("addStudent")}
            opened={opened}
            onClose={close}
        >
            <Stack m="xs">
                <Group grow>
                    <TextInput
                        label={t("name")}
                        withAsterisk
                        placeholder={t("studentName")}
                    />
                    <TextInput
                        label={t("surname")}
                        withAsterisk
                        placeholder={t("studentSurname")}
                    />
                </Group>
                <TextInput
                    label={t("patronymic")}
                    withAsterisk
                    placeholder={t("studentPatronymic")}
                />
                <Group grow>
                    <NativeSelect
                        label={t("studyYear")}
                        withAsterisk
                        data={[t("year1"), t("year2"), t("year3"), t("year4")]}
                    />
                    <NativeSelect
                        label={t("speciality")}
                        withAsterisk
                        data={[

                        ]}
                    />
                </Group>
                <TextInput
                    label={t("recordBookNum")}
                    withAsterisk
                    placeholder={t("studentRecordBookNum")}
                />
                <TextInput
                    label={t("studentEmail")}
                    withAsterisk
                    placeholder={t("studentEmail")}
                />
                <PasswordInput
                    label={t("password")}
                    withAsterisk
                    placeholder={t("studentPassword")}
                />
                <Button
                    variant="filled"
                    color="green"
                    mt="xs"
                >
                    {t("addStudent")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default AddStudentModal