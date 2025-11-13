import { Kbd, TextInput } from "@mantine/core";
import search from '../../assets/search.svg'
import Icon from "./Icon.tsx";

type SearchbarProps = {
    label: string;
    description: string;
    placeholder: string;
}

function Searchbar({label, description, placeholder}: SearchbarProps) {
    return(
        <TextInput
            label={label}
            leftSection={<Icon src={search}/>}
            rightSection={<Kbd mr="1.8em">Enter</Kbd>}
            description={description}
            placeholder={placeholder}
            size="md"
            width="md"
        />
    );
}

export default Searchbar