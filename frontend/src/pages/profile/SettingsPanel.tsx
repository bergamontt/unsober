import { Stack } from "@mantine/core";
import LanguageMenu from "../../components/common/LanguageMenu";

function SettingsPanel() {
    return(
        <Stack pl="6em">
            <LanguageMenu/>
        </Stack>
    );
}

export default SettingsPanel