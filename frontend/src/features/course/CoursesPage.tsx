import { Center, Pagination, Stack, Title } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useEffect, useMemo, useState } from "react";
import Searchbar from "../../common/searchbar/Searchbar.tsx";
import CoursePreview from "./CoursePreview.tsx";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import useFetch from "../../hooks/useFetch.ts";
import { useNavigate } from "react-router";
import { useAuthStore } from "../../hooks/authStore.ts";
import { getAllCourses } from "../../services/CourseService.ts";

function CoursesPage() {
    const { t } = useTranslation("courseSearch");
    const [page, setPage] = useState<number>(1);
    const { isAuthenticated, loadingAuth } = useAuthStore();
    const navigate = useNavigate();
    useEffect(() => {
        if (!isAuthenticated && !loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, loadingAuth, navigate]);

    const params = useMemo(() => (
        { page: page - 1 }), [page]
    );
    const { data: pages } = useFetch(getAllCourses, [params]);
    return (
        <PageWrapper>
            <Title>{t('courseCatalog')} </Title>
            <Searchbar
                label={t('courseSearchbarLabel')}
                description={t('courseSearchbarDescr')}
                placeholder={t('courseSearchbarPlchldr')}
            />
            <Stack align="stretch" justify="space-between" flex={1}>
                <Stack align="stretch" gap={0}>
                    {pages &&
                        pages.content.map((course) => (
                            <CoursePreview course={course} key={course.id} />
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

export default CoursesPage