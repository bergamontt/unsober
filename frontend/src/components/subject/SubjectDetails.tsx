import type { Subject } from "../../services/models/Subject";
import { Blockquote, Stack, Table } from "@mantine/core";
import { useTranslation } from "react-i18next";
import infoCircle from '../../assets/infoCircle.svg'
import Icon from "../common/Icon";

interface SubjectDetailsProps {
    subject: Subject;
}

function SubjectDetails({subject} : SubjectDetailsProps) {
    const {t} = useTranslation("subjectPreview"); 
    return (
        <Stack align="stretch" justify="center" gap={0} >
            <Blockquote color="indigo" cite={t('annotation')} iconSize={35} icon={<Icon src={infoCircle}/>} mt="0.8em" w="100%">
                {subject?.annotation} 
            </Blockquote>
            
            <Table mt="1em" variant="vertical" withTableBorder highlightOnHover captionSide="top">                                
                <Table.Caption>Загальні деталі курсу</Table.Caption>
                <Table.Tbody>
                    <Table.Tr>
                        <Table.Th w={300}>{t('faculty')}</Table.Th>
                        <Table.Td>Факультет інформатики</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>{t('department')}</Table.Th>
                        <Table.Td>Кафедра інформатики</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>{t('educationlevel')}</Table.Th>
                        <Table.Td>Бакалавр</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>{t('academicYear')}</Table.Th>
                        <Table.Td>2025–2026</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>{t('semester')}</Table.Th>
                        <Table.Td>{subject.term}</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>{t('recommendedCourse')}</Table.Th>
                        <Table.Td>2 рік</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>{t('numOfcredits')}</Table.Th>
                        <Table.Td>{subject.credits}</Table.Td>
                    </Table.Tr>
            
                    <Table.Tr>
                        <Table.Th>{t('weeklyHours')}</Table.Th>
                        <Table.Td>4 год/тижд.</Table.Td>
                    </Table.Tr>
            
                </Table.Tbody>
            </Table>
            
            <Table mt="1em" variant="vertical" withTableBorder highlightOnHover captionSide="top">
                <Table.Caption>{t('limitations')}</Table.Caption>
                <Table.Tbody>

                    <Table.Tr>
                        <Table.Th w={300}>{t('maxNumOfStudents')}</Table.Th>
                        <Table.Td>Необмежено</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('numOfStudentsPerGroup')}</Table.Th>
                        <Table.Td>9-12</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('numOfEnrolledStudents')}</Table.Th>
                        <Table.Td>66</Table.Td>
                    </Table.Tr>

                    <Table.Tr>
                        <Table.Th>{t('numOfGroups')}</Table.Th>
                        <Table.Td>6</Table.Td>
                    </Table.Tr>

                </Table.Tbody>
            </Table>
        </Stack>
    );
}

export default SubjectDetails