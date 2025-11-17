import { ActionIcon, Group, Stack, Table, Tooltip } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import Icon from "../../common/Icon";
import Searchbar from "../../common/Searchbar";
import AddModal from "./AddModal";
import DeleteModal from "./DeleteModal";
import EditModal from "./EditModal";
import edit from '../../assets/edit.svg';
import del from '../../assets/delete.svg';
import plus from '../../assets/plus.svg'
import { useAuthStore } from "../../hooks/authStore";
import { useNavigate } from "react-router";
import { useEffect } from "react";

function StudentPanel() {
    const { isAuthenticated, loadingAuth } = useAuthStore();
    const navigate = useNavigate();
    useEffect(() => {
        if (!isAuthenticated && !loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, navigate]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);
    
    const students = [
        { name: "Іваненко Іван Іванович", email: "ivanenko@example.com", year: 2, specialty: "Комп'ютерні науки" },
        { name: "Петренко Ольга Сергіївна", email: "petrenko@example.com", year: 3, specialty: "Робототехніка" },
        { name: "Шевченко Андрій Миколайович", email: "shevchenko@example.com", year: 1, specialty: "Кібербезпека" },
    ];

    const rows = students.map((student, index) => (
        <Table.Tr key={index}>
            <Table.Td>{student.name}</Table.Td>
            <Table.Td>{student.email}</Table.Td>
            <Table.Td>{student.year}</Table.Td>
            <Table.Td>{student.specialty}</Table.Td>
            <Table.Td>
                <Group gap="xs" justify="center">
                    <Tooltip label="Редагувати">
                        <ActionIcon
                            variant="light"
                            color="indigo"
                            onClick={openEdit}
                        >
                            <Icon src={edit}/>
                        </ActionIcon>
                    </Tooltip>
                    <Tooltip label="Видалити">
                        <ActionIcon
                            variant="light"
                            color="red"
                            onClick={openDelete}
                        >
                            <Icon src={del}/>
                        </ActionIcon>
                    </Tooltip>
                </Group>
            </Table.Td>
        </Table.Tr>
    ));

    return (
        <Stack>
            <Group
                grow
                align="flex-end"
            >
                <Group grow>
                    <Searchbar 
                        label="Пошук студентів"
                        description="Введіть електрону пошту студента"
                        placeholder="Електронна пошта"
                    />
                </Group>
                <Tooltip label="Додати">
                    <ActionIcon
                        onClick={openAdd}
                        variant="filled"
                        color="green"
                        size="xl"
                        maw="xl"
                    >
                        <Icon src={plus} size="1.5em"/>
                    </ActionIcon>
                </Tooltip>
            </Group>
            <Table striped highlightOnHover withTableBorder>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th>ПІБ</Table.Th>
                        <Table.Th>Електронна пошта</Table.Th>
                        <Table.Th>Рік навчання</Table.Th>
                        <Table.Th>Спеціальність</Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>{rows}</Table.Tbody>
            </Table>
            <AddModal
                opened={addOpened}
                close={closeAdd}
            />
            <DeleteModal
                opened={deleteOpened}
                close={closeDelete}
            />
            <EditModal
                opened={editOpened}
                close={closeEdit}
            />
        </Stack>
    );
}

export default StudentPanel;