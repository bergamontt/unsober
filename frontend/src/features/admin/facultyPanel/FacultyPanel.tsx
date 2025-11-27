import { Stack, Table } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { getAllFaculties } from "../../../services/FacultyService.ts";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import AddFacultyModal from "./AddFacultyModal.tsx";
import DeleteFacultyModal from "./DeleteFacultyModal.tsx";
import EditFacultyModal from "./EditFacultyModal.tsx";
import useFetch from "../../../hooks/useFetch.ts";
import RowActions from "../RowActions.tsx";
import SearchWithAdd from "../SearchWithAdd.tsx";

function FacultyPanel() {
    const { t } = useTranslation(["adminMenu", "manageFaculties"]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);
    const [currentId, setCurrentId] = useState<string | null>(null);

    const { data } = useFetch(getAllFaculties, []);
    const faculties = data ?? [];

    const rows = faculties.map((f, index) => (
        <Table.Tr key={index}>
            <Table.Td>{f.name}</Table.Td>
            <Table.Td>{f.description || "-"}</Table.Td>
            <Table.Td>
                <RowActions
                    onEdit={() => {
                        setCurrentId(f.id);
                        openEdit();
                    }}
                    onDelete={() => {
                        setCurrentId(f.id);
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
            <SearchWithAdd
                label={t("manageFaculties:facultySearch")}
                description={t("manageFaculties:enterName")}
                placeholder={t("manageFaculties:name")}
                onAdd={openAdd}
            />
            <Table striped highlightOnHover withTableBorder>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th>{t("manageFaculties:name")}</Table.Th>
                        <Table.Th>{t("manageFaculties:description")}</Table.Th>
                        <Table.Th></Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>{rows}</Table.Tbody>
            </Table>

            <AddFacultyModal
                opened={addOpened}
                close={closeAdd}
            />
            <DeleteFacultyModal
                opened={deleteOpened}
                close={closeDelete}
                facultyId={currentId}
            />
            <EditFacultyModal
                opened={editOpened}
                close={closeEdit}
                facultyId={currentId}
            />
        </Stack>
    );
}

export default FacultyPanel;
