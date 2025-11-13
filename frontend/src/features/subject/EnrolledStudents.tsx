import { Table } from "@mantine/core";

function EnrolledStudents() {
    return(
        <Table highlightOnHover withTableBorder withColumnBorders captionSide="top">
            <Table.Caption>
                Список студентів
            </Table.Caption>
            
            <Table.Thead>
                <Table.Tr bg="#F8F9FA">
                    <Table.Th fw="normal">№</Table.Th>
                    <Table.Th fw="normal">ПІБ</Table.Th>
                    <Table.Th fw="normal">Курс</Table.Th>
                    <Table.Th fw="normal">Спеціальність</Table.Th>
                    <Table.Th fw="normal">Тип</Table.Th>
                    <Table.Th fw="normal">Статус</Table.Th>
                    <Table.Th fw="normal">Група</Table.Th>
                </Table.Tr>
            </Table.Thead>
            
            <Table.Tbody>
                <Table.Tr key="fooo">
                    <Table.Td>1</Table.Td>
                    <Table.Td>Степаненко Назар Романович</Table.Td>
                    <Table.Td>2</Table.Td>
                    <Table.Td>Інженерія програмного забезпечення</Table.Td>
                    <Table.Td>Вільного вибору</Table.Td>
                    <Table.Td>Примусово</Table.Td>
                    <Table.Td>1</Table.Td>
                </Table.Tr>
            </Table.Tbody>
        </Table>
    );
}

export default EnrolledStudents