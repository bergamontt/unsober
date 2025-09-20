import { Center, Stack, Title, Pagination } from "@mantine/core";
import { useTranslation } from "react-i18next";
import Searchbar from "../components/common/Searchbar";
import Subject from "../components/common/Subject";

function SubjectsPage() {
    const {t} = useTranslation(); 
    return (
        <Center
            w="100%"
            h='calc(100vh - 60px)'
        >
            <Stack
                w='1200px'
                mih='calc(100vh - 60px)'
                pt="3em"
            >
                <Title order={2}>{t('subjectCatalog')} </Title>
                <Searchbar
                    label={t('subjectSearchbarLabel')}
                    description={t('subjectSearchbarDescr')}
                    placeholder={t('subjectSearchbarPlchldr')}
                />
                <Stack
                    pb="2em"
                    justify="space-between"
                    style={{ flexGrow: 1 }}
                    align="center"
                >
                    <Subject
                        name="Основи коп'ютерних алгоритмів"
                        speciality="ФІ"
                        isRecomended={true}
                    />
                    <Pagination total={20} color="indigo" />
                </Stack>
            </Stack>
        </Center>
    );
}

export default SubjectsPage