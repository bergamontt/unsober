import { useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getFacultyById, deleteFaculty } from "../../../services/FacultyService.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import DeleteModal from "../DeleteModal.tsx";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
    facultyId: string | null;
}

function DeleteFacultyModal({ opened, close, facultyId }: DeleteModalProps) {
    const { t } = useTranslation("manageFaculties");
    const { data: faculty } = useFetch(getFacultyById, [facultyId]);
    const [isDeleting, setIsDeleting] = useState<boolean>(false);

    if (!facultyId || !faculty) 
        return <></>;

    const handleDelete = async () => {
        setIsDeleting(true);
        try {
            await deleteFaculty(facultyId);
            notifications.show({
                title: t("success"),
                message: t("facultyDeleted", { name: faculty.name }),
                color: "green",
            });
            close();
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownDeleteError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim()) errorMessage = data;
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

export default DeleteFacultyModal;
