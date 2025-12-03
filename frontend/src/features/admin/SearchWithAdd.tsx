import { ActionIcon, Group, Tooltip } from "@mantine/core";
import Searchbar from "../../common/searchbar/Searchbar";
import Icon from "../../common/icon/Icon";
import plus from '../../assets/plus.svg'
import { useTranslation } from "react-i18next";

type SearchWithAddProps = {
    label: string;
    description?: string;
    placeholder?: string;
    onAdd: () => void;
    onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

function SearchWithAdd(
    { label, description, placeholder, onAdd, onChange }: SearchWithAddProps
) {
    const { t } = useTranslation("common");
    return (
        <Group grow align="flex-end">
            <Group grow>
                <Searchbar
                    label={label}
                    description={description}
                    placeholder={placeholder}
                    onChange={onChange}
                />
            </Group>
            <Tooltip label={t("add")}>
                <ActionIcon
                    onClick={onAdd}
                    variant="filled"
                    color="green"
                    size="xl"
                    maw="xl"
                >
                    <Icon src={plus} size="1.5em" />
                </ActionIcon>
            </Tooltip>
        </Group>
    );
}

export default SearchWithAdd