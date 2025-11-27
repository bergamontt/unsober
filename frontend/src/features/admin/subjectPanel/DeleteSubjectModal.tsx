import { useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getSubject, deleteSubject } from "../../../services/SubjectService.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import DeleteModal from "../DeleteModal.tsx";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
    subjectId: string | null;
};

function DeleteSubjectModal({ opened, close, subjectId }: DeleteModalProps) {
    const { t } = useTranslation("manageSubjects");
    const { data: subject } = useFetch(getSubject, [subjectId]);
    const [isDeleting, setIsDeleting] = useState<boolean>(false);

    if (!subjectId || !subject)
        return <></>;

    const handleDelete = async () => {
        setIsDeleting(true);
        try {
            await deleteSubject(subjectId);
            notifications.show({
                title: t("success"),
                message: t("subjectDeleted", { name: subject.name }),
                color: "green",
            });
            close();
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownDeleteError");
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

export default DeleteSubjectModal;
