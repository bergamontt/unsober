import { Stack, Table } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { getAllSpecialities } from "../../../services/SpecialityService.ts";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import AddSpecialityModal from "./AddSpecialityModal.tsx";
import DeleteSpecialityModal from "./DeleteSpecialityModal.tsx";
import EditSpecialityModal from "./EditSpecialityModal.tsx";
import useFetch from "../../../hooks/useFetch.ts";
import RowActions from "../RowActions.tsx";
import SearchWithAdd from "../SearchWithAdd.tsx";

function SpecialityPanel() {
    const { t } = useTranslation(["adminMenu", "manageSpecialities"]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);
    const [currentId, setCurrentId] = useState<string | null>(null);

    const { data } = useFetch(getAllSpecialities, []);
    const specialities = data ?? [];

    const rows = specialities.map((s, index) => (
        <Table.Tr key={index}>
            <Table.Td>{s.name}</Table.Td>
            <Table.Td>{s.department?.name ?? "-"}</Table.Td>
            <Table.Td>{s.department?.faculty?.name ?? "-"}</Table.Td>
            <Table.Td>
                <RowActions
                    onEdit={() => {
                        setCurrentId(s.id);
                        openEdit();
                    }}
                    onDelete={() => {
                        setCurrentId(s.id);
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
                label={t("manageSpecialities:specialitySearch")}
                description={t("manageSpecialities:enterName")}
                placeholder={t("manageSpecialities:name")}
                onAdd={openAdd}
            />
            <Table striped highlightOnHover withTableBorder>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th>{t("manageSpecialities:name")}</Table.Th>
                        <Table.Th>{t("manageSpecialities:department")}</Table.Th>
                        <Table.Th>{t("manageSpecialities:faculty")}</Table.Th>
                        <Table.Th></Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>{rows}</Table.Tbody>
            </Table>

            <AddSpecialityModal
                opened={addOpened}
                close={closeAdd}
            />
            <DeleteSpecialityModal
                opened={deleteOpened}
                close={closeDelete}
                specialityId={currentId}
            />
            <EditSpecialityModal
                opened={editOpened}
                close={closeEdit}
                specialityId={currentId}
            />
        </Stack>
    );
}

export default SpecialityPanel;
