import { Table, Text } from "@mantine/core";
import { EnrollmentStage, type AppState } from "../../models/AppState";
import { EnrollmentStatus, type StudentEnrollment } from "../../models/StudentEnrollment";
import { useTranslation } from "react-i18next";
import type { CourseGroup } from "../../models/CourseGroup";
import GroupSelector from "./GroupSelector";

interface CourseGroupListProps {
    state: AppState;
    enrollments: StudentEnrollment[];
}

function CourseGroupList({ state, enrollments }: CourseGroupListProps) {
    const { t } = useTranslation("groups");
    const header = (
        <Table.Tr>
            <Table.Th>{t("courseName")}</Table.Th>
            <Table.Th>{t("groupNumber")}</Table.Th>
        </Table.Tr>
    );

    const termEnrollments = enrollments
        .sort((e1, e2) => e1.course.subject.name.localeCompare(e2.course.subject.name))
        .filter(e => e.course.subject.term == state.term)
        .filter(e => e.status != EnrollmentStatus.WITHDRAWN) ?? [];

    const groupToStr = (g: CourseGroup): string => {
        return `${g.groupNumber} ${t("group")} (${g.numEnrolled}/${g.maxStudents})`;
    }

    const enrollmentActive = state.enrollmentStage == EnrollmentStage.CORRECTION
        || state.enrollmentStage == EnrollmentStage.GROUPS;
    const rows = termEnrollments.map(e => {
        const badge = enrollmentActive ?
            <GroupSelector enrollment={e} />
            :
            <Text>
                {e.group ? groupToStr(e.group) : t("notSelected")}
            </Text>;
        return (
            <Table.Tr key={e.id}>
                <Table.Td>{e.course.subject.name}</Table.Td>
                <Table.Td>{badge}</Table.Td>
            </Table.Tr>
        );
    });
    return (
        <Table
            withColumnBorders
            withTableBorder
            withRowBorders
        >
            <Table.Thead>
                {header}
            </Table.Thead>
            <Table.Tbody>
                {rows.length ? rows : (
                    <Table.Tr>
                        <Table.Td colSpan={4} style={{ textAlign: 'center', opacity: 0.6 }}>
                            {t("noEnrollments")}
                        </Table.Td>
                    </Table.Tr>
                )}
            </Table.Tbody>
        </Table>
    );
}

export default CourseGroupList;