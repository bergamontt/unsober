import { ActionIcon, Group, Stack, Table, Tooltip } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { getAllStudents } from "../../../services/StudentService.ts";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import Icon from "../../../common/icon/Icon.tsx";
import Searchbar from "../../../common/searchbar/Searchbar.tsx";
import AddStudentModal from "./AddStudentModal.tsx";
import DeleteStudentModal from "./DeleteStudentModal.tsx";
import EditStudentModal from "./EditStudentModal.tsx";
import edit from '../../../assets/edit.svg';
import del from '../../../assets/delete.svg';
import plus from '../../../assets/plus.svg'
import useFetch from "../../../hooks/useFetch.ts";

function StudentPanel() {
    const { t } = useTranslation(["adminMenu", "manageStudents"]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);
    const [currentId, setCurrentId] = useState<string | null>(null);

    const { data } = useFetch(getAllStudents, []);
    const students = data ?? [];

    const rows = students.map((student, index) => (
        <Table.Tr key={index}>
            <Table.Td>{`${student.lastName} ${student.firstName} ${student.patronymic}`}</Table.Td>
            <Table.Td>{student.email}</Table.Td>
            <Table.Td>{student.studyYear}</Table.Td>
            <Table.Td>{student.speciality.name}</Table.Td>
            <Table.Td>
                <Group gap="xs" justify="center">
                    <Tooltip label={t("adminMenu:edit")}>
                        <ActionIcon
                            variant="light"
                            color="indigo"
                            onClick={() => {
                                setCurrentId(student.id);
                                openEdit();
                            }}
                        >
                            <Icon src={edit} />
                        </ActionIcon>
                    </Tooltip>
                    <Tooltip label={t("adminMenu:delete")}>
                        <ActionIcon
                            variant="light"
                            color="red"
                            onClick={() => {
                                setCurrentId(student.id);
                                openDelete();
                            }}
                        >
                            <Icon src={del} />
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
                        label={t("manageStudents:studentSearch")}
                        description={t("manageStudents:enterEmail")}
                        placeholder={t("manageStudents:email")}
                    />
                </Group>
                <Tooltip label={t("adminMenu:add")}>
                    <ActionIcon
                        onClick={openAdd}
                        variant="filled"
                        color="green"
                        size="xl"
                        maw="xl"
                    >
                        <Icon src={plus} size="1.5em" />
                    </ActionIcon>
                </Tooltip>
            </Group>
            <Table striped highlightOnHover withTableBorder>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th>{t("manageStudents:fullName")}</Table.Th>
                        <Table.Th>{t("manageStudents:email")}</Table.Th>
                        <Table.Th>{t("manageStudents:studyYear")}</Table.Th>
                        <Table.Th>{t("manageStudents:speciality")}</Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>{rows}</Table.Tbody>
            </Table>
            <AddStudentModal
                opened={addOpened}
                close={closeAdd}
            />
            <DeleteStudentModal
                opened={deleteOpened}
                close={closeDelete}
                studentId={currentId}
            />
            <EditStudentModal
                opened={editOpened}
                close={closeEdit}
                studentId={currentId}
            />
        </Stack>
    );
}

export default StudentPanel;