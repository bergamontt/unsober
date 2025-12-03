import { Button, Modal, Stack, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useTranslation } from "react-i18next";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import type { BuildingDto } from "../../../models/Building.js";
import { validateBuildingDto } from "../../../validation/buildingValidator.js";
import { addBuilding } from "../../../services/BuildingService.js";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddBuildingModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("manageBuildings");
    const { t: errT } = useTranslation("buildingErrors");

    const [name, setName] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [latitude, setLatitude] = useState<number | undefined>();
    const [longitude, setLongitude] = useState<number | undefined>();
    const [isAdding, setIsAdding] = useState(false);

    const resetForm = () => {
        setName("");
        setAddress("");
        setLatitude(undefined);
        setLongitude(undefined);
    };

    const handleSubmit = useCallback(async () => {
        const dto: BuildingDto = {
            name,
            address,
            latitude,
            longitude
        };

        const errs = validateBuildingDto(dto, (k, p) => errT(k, p));
        if(errs.length > 0){
            notifications.show({
                title: t("error"),
                message: errs.join('\n'),
                color: "red",
            });
            return;
        }

        setIsAdding(true);
        try {
            await addBuilding(dto);
            notifications.show({
                title: t("success"),
                message: t("buildingAdded", { name }),
                color: "green",
            });
            close();
            resetForm();
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownAddError");
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
            setIsAdding(false);
        }
    }, [t, errT, close, name, address, latitude, longitude]);

    return (
        <Modal
            centered
            title={t("addBuilding")}
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
                    onClick={handleSubmit}
                    loading={isAdding}
                    disabled={isAdding}
                >
                    {t("addBuilding")}
                </Button>
            </Stack>
        </Modal>
    );
}

export default AddBuildingModal;
