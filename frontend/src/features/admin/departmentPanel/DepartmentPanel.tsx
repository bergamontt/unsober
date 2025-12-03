import { Stack, Table, Title } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { getAllDepartments } from "../../../services/DepartmentService.ts";
import { useTranslation } from "react-i18next";
import { useMemo, useState } from "react";
import AddDepartmentModal from "./AddDepartmentModal.tsx";
import DeleteDepartmentModal from "./DeleteDepartmentModal.tsx";
import EditDepartmentModal from "./EditDepartmentModal.tsx";
import useFetch from "../../../hooks/useFetch.ts";
import RowActions from "../RowActions.tsx";
import SearchWithAdd from "../SearchWithAdd.tsx";

function DepartmentPanel() {
    const { t } = useTranslation(["adminMenu", "manageDepartments"]);

    const [addOpened, { open: openAdd, close: closeAdd }] = useDisclosure(false);
    const [deleteOpened, { open: openDelete, close: closeDelete }] = useDisclosure(false);
    const [editOpened, { open: openEdit, close: closeEdit }] = useDisclosure(false);
    const [currentId, setCurrentId] = useState<string | null>(null);
    const [name, setName] = useState<string>("");

    const filters = useMemo(() => ({
        name
    }), [name]);
    const { data } = useFetch(getAllDepartments, [filters]);
    const departments = data ?? [];

    const rows = departments.map((d, index) => (
        <Table.Tr key={index}>
            <Table.Td>{d.name}</Table.Td>
            <Table.Td>{d.faculty?.name ?? "-"}</Table.Td>
            <Table.Td>
                <RowActions
                    onEdit={() => {
                        setCurrentId(d.id);
                        openEdit();
                    }}
                    onDelete={() => {
                        setCurrentId(d.id);
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
                    {t("departments")}
                </Title>
                <SearchWithAdd
                    label={t("manageDepartments:departmentSearch")}
                    description={t("manageDepartments:enterName")}
                    placeholder={t("manageDepartments:name")}
                    onAdd={openAdd}
                    onChange={e => setName(e.currentTarget.value)}
                />
            </Stack>
            <Table striped highlightOnHover withTableBorder>
                <Table.Thead>
                    <Table.Tr>
                        <Table.Th>{t("manageDepartments:name")}</Table.Th>
                        <Table.Th>{t("manageDepartments:faculty")}</Table.Th>
                        <Table.Th></Table.Th>
                    </Table.Tr>
                </Table.Thead>
                <Table.Tbody>{rows}</Table.Tbody>
            </Table>

            <AddDepartmentModal
                opened={addOpened}
                close={closeAdd}
            />
            <DeleteDepartmentModal
                opened={deleteOpened}
                close={closeDelete}
                departmentId={currentId}
            />
            <EditDepartmentModal
                opened={editOpened}
                close={closeEdit}
                departmentId={currentId}
            />
        </Stack>
    );
}

export default DepartmentPanel;
