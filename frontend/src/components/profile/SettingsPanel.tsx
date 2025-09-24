import { Stack } from "@mantine/core";
import LanguageMenu from "./LanguageMenu.tsx";

function SettingsPanel() {
    return(
        <Stack pl="6em">
            <LanguageMenu/>
        </Stack>
    );
}

export default SettingsPanel