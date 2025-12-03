import { useTranslation } from "react-i18next";
import type { EnrollmentRequest, RequestStatus, WithdrawalRequest } from "../../models/Request";
import useFetch from "../../hooks/useFetch";
import { getAllEnrollmentRequests } from "../../services/EnrollmentRequestService";
import { useStudentStore } from "../../hooks/studentStore";
import { getAllWithdrawalRequests } from "../../services/WithdrawalRequestService";
import StudentRequestCard from "./StudentRequestCard";
import { Stack, Text } from "@mantine/core";
import { useMemo } from "react";

interface StudentRequestListProps {
    status?: RequestStatus,
    reason?: string
}

type AnyRequest =
    | { type: "enrollment"; item: EnrollmentRequest }
    | { type: "withdrawal"; item: WithdrawalRequest };

function StudentRequestList({ status, reason }: StudentRequestListProps) {
    const { t } = useTranslation("studentRequests");
    const { user: student } = useStudentStore();
    const filters = useMemo(() => ({
        status,
        reason,
        studentId: student?.id
    }), [status, reason, student]);
    const { data: enrollmentRequestsRaw } = useFetch(
        getAllEnrollmentRequests, [filters]);
    const { data: withdrawalRequestsRaw } = useFetch(
        getAllWithdrawalRequests, [filters]);

    const enrollmentRequests = enrollmentRequestsRaw ?? [];
    const withdrawalRequests = withdrawalRequestsRaw ?? [];

    const requests: AnyRequest[] = [
        ...enrollmentRequests.map(r => ({ type: "enrollment", item: r } as AnyRequest)),
        ...withdrawalRequests.map(r => ({ type: "withdrawal", item: r } as AnyRequest))
    ];

    const parseTime = (iso: string) => {
        const t = Date.parse(iso);
        return Number.isFinite(t) ? t : 0;
    };

    requests.sort((r1, r2) => {
        const t1 = parseTime(r1.item.createdAt);
        const t2 = parseTime(r2.item.createdAt);
        return t1 - t2;
    });

    const components = requests.map(r => {
        const text = r.type == "enrollment" ?
            t("enrollmentRequestFor") + r.item.course.subject.name
            :
            t("withdrawalRequestFrom") + r.item.studentEnrollment.course.subject.name;
        return (
            <StudentRequestCard
                key={r.item.id}
                title={
                    <Text size="md">
                        {text}
                    </Text>
                }
                reason={r.item.reason}
                status={r.item.status}
                createdAt={r.item.createdAt}
            />
        );
    });

    return (
        <Stack>
            {components}
        </Stack>
    );
}

export default StudentRequestList;