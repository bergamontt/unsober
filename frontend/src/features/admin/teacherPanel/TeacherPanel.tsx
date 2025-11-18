import { Stack, Table, } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import RowActions from "../RowActions";
import SearchWithAdd from "../SearchWithAdd";
import AddTeacherModal from "./AddTeacherModal";
import DeleteTeacherModal from "./DeleteTeacherModal";
import EditTeacherModal from "./EditTeacherModal";

function TeacherPanel() {

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);

    const teachers = [
        { name: "Іваненко Іван Іванович", email: "ivanenko@example.com"},
        { name: "Петренко Ольга Сергіївна", email: "petrenko@example.com"},
        { name: "Шевченко Андрій Миколайович", email: "shevchenko@example.com"}
    ];

    const rows = teachers.map((teacher, index) => (
        <Table.Tr key={index}>
            <Table.Td>{`${teacher.name}`}</Table.Td>
            <Table.Td>{teacher.email}</Table.Td>
            <Table.Td>
                <RowActions
                    onEdit={() => {
                        openEdit();
                    }}
                    onDelete={() => {
                        openDelete();
                    }}
                />
            </Table.Td>
        </Table.Tr>
    ));

    return(
        <Stack>
            <SearchWithAdd
                label="Пошук викладачів"
                description="Введіть електронну пошту викладача"
                placeholder="Електронна пошта"
                onAdd={openAdd}
            />
            <Table striped highlightOnHover withTableBorder>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th>ПІБ</Table.Th>
                        <Table.Th>Електронна пошта</Table.Th>
                        <Table.Th></Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>{rows}</Table.Tbody>
            </Table>
            <AddTeacherModal
                opened={addOpened}
                close={closeAdd}
            />
            <DeleteTeacherModal
                opened={deleteOpened}
                close={closeDelete}
            />
            <EditTeacherModal
                opened={editOpened}
                close={closeEdit}
            />
        </Stack>
    );
}

export default TeacherPanel