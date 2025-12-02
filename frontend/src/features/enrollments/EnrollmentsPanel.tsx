import { Button, Group, NativeSelect, Stack, Title } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import IndividualPlan from "./IndividualPlan";
import { getAllYearsByStudentId } from "../../services/StudentEnrollmentService";
import { useStudentStore } from "../../hooks/studentStore";
import useFetch from "../../hooks/useFetch";
import download from "../../assets/download.svg";
import Icon from "../../common/icon/Icon";
import { downloadStudyPlan } from "../../services/StudyPlanService";

function EnrollmentsPanel() {
    const { user: student } = useStudentStore();
    const { t } = useTranslation("enrollments");
    const { data } = useFetch(getAllYearsByStudentId, [student?.id ?? null]);
    const years = data ?? [];
    const [year, setYear] = useState<number>(years[0]);
    useEffect(() => {
        if (years.length > 0) {
            setYear(years[0]);
        }
    }, [years, setYear]);

    return (
        <Stack pl="6em">
            <Group
                justify="space-between"
                align="flex-end"
            >
                <Title>{t('individualPlan')} </Title>
                <Group
                    justify="flex-end"
                    align="flex-end"
                >

                    <NativeSelect
                        label={t("studyYear")}
                        data={years.map(year => ({
                            label: String(year) + "-" + String(year + 1),
                            value: String(year)
                        }))}
                        value={year}
                        onChange={(e) => {
                            setYear(Number(e.currentTarget.value));
                        }}
                    />
                    <Button
                        color='green'
                        leftSection={<Icon src={download} size="1.5em" />}
                        onClick={() => { downloadStudyPlan(student?.id ?? null); }}
                    >
                        {t("download")}
                    </Button>
                </Group>
            </Group>
            <IndividualPlan year={year} />
        </Stack>
    );
}

export default EnrollmentsPanel;