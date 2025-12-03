import { Stack, Table, Title } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { getAllStudents } from "../../../services/StudentService.ts";
import { useTranslation } from "react-i18next";
import { useMemo, useState } from "react";
import AddStudentModal from "./AddStudentModal.tsx";
import DeleteStudentModal from "./DeleteStudentModal.tsx";
import EditStudentModal from "./EditStudentModal.tsx";
import useFetch from "../../../hooks/useFetch.ts";
import RowActions from "../RowActions.tsx";
import SearchWithAdd from "../SearchWithAdd.tsx";

function StudentPanel() {
    const { t } = useTranslation(["adminMenu", "manageStudents"]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);
    const [currentId, setCurrentId] = useState<string | null>(null);
    const [email, setEmail] = useState<string>("");

    const filters = useMemo(() => ({
        email
    }), [email]);
    const { data } = useFetch(getAllStudents, [filters]);
    const students = data ?? [];

    const rows = students.map((student, index) => (
        <Table.Tr key={index}>
            <Table.Td>{`${student.lastName} ${student.firstName} ${student.patronymic}`}</Table.Td>
            <Table.Td>{student.email}</Table.Td>
            <Table.Td>{student.studyYear}</Table.Td>
            <Table.Td>{student.speciality.name}</Table.Td>
            <Table.Td>
                <RowActions
                    onEdit={() => {
                        setCurrentId(student.id);
                        openEdit();
                    }}
                    onDelete={() => {
                        setCurrentId(student.id);
                        openDelete();
                    }}
                    editLabel={t("adminMenu:edit")}
                    deleteLabel={t("adminMenu:delete")}
                />
            </Table.Td>
        </Table.Tr>
    ));

    return (
        <Stack>
            <Stack gap="sm">
                <Title order={4}>
                    {t("students")}
                </Title>
                <SearchWithAdd
                    label={t("manageStudents:studentSearch")}
                    description={t("manageStudents:enterEmail")}
                    placeholder={t("manageStudents:email")}
                    onAdd={openAdd}
                    onChange={e => setEmail(e.currentTarget.value)}
                />
            </Stack>
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