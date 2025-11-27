import { Center, Pagination, Stack, Table } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { useTranslation } from "react-i18next";
import { useMemo, useState } from "react";
import useFetch from "../../../hooks/useFetch.ts";
import { getSubjects } from "../../../services/SubjectService.ts";
import RowActions from "../RowActions.tsx";
import SearchWithAdd from "../SearchWithAdd.tsx";
import AddSubjectModal from "./AddSubjectModal.tsx";
import DeleteSubjectModal from "./DeleteSubjectModal.tsx";
import EditSubjectModal from "./EditSubjectModal.tsx";

function SubjectPanel() {
    const { t } = useTranslation(["adminMenu", "manageSubjects"]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);
    const [currentId, setCurrentId] = useState<string | null>(null);
    const [page, setPage] = useState<number>(1);

    const params = useMemo(() => (
        { page: page - 1 }), [page]
    );
    const { data: pages } = useFetch(getSubjects, [params]);
    const subjects = pages?.content ?? [];

    const rows = subjects.map((subject, index) => (
        <Table.Tr key={subject.id ?? index}>
            <Table.Td>{subject.name}</Table.Td>
            <Table.Td>{subject.facultyName ?? ""}</Table.Td>
            <Table.Td>{subject.departmentName ?? ""}</Table.Td>
            <Table.Td>{t(`manageSubjects:${subject.educationLevel}`)}</Table.Td>
            <Table.Td>{subject.credits}</Table.Td>
            <Table.Td>{subject.hoursPerWeek}</Table.Td>
            <Table.Td>{t(`manageSubjects:${subject.term}`)}</Table.Td>
            <Table.Td>
                <RowActions
                    onEdit={() => {
                        setCurrentId(subject.id);
                        openEdit();
                    }}
                    onDelete={() => {
                        setCurrentId(subject.id);
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
            <SearchWithAdd label={t("manageSubjects:subjectSearch")} description={t("manageSubjects:enterName")} placeholder={t("manageSubjects:name")} onAdd={openAdd} />
            <Table striped highlightOnHover withTableBorder>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th>{t("manageSubjects:name")}</Table.Th>
                        <Table.Th>{t("manageSubjects:facultyName")}</Table.Th>
                        <Table.Th>{t("manageSubjects:departmentName")}</Table.Th>
                        <Table.Th>{t("manageSubjects:educationLevel")}</Table.Th>
                        <Table.Th>{t("manageSubjects:credits")}</Table.Th>
                        <Table.Th>{t("manageSubjects:hoursPerWeek")}</Table.Th>
                        <Table.Th>{t("manageSubjects:term")}</Table.Th>
                        <Table.Th></Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>{rows}</Table.Tbody>
            </Table>
            <Center>
                <Pagination
                    total={pages?.page.totalPages || 1}
                    value={page}
                    onChange={setPage}
                    color="indigo"
                />
            </Center>

            <AddSubjectModal
                opened={addOpened}
                close={closeAdd}
            />
            <DeleteSubjectModal
                opened={deleteOpened}
                close={closeDelete}
                subjectId={currentId}
            />
            <EditSubjectModal
                opened={editOpened}
                close={closeEdit}
                subjectId={currentId}
            />
        </Stack>
    );
}

export default SubjectPanel;
