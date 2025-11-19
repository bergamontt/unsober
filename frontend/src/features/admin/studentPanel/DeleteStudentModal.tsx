import { useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getStudentById, deleteStudent } from "../../../services/StudentService.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import DeleteModal from "../DeleteModal.tsx";

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
        <DeleteModal
            opened={opened}
            close={close}
            loading={isDeleting}
            onConfirm={handleDelete}
            message={t("areYouSure")}
            denyLabel={t("cancel")}
            confirmLabel={t("delete")}
        />
    );
}

export default DeleteStudentModal;
