import { useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import DeleteModal from "../DeleteModal.tsx";
import { deleteCourse, getCourseById } from "../../../services/CourseService.ts";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
    courseId: string | null;
}

function DeleteCourseModal({ opened, close, courseId }: DeleteModalProps) {
    const { t } = useTranslation("coursePanel");
    const { data: course } = useFetch(getCourseById, [courseId]);
    const [isDeleting, setIsDeleting] = useState<boolean>(false);

    if (!courseId || !course) 
        return <></>;

    const handleDelete = async () => {
        setIsDeleting(true);
        try {
            await deleteCourse(courseId);
            notifications.show({
                title: t("success"),
                message: t("courseDeleted", { name: course.subject.name }),
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
            title={t("deleteCourse")}
        />
    );
}

export default DeleteCourseModal;
