import { Button, Group, Modal, Stack, Text } from "@mantine/core";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getStudentById, deleteStudent } from "../../../services/StudentService.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
    studentId: string | null;
}

function DeleteStudentModal({ opened, close, studentId }: DeleteModalProps) {
    const { t } = useTranslation("manageStudents");
    const { data: student } = useFetch(getStudentById, [studentId]);
    const [isDeleting, setIsDeleting] = useState<boolean>(false);

    if(!studentId || !student)
        return <></>;

    const handleDelete = async () => {
        setIsDeleting(true);
        try {
            await deleteStudent(studentId);
            notifications.show({
                title: t("success"),
                message: t("studentDeleted", {fstName: student.firstName, lstName: student.lastName}),
                color: "green",
            });
            close();
        } catch (err: unknown) {
            let errorMessage = t("unknownDeleteError");
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
            setIsDeleting(false);
        }
    };

    return (
        <Modal
            size="xs"
            centered
            opened={opened}
            onClose={close}
            withCloseButton={false}
        >
            <Stack mt="xs">
                <Text>
                    {t("areYouSure")}
                </Text>
                <Group grow>
                    <Button
                        variant="default"
                        onClick={() => close()}
                        disabled={isDeleting}
                    >
                        {t("cancel")}
                    </Button>
                    <Button
                        color="red"
                        onClick={handleDelete}
                        disabled={isDeleting}
                        loading={isDeleting}
                    >
                        {t("delete")}
                    </Button>
                </Group>
            </Stack>
        </Modal>
    );
}

export default DeleteStudentModal;
