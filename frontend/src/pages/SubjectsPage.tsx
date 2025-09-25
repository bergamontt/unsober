import {Center, Pagination, Stack, Title} from "@mantine/core";
import { useTranslation } from "react-i18next";
import Searchbar from "../components/common/Searchbar";
import SubjectPreview from "../components/subject/SubjectPreview.tsx";
import PageWrapper from "../components/common/PageWrapper.tsx";

function SubjectsPage() {
    const {t} = useTranslation("subjectSearch"); 
    return (
        <PageWrapper>
            <Stack w='1200px' pt="3em" mih='calc(100vh - 60px)' pl="1em" pr="1em">
                <Title order={2}>{t('subjectCatalog')} </Title>
                <Searchbar
                    label={t('subjectSearchbarLabel')}
                    description={t('subjectSearchbarDescr')}
                    placeholder={t('subjectSearchbarPlchldr')}
                />
                <Stack align="stretch" justify="space-between" flex={1}>
                    <SubjectPreview />
                    <Center mb="sm">
                        <Pagination total={10} color="indigo"/>
                    </Center>
                </Stack>
            </Stack>
        </PageWrapper>
    );
}

export default SubjectsPage