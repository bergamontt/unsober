import {Center, Stack, Title} from "@mantine/core";
import { useTranslation } from "react-i18next";
import Searchbar from "../components/common/Searchbar";
import SubjectPreview from "../components/subject/SubjectPreview.tsx";

function SubjectsPage() {
    const {t} = useTranslation("subjectSearch"); 
    return (
        <Center w="100%" h='calc(100vh - 60px)'>
            <Stack w='1200px' pt="3em" mih='calc(100vh - 60px)' >
                <Title order={2}>{t('subjectCatalog')} </Title>
                <Searchbar
                    label={t('subjectSearchbarLabel')}
                    description={t('subjectSearchbarDescr')}
                    placeholder={t('subjectSearchbarPlchldr')}
                />
                <SubjectPreview />
            </Stack>
        </Center>
    );
}

export default SubjectsPage