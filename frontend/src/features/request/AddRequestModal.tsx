import { Modal } from "@mantine/core";
import { useTranslation } from "react-i18next";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddRequestModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("studentRequests");

    return (
        <Modal
            centered
            title={t("addRequest")}
            opened={opened}
            onClose={close}
        >
        </Modal>
    );
}

export default AddRequestModal;
