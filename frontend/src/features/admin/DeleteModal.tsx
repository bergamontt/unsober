import { Button, Group, Modal, Stack, Text } from "@mantine/core";

type DeleteModalProps = {
    opened: boolean;
    close: () => void;
}

function DeleteModal({opened, close} : DeleteModalProps) {
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
                    Ви дійсно впевнені, що хочете виконати операцію видалення?
                    Ця операція є незворотньою.
                </Text>
                <Group grow>
                    <Button
                        variant="default"
                    >
                        Відмінити
                    </Button>
                    <Button
                        color="red"
                    >
                        Видалити
                    </Button>
                </Group>
            </Stack>
        </Modal>
    );
}

export default DeleteModal