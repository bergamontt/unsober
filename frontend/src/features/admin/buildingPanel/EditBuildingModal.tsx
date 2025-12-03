import { Button, Modal, Stack, TextInput } from "@mantine/core";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import { getBuildingById, updateBuilding } from "../../../services/BuildingService.ts";
import { validateBuildingDto } from "../../../validation/buildingValidator.ts";
import type { BuildingDto } from "../../../models/Building.ts";

type EditModalProps = {
    opened: boolean;
    close: () => void;
    buildingId: string | null;
}

function EditBuildingModal({ opened, close, buildingId }: EditModalProps) {
    const { t } = useTranslation("manageBuildings");
    const { t: errT } = useTranslation("buildingErrors");
    const { data: building } = useFetch(getBuildingById, [buildingId]);

    const [name, setName] = useState<string>(building?.name ?? "");
    const [address, setAddress] = useState<string>(building?.address ?? "");
    const [latitude, setLatitude] = useState<number | undefined>(building?.latitude);
    const [longitude, setLongitude] = useState<number | undefined>(building?.longitude);

    const [isSaving, setIsSaving] = useState(false);

    useEffect(() => {
        if (!building)
            return;
        setName(building.name);
        setAddress(building.address);
        setLatitude(building.latitude);
        setLongitude(building.longitude);
    }, [building]);

    const resetForm = () => {
        setName("");
        setAddress("");
        setLatitude(undefined);
        setLongitude(undefined);
    };

    const handleSave = useCallback(async () => {
        const dto: BuildingDto = {
            name,
            address,
            latitude,
            longitude
        };

        const errs = validateBuildingDto(dto, (k, p) => errT(k, p));
        if (errs.length > 0) {
            notifications.show({
                title: t("error"),
                message: errs.join('\n'),
                color: "red",
            });
            return;
        }

        setIsSaving(true);
        try {
            if (!buildingId)
                return;
            await updateBuilding(buildingId, dto);
            notifications.show({
                title: t("success"),
                message: t("buildingUpdated"),
                color: "green",
            });
            close();
            resetForm();
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownUpdateError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim())
                    errorMessage = data;
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: "red",
            });
        } finally {
            setIsSaving(false);
        }
    }, [t, errT, close, buildingId, name, address, latitude, longitude]);

    if (!buildingId)
        return <></>;

    return (
        <Modal
            centered
            title={t("editBuilding")}
            opened={opened}
            onClose={close}
        >
            <Stack p="xs" pt="0">
                <TextInput
                    label={t("name")}
                    withAsterisk
                    placeholder={t("buildingName")}
                    value={name}
                    onChange={(e) => setName(e.currentTarget.value)}
                />
                <TextInput
                    label={t("address")}
                    withAsterisk
                    placeholder={t("buildingAddress")}
                    value={address}
                    onChange={(e) => setAddress(e.currentTarget.value)}
                />
                <TextInput
                    label={t("latitude")}
                    withAsterisk
                    placeholder={t("buildingLatitude")}
                    value={latitude}
                    onChange={(e) => setLatitude(Number(e.currentTarget.value))}
                />
                <TextInput
                    label={t("longitude")}
                    withAsterisk
                    placeholder={t("buildingLongitude")}
                    value={longitude}
                    onChange={(e) => setLongitude(Number(e.currentTarget.value))}
                />
                <Button
                    variant="filled"
                    color="green"
                    onClick={handleSave}
                    loading={isSaving}
                    disabled={isSaving}
                >
                    {t("saveChanges")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default EditBuildingModal;
