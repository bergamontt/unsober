import { useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import DeleteModal from "../DeleteModal.tsx";
import { deleteBuilding, getBuildingById } from "../../../services/BuildingService.ts";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
    buildingId: string | null;
}

function DeleteBuildingModal({ opened, close, buildingId }: DeleteModalProps) {
    const { t } = useTranslation("manageBuildings");
    const { data: building } = useFetch(getBuildingById, [buildingId]);
    const [isDeleting, setIsDeleting] = useState<boolean>(false);

    if (!buildingId || !building)
        return <></>;

    const handleDelete = async () => {
        setIsDeleting(true);
        try {
            await deleteBuilding(buildingId);
            notifications.show({
                title: t("success"),
                message: t("buildingDeleted", { name: building.name }),
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

export default DeleteBuildingModal;
