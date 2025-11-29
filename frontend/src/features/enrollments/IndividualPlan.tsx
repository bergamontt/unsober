import { useTranslation } from "react-i18next";
import { Term } from "../../models/Subject";
import { Stack } from "@mantine/core";
import EnrollmentsGroup from "./EnrollmentsGroup";
import EnrollmentsSummary from "./EnrollmentsSummary";
import { getAllEnrollmentsByStudentIdAndYear } from "../../services/StudentEnrollmentService";
import { useStudentStore } from "../../hooks/studentStore";
import useFetch from "../../hooks/useFetch";
import { EnrollmentStatus } from "../../models/StudentEnrollment";

interface EnrollmentGroupParams {
    year: number;
}

function IndividualPlan({ year }: EnrollmentGroupParams) {
    const { t } = useTranslation("enrollments");
    const { user: student } = useStudentStore();

    const { data } = useFetch(getAllEnrollmentsByStudentIdAndYear, [student?.id ?? null, year]);
    const enrollments = data?.filter(e => e.status != EnrollmentStatus.WITHDRAWN) ?? [];
    const autumn = enrollments.filter(e => e.course.subject.term == Term.AUTUMN);
    const spring = enrollments.filter(e => e.course.subject.term == Term.SPRING);
    const summer = enrollments.filter(e => e.course.subject.term == Term.SUMMER);

    return (
        <Stack>
            <EnrollmentsGroup
                enrollments={autumn}
                name={t("autumn")}
            />
            <EnrollmentsGroup
                enrollments={spring}
                name={t("spring")}
            />
            <EnrollmentsGroup
                enrollments={summer}
                name={t("summer")}
            />
            <EnrollmentsSummary enrollments={enrollments} />
        </Stack>
    );
}

export default IndividualPlan;