import { Button, Group, Modal, Stack, TextInput } from "@mantine/core";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddTeacherModal({ opened, close }: AddModalProps) {
    return (
        <Modal
            centered
            title="Додати викладача"
            opened={opened}
            onClose={close}
        >
            <Stack p="xs" pt="0">
                <Group grow>
                    <TextInput
                        label="Ім'я"
                        withAsterisk
                        placeholder="Ім'я викладача"
                    />
                    <TextInput
                        label="Прізвище"
                        withAsterisk
                        placeholder="Прізвище викладача"
                    />
                </Group>
                <TextInput
                    label="По батькові"
                    withAsterisk
                    placeholder="По батькові викладача"
                />
                <TextInput
                    label="Електронна пошта"
                    withAsterisk
                    placeholder="Електронна пошта викладача"
                />
                <Button
                    variant="filled"
                    color="green"
                    mt="xs"
                >
                    Додати викладача
                </Button>
            </Stack>
        </Modal>
    );
}

export default AddTeacherModal