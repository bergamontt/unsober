import { Stack, Table, Title } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { useTranslation } from "react-i18next";
import { useMemo, useState } from "react";
import useFetch from "../../../hooks/useFetch.js";
import RowActions from "../RowActions.js";
import SearchWithAdd from "../SearchWithAdd.js";
import { getAllBuildings } from "../../../services/BuildingService.js";
import AddBuildingModal from "./AddBuildingModal.js";
import DeleteBuildingModal from "./DeleteBuildingModal.js";
import EditBuildingModal from "./EditBuildingModal.js";

function BuildingPanel() {
    const { t } = useTranslation(["adminMenu", "manageBuildings"]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);
    const [currentId, setCurrentId] = useState<string | null>(null);
    const [name, setName] = useState<string>("");

    const filters = useMemo(() => ({
        name
    }), [name]);
    const { data } = useFetch(getAllBuildings, [filters]);
    const buildings = data ?? [];

    const rows = buildings.map((b, index) => (
        <Table.Tr key={index}>
            <Table.Td>{b.name}</Table.Td>
            <Table.Td>{b.address ?? "-"}</Table.Td>
            <Table.Td>
                <RowActions
                    onEdit={() => {
                        setCurrentId(b.id);
                        openEdit();
                    }}
                    onDelete={() => {
                        setCurrentId(b.id);
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
                    {t("buildings")}
                </Title>
                <SearchWithAdd
                    label={t("manageBuildings:buildingSearch")}
                    description={t("manageBuildings:enterName")}
                    placeholder={t("manageBuildings:name")}
                    onAdd={openAdd}
                    onChange={e => setName(e.currentTarget.value)}
                />
            </Stack>
            <Table striped highlightOnHover withTableBorder>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th>{t("manageBuildings:name")}</Table.Th>
                        <Table.Th>{t("manageBuildings:address")}</Table.Th>
                        <Table.Th></Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>{rows}</Table.Tbody>
            </Table>

            <AddBuildingModal
                opened={addOpened}
                close={closeAdd}
            />
            <DeleteBuildingModal
                opened={deleteOpened}
                close={closeDelete}
                buildingId={currentId}
            />
            <EditBuildingModal
                opened={editOpened}
                close={closeEdit}
                buildingId={currentId}
            />
        </Stack>
    );
}

export default BuildingPanel;
