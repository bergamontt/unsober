import { Kbd, TextInput } from "@mantine/core";
import search from '../../assets/search.svg'
import Icon from "../icon/Icon.tsx";

type SearchbarProps = {
    label: string;
    description?: string;
    placeholder?: string;
    onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

function Searchbar({ label, description, placeholder, onChange }: SearchbarProps) {
    return (
        <TextInput
            label={label}
            leftSection={<Icon src={search} />}
            rightSection={<Kbd mr="1.8em">Enter</Kbd>}
            description={description}
            placeholder={placeholder}
            onChange={onChange}
            size="md"
            width="md"
        />
    );
}

export default Searchbar