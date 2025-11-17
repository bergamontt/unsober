import { Button, Group, Modal, Stack, Text } from "@mantine/core";
import { useTranslation } from "react-i18next";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
}

function DeleteStudentModal({opened, close} : DeleteModalProps) {
    const { t } = useTranslation("manageStudents");
    return(
        <Modal
            size="xs"
            centered
            opened={opened}
            onClose={close}
            withCloseButton={false}
        >
            <Stack
                mt="xs"
            >
                <Text>
                    {t("areYouSure")}
                </Text>
                <Group grow>
                    <Button
                        variant="default"
                    >
                        {t("cancel")}
                    </Button>
                    <Button
                        color="red"
                    >
                        {t("delete")}
                    </Button>
                </Group>
            </Stack>
        </Modal>
    );
}

export default DeleteStudentModal