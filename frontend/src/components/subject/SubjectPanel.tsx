import { Blockquote, Group, Table } from "@mantine/core";
import infoCircle from '../../assets/infoCircle.svg'
import Icon from "../common/Icon.tsx";

function SubjectPanel() {
    return(
        <Group gap="0">
            
            <Blockquote color="indigo" cite="– Aнотація" iconSize={35} icon={<Icon src={infoCircle}/>} mt="0.8em">
                Методика побудови комп’ютерних алгоритмів.
                Математичний апарат аналізу алгоритмів, поняття ефективності алгоритмів,
                базові алгоритми розв’язання типових класів задач.
                Основні методи розв’язання задач:
                «розділяй і пануй», бектрекінг, символьні обчислення, використання евристик. 
            </Blockquote>
            
            <Table mt="1em" variant="vertical" withTableBorder highlightOnHover>                                
                <Table.Caption>Обмеження</Table.Caption>
                <Table.Tbody>
                    <Table.Tr>
                        <Table.Th w={300}>Факультет</Table.Th>
                        <Table.Td>Факультет інформатики</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>Кафедра</Table.Th>
                        <Table.Td>Кафедра інформатики</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>Освітній рівень</Table.Th>
                        <Table.Td>Бакалавр</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>Навчальний рік</Table.Th>
                        <Table.Td>2025–2026</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>Семестр</Table.Th>
                        <Table.Td>Осінній</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>Рекомендований курс</Table.Th>
                        <Table.Td>2 рік</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>Кількість кредитів</Table.Th>
                        <Table.Td>5.0 кред.</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>Тижневі години</Table.Th>
                        <Table.Td>4 год/тижд.</Table.Td>
                    </Table.Tr>
            
                </Table.Tbody>
            </Table>
            
            <Table mt="1em" variant="vertical" withTableBorder highlightOnHover>
                <Table.Tbody>

                    <Table.Tr>
                        <Table.Th w={300}>Максимальна кількість студентів</Table.Th>
                        <Table.Td>Необмежено</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>Кількість студентів у групі</Table.Th>
                        <Table.Td>9-12</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>Кількість записаних студентів</Table.Th>
                        <Table.Td>66</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>Кількість груп</Table.Th>
                        <Table.Td>6</Table.Td>
                    </Table.Tr>

                </Table.Tbody>
            </Table>
        </Group>
    );
}

export default SubjectPanel