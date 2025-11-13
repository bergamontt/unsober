import { ActionIcon, Group, Table, Tooltip } from "@mantine/core";
import PageWrapper from "../../common/PageWrapper";
import Searchbar from "../../common/Searchbar";
import Icon from "../../common/Icon";
import edit from '../../assets/edit.svg';
import del from '../../assets/delete.svg';

function AdminPage() {
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
                            onClick={() => {}}
                        >
                            <Icon src={edit}/>
                        </ActionIcon>
                    </Tooltip>
                    <Tooltip label="Відрахувати">
                        <ActionIcon
                            variant="light"
                            color="red"
                            onClick={() => {}}
                        >
                            <Icon src={del}/>
                        </ActionIcon>
                    </Tooltip>
                </Group>
            </Table.Td>
        </Table.Tr>
    ));

    return (
        <PageWrapper>
            <Searchbar 
                label="Пошук студентів"
                description="Введіть електрону пошту студента"
                placeholder="Електронна пошта"
            />
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
        </PageWrapper>
    );
}

export default AdminPage