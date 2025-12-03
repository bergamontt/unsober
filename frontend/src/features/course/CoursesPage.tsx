import {Center, Group, Loader, Pagination, Select, Stack, Title} from "@mantine/core";
import {useTranslation} from "react-i18next";
import {useEffect, useMemo, useState} from "react";
import Searchbar from "../../common/searchbar/Searchbar.tsx";
import CoursePreview from "./CoursePreview.tsx";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import useFetch from "../../hooks/useFetch.ts";
import {getAllCourses} from "../../services/CourseService.ts";
import {getAppState} from "../../services/AppStateService.ts";
import {EducationLevel, Term} from "../../models/Subject";

function CoursesPage() {
    const {t} = useTranslation("courseSearch");
    const {data: state} = useFetch(getAppState, []);
    const [page, setPage] = useState<number>(1);
    const [name, setName] = useState<string>("");
    const [level, setLevel] = useState<EducationLevel | undefined>(undefined);
    const [term, setTerm] = useState<Term | undefined>(undefined);
    const [credits, setCredits] = useState<number | undefined>(undefined);
    const [year, setYear] = useState<number | undefined>(undefined);

    useEffect(() => {
        setYear(state?.currentYear)
    }, [state]);

    const filters = useMemo(() => ({
        subjectName: name,
        courseYear: year,
        educationLevel: level,
        term: term,
        credits: credits
    }), [name, level, term, credits, year]);
    const params = useMemo(() => (
        {page: page - 1}), [page]
    );
    const {data: pages} = useFetch(getAllCourses, [params, filters]);
    return (
        <PageWrapper>
            <Title>{t('courseCatalog')} </Title>
            <Searchbar
                label={t('courseSearchbarLabel')}
                description={t('courseSearchbarDescr')}
                placeholder={t('courseSearchbarPlchldr')}
                onChange={e => {
                    setName(e.currentTarget.value);
                    setPage(1);
                }}
            />
            {
                year &&
                <Group>
                    <Select
                        label={t("courseYearLabel")}
                        placeholder={t("courseYearPlcfldr")}
                        data={[
                            "2025", "2024", "2023", "2022", "2021", "2020",
                            "2019", "2018", "2017", "2016", "2015", "2014",
                            "2013", "2012", "2011", "2010", "2009", "2008",
                            "2007", "2006", "2005", "2004", "2003", "2002",
                            "2001", "2000"
                        ]}
                        value={String(year)}
                        onChange={year => {
                            setYear(year ? Number(year) : undefined);
                            setPage(1);
                        }}
                    />
                    <Select clearable
                            label={t("courseLevelLabel")}
                            placeholder={t("courseLevelPlcfldr")}
                            data={[EducationLevel.BATCHELOR, EducationLevel.MASTER].map(level => {
                                return {label: t(level.toLowerCase()), value: level}
                            })
                            }
                            onChange={level => {
                                setLevel(level ? level as EducationLevel : undefined);
                                setPage(1);
                            }}
                    />
                    <Select clearable
                            label={t("courseTermLabel")}
                            placeholder={t("courseTermPlcfldr")}
                            data={[Term.AUTUMN, Term.SPRING, Term.SUMMER].map(term => {
                                return {label: t(term.toLowerCase()), value: term}
                            })
                            }
                            onChange={term => {
                                setTerm(term ? term as Term : undefined);
                                setPage(1);
                            }}
                    />
                    <Select clearable
                            label={t("courseTermLabel")}
                            placeholder={t("courseTermPlcfldr")}
                            data={["2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6"]}
                            onChange={credits => {
                                setCredits(credits ? Number(credits) : undefined);
                                setPage(1);
                            }}
                    />
                </Group>
            }
            {
                year && pages &&
                <Stack align="stretch" justify="space-between" flex={1}>
                    <Stack align="stretch" gap={0}>
                        {
                            pages.content.map((course) => (
                                <CoursePreview course={course} key={course.id}/>
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
                || <Center><Loader color="gray" size="xl" mt={100}/>
                </Center>
            }

        </PageWrapper>
    );
}

export default CoursesPage
