import { Modal, Stack, Group, TextInput, Button } from "@mantine/core";

type EditModalProps = {
    opened: boolean;
    close: () => void;
}

function EditTeacherModal({ opened, close }: EditModalProps) {
    return (
        <Modal
            centered
            title="Редагувати викладача"
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
                    Зберегти 
                </Button>
            </Stack>
        </Modal>
    );
}

export default EditTeacherModal