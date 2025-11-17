import { ActionIcon, Group, Stack, Table, Tooltip } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import Icon from "../../common/Icon";
import Searchbar from "../../common/Searchbar";
import AddStudentModal from "./AddStudentModal";
import DeleteStudentModal from "./DeleteStudentModal";
import EditStudentModal from "./EditStudentModal";
import edit from '../../assets/edit.svg';
import del from '../../assets/delete.svg';
import plus from '../../assets/plus.svg'
import useFetch from "../../hooks/useFetch";
import { getAllStudents } from "../../services/StudentService";
import { useTranslation } from "react-i18next";

function StudentPanel() {
    const { t } = useTranslation(["adminMenu", "manageStudents"]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);

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
                            onClick={openEdit}
                        >
                            <Icon src={edit} />
                        </ActionIcon>
                    </Tooltip>
                    <Tooltip label={t("adminMenu:delete")}>
                        <ActionIcon
                            variant="light"
                            color="red"
                            onClick={openDelete}
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
            />
            <EditStudentModal
                opened={editOpened}
                close={closeEdit}
            />
        </Stack>
    );
}

export default StudentPanel;