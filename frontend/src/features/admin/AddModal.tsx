import { Button, Group, Modal, NativeSelect, PasswordInput, Stack, TextInput } from "@mantine/core";

type AddModalProps = {
    opened: boolean;
    close: () => void;
}

function AddModal({opened, close} : AddModalProps) {
    return (
        <Modal
            centered
            title="Додати студента"
            opened={opened}
            onClose={close}
        >
            <Stack m="xs">
                <Group grow>
                    <TextInput
                        label="Ім'я"
                        withAsterisk
                        placeholder="Ім'я студента"
                    />
                    <TextInput
                        label="Прізвище"
                        withAsterisk
                        placeholder="Прізвище студента"
                    />
                </Group>
                <TextInput
                    label="По батькові"
                    withAsterisk
                    placeholder="По батькові студента"
                />
                <Group grow>
                    <NativeSelect
                        label="Рік навчання"
                        withAsterisk
                        data={['1-ий рік', '2-ий рік', '3-ий рік', '4-ий рік']}
                    />
                    <NativeSelect
                        label="Спеціальність"
                        withAsterisk
                        data={['Кібербезпека', 'Прикладна математика', 'Робототехніка']}
                    />
                </Group>
                <TextInput
                    label="Номер залікової книжки"
                    withAsterisk
                    placeholder="Номер залікової книжки студента"
                />
                <TextInput
                    label="Пошта"
                    withAsterisk
                    placeholder="Пошта студента"
                />
                <PasswordInput
                    label="Пароль"
                    withAsterisk
                    placeholder="Пароль студента"
                />
                <Button
                    variant="filled"
                    color="green"
                    mt="xs"
                >
                    Додати
                </Button>
            </Stack>
        </Modal>
    );
}

export default AddModal