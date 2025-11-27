import { useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getDepartmentById, deleteDepartment } from "../../../services/DepartmentService.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import DeleteModal from "../DeleteModal.tsx";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
    departmentId: string | null;
}

function DeleteDepartmentModal({ opened, close, departmentId }: DeleteModalProps) {
    const { t } = useTranslation("manageDepartments");
    const { data: department } = useFetch(getDepartmentById, [departmentId]);
    const [isDeleting, setIsDeleting] = useState<boolean>(false);

    if (!departmentId || !department) 
        return <></>;

    const handleDelete = async () => {
        setIsDeleting(true);
        try {
            await deleteDepartment(departmentId);
            notifications.show({
                title: t("success"),
                message: t("departmentDeleted", { name: department.name }),
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

export default DeleteDepartmentModal;
