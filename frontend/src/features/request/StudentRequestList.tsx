import { useTranslation } from "react-i18next";
import type { EnrollmentRequest, RequestStatus, WithdrawalRequest } from "../../models/Request";
import useFetch from "../../hooks/useFetch";
import { getEnrollmentRequestsByStudent } from "../../services/EnrollmentRequestService";
import { useStudentStore } from "../../hooks/studentStore";
import { getWithdrawalRequestsByStudent } from "../../services/WithdrawalRequestService";
import StudentRequestCard from "./StudentRequestCard";
import { Stack, Text } from "@mantine/core";

interface StudentRequestListProps {
    showOnly: RequestStatus | undefined
}

type AnyRequest =
    | { type: "enrollment"; item: EnrollmentRequest }
    | { type: "withdrawal"; item: WithdrawalRequest };

interface Request {
    status: RequestStatus;
}

const getFilterByStatus = (status: RequestStatus | undefined):
    ((r: Request) => boolean) => {
    if (!status)
        return _ => true;
    return r => r.status == status;
}

function StudentRequestList({ showOnly }: StudentRequestListProps) {
    const { t } = useTranslation("studentRequests");
    const { user: student } = useStudentStore();
    const { data: enrollmentRequestsRaw } = useFetch(
        getEnrollmentRequestsByStudent, [student?.id ?? null]);
    const { data: withdrawalRequestsRaw } = useFetch(
        getWithdrawalRequestsByStudent, [student?.id ?? null]);

    const filterFunc = getFilterByStatus(showOnly);
    const enrollmentRequests = enrollmentRequestsRaw?.filter(filterFunc) ?? [];
    const withdrawalRequests = withdrawalRequestsRaw?.filter(filterFunc) ?? [];

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