import {Center, Pagination, Stack, Title} from "@mantine/core";
import { getSubjects } from "../../services/SubjectService.ts";
import { useTranslation } from "react-i18next";
import { useMemo, useState } from "react";
import Searchbar from "../../common/Searchbar.tsx";
import SubjectPreview from "./SubjectPreview.tsx";
import PageWrapper from "../../common/PageWrapper.tsx";
import useFetch from "../../hooks/useFetch.ts";

function SubjectsPage() {
    const {t} = useTranslation("subjectSearch"); 
    const [page, setPage] = useState<number>(1);
    const params = useMemo(() => (
        { page: page - 1 }), [page]
    );
    const {data : pages} = useFetch(
        getSubjects, [params],
    );
    return (
        <PageWrapper>
            <Title>{t('subjectCatalog')} </Title>
            <Searchbar
                label={t('subjectSearchbarLabel')}
                description={t('subjectSearchbarDescr')}
                placeholder={t('subjectSearchbarPlchldr')}
            />
            <Stack align="stretch" justify="space-between" flex={1}>
                <Stack align="stretch" gap={0}>
                    {pages &&
                    pages.content.map((subject) => (
                        <SubjectPreview subject={subject} key={subject.id}/>
                    ))}
                </Stack>
                <Center>
                    <Pagination
                        total={pages?.page.totalPages || 1}
                        value={page}
                        onChange={setPage}
                        color="indigo"
                    />
                </Center>
            </Stack>
        </PageWrapper>
    );
}

export default SubjectsPage