import { Button, Group, Modal, Stack, Text } from "@mantine/core";

export type DeleteModalProps = {
    opened: boolean;
    close: () => void;
    message: string;
    denyLabel: string;
    confirmLabel: string;
    loading: boolean;
    onConfirm: () => void;
};

function DeleteModal({
    opened, close, message, denyLabel,
    confirmLabel, loading, onConfirm,
}: DeleteModalProps) {
    return (
        <Modal
            size="xs"
            centered
            opened={opened}
            onClose={close}
            withCloseButton={false}
        >
            <Stack mt="xs">
                <Text>
                    {message}
                </Text>
                <Group grow>
                    <Button
                        variant="default"
                        onClick={close}
                        disabled={loading}
                    >
                        {denyLabel}
                    </Button>
                    <Button
                        color="red"
                        onClick={onConfirm}
                        loading={loading}
                    >
                        {confirmLabel}
                    </Button>
                </Group>
            </Stack>
        </Modal>
    );
}

export default DeleteModal