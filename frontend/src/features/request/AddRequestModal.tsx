import { Modal, Select } from "@mantine/core";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import AddEnrollmentRequestPanel from "./AddEnrollmentRequestPanel";
import AddWithdrawalRequestPanel from "./AddWithdrawalRequestPanel";

interface AddModalProps {
    opened: boolean;
    close: () => void;
}

type Option = "enrollment" | "withdrawal";

function AddRequestModal({ opened, close }: AddModalProps) {
    const { t } = useTranslation("studentRequests");
    const [option, setOption] = useState<Option>("enrollment");

    return (
        <Modal
            centered
            title={t("addRequest")}
            opened={opened}
            onClose={close}
        >
            <Select
                label={t("requestType")}
                data={[
                    { value: "enrollment", label: t("enrollment") },
                    { value: "withdrawal", label: t("withdrawal") },
                ]}
                value={option}
                onChange={e => setOption(e as Option)}
            />
            {
                option == "enrollment" ?
                    <AddEnrollmentRequestPanel close={close}/>
                    :
                    <AddWithdrawalRequestPanel close={close}/>
            }
        </Modal>
    );
}

export default AddRequestModal;
