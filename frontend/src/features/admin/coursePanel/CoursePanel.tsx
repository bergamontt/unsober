import {useTranslation} from "react-i18next";
import {useMemo, useState} from "react";
import useFetch from "../../../hooks/useFetch.ts";
import {getAllCourses} from "../../../services/CourseService.ts";
import {Center, Loader, Pagination, Stack} from "@mantine/core";
import SearchWithAdd from "../SearchWithAdd.tsx";
import {useDisclosure} from "@mantine/hooks";
import AddCourseModal from "./AddCourseModal.tsx";
import CoursePanelPreview from "./CoursePanelPreview.tsx";

function CoursePanel() {
    const {t} = useTranslation("coursePanel");
    const [page, setPage] = useState<number>(1);
    const [addOpened, {open: openAdd, close: closeAdd}] = useDisclosure(false);

    const params = useMemo(() => (
        {page: page - 1}), [page]
    );
    const {data: pages} = useFetch(getAllCourses, [params]);
    return (
        <Stack>
            <SearchWithAdd
                label={t("search")}
                placeholder={t("searchPlaceholder")}
                onAdd={openAdd}
            />
            <Stack align="stretch" justify="space-between" flex={1}>
                {
                    pages &&
                    <>
                        <Stack align="stretch" gap={0}>
                            {pages.content.map((course) => (
                                <CoursePanelPreview course={course} key={course.id}/>
                            ))}
                        </Stack>
                        <Center>
                            <Pagination
                                total={pages.page.totalPages}
                                value={page}
                                onChange={setPage}
                                color="indigo"
                            />
                        </Center>
                    </>
                    || <Center><Loader color="gray" size="xl" mt={100}/></Center>
                }
            </Stack>

            <AddCourseModal
                opened={addOpened}
                close={closeAdd}
            />
        </Stack>
    );
}

export default CoursePanel
