import { Stack, Table, Title } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { useTranslation } from "react-i18next";
import { useMemo, useState } from "react";
import useFetch from "../../../hooks/useFetch.ts";
import { getAllTeachers } from "../../../services/TeacherService.ts";
import RowActions from "../RowActions.tsx";
import SearchWithAdd from "../SearchWithAdd.tsx";
import AddTeacherModal from "./AddTeacherModal.tsx";
import DeleteTeacherModal from "./DeleteTeacherModal.tsx";
import EditTeacherModal from "./EditTeacherModal.tsx";

function TeacherPanel() {
    const { t } = useTranslation(["adminMenu", "manageTeachers"]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);
    const [currentId, setCurrentId] = useState<string | null>(null);
    const [email, setEmail] = useState<string>("");

    const filters = useMemo(() => ({
        email
    }), [email]);
    const { data } = useFetch(getAllTeachers, [filters]);
    const teachers = data ?? [];

    const rows = teachers.map((teacher, index) => (
        <Table.Tr key={teacher.id ?? index}>
            <Table.Td>{`${teacher.lastName} ${teacher.firstName} ${teacher.patronymic}`}</Table.Td>
            <Table.Td>{teacher.email}</Table.Td>
            <Table.Td>
                <RowActions
                    onEdit={() => {
                        setCurrentId(teacher.id);
                        openEdit();
                    }}
                    onDelete={() => {
                        setCurrentId(teacher.id);
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
                    {t("subjects")}
                </Title>
                <SearchWithAdd
                    label={t("manageTeachers:teacherSearch")}
                    description={t("manageTeachers:enterEmail")}
                    placeholder={t("manageTeachers:email")}
                    onAdd={openAdd}
                    onChange={e => setEmail(e.currentTarget.value)}
                />
            </Stack>
            <Table striped highlightOnHover withTableBorder>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th>{t("manageTeachers:fullName")}</Table.Th>
                        <Table.Th>{t("manageTeachers:email")}</Table.Th>
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
                teacherId={currentId}
            />
            <EditTeacherModal
                opened={editOpened}
                close={closeEdit}
                teacherId={currentId}
            />
        </Stack>
    );
}

export default TeacherPanel;
