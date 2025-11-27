import { useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { getSpecialityById, deleteSpeciality } from "../../../services/SpecialityService.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import DeleteModal from "../DeleteModal.tsx";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
    specialityId: string | null;
}

function DeleteSpecialityModal({ opened, close, specialityId }: DeleteModalProps) {
    const { t } = useTranslation("manageSpecialities");
    const { data: speciality } = useFetch(getSpecialityById, [specialityId]);
    const [isDeleting, setIsDeleting] = useState<boolean>(false);

    if (!specialityId || !speciality) 
        return <></>;

    const handleDelete = async () => {
        setIsDeleting(true);
        try {
            await deleteSpeciality(specialityId);
            notifications.show({
                title: t("success"),
                message: t("specialityDeleted", { name: speciality.name }),
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

export default DeleteSpecialityModal;
