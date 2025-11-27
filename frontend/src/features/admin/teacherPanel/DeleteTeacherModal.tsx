import { useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getTeacherById, deleteTeacher } from "../../../services/TeacherService.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import DeleteModal from "../DeleteModal.tsx";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
    teacherId: string | null;
}

function DeleteTeacherModal({ opened, close, teacherId }: DeleteModalProps) {
    const { t } = useTranslation("manageTeachers");
    const { data: teacher } = useFetch(getTeacherById, [teacherId]);
    const [isDeleting, setIsDeleting] = useState<boolean>(false);

    if(!teacherId || !teacher)
        return <></>;

    const handleDelete = async () => {
        setIsDeleting(true);
        try {
            await deleteTeacher(teacherId);
            notifications.show({
                title: t("success"),
                message: t("teacherDeleted", { fstName: teacher.firstName, lstName: teacher.lastName }),
                color: "green",
            });
            close();
            window.location.reload();
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

export default DeleteTeacherModal;
