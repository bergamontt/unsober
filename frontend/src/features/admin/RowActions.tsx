import { ActionIcon, Group, Tooltip } from "@mantine/core";
import Icon from "../../common/icon/Icon";
import editIcon from "../../assets/edit.svg";
import delIcon from "../../assets/delete.svg";

type RowActionsProps = {
    onEdit: () => void;
    onDelete: () => void;
    editLabel?: string;
    deleteLabel?: string;
}

function RowActions({
    onEdit,
    onDelete,
    editLabel="Редагувати",
    deleteLabel="Видалити"
} : RowActionsProps) {
    return (
        <Group
            gap="xs"
            justify="right"
        >
            <Tooltip label={editLabel}>
                <ActionIcon
                    variant="light"
                    color="indigo"
                    onClick={onEdit}
                >
                    <Icon src={editIcon} />
                </ActionIcon>
            </Tooltip>
            <Tooltip label={deleteLabel}>
                <ActionIcon
                    variant="light"
                    color="red"
                    onClick={onDelete}
                >
                    <Icon src={delIcon} />
                </ActionIcon>
            </Tooltip>
        </Group>
    );
}

export default RowActions