import { Stack, Title } from "@mantine/core";
import { useTranslation } from "react-i18next";
import useFetch from "../../../hooks/useFetch.ts";
import Searchbar from "../../../common/searchbar/Searchbar.tsx";
import EnrollmentRequestCard from "./EnrollmentRequestCard.tsx";
import { getAllEnrollmentRequests } from "../../../services/EnrollmentRequestService.ts";
import { RequestStatus, type EnrollmentRequest, type WithdrawalRequest } from "../../../models/Request.ts";
import { getAllWithdrawalRequests } from "../../../services/WithdrawalRequestService.ts";
import WithdrawalRequestCard from "./WithdrawalRequestCard.tsx";
import { useMemo, useState } from "react";

type AnyRequest =
    | { type: "enrollment"; item: EnrollmentRequest }
    | { type: "withdrawal"; item: WithdrawalRequest };

function RequestPanel() {
    const { t } = useTranslation("manageRequests");
    const [reason, setReason] = useState<string>("");

    const filters = useMemo(() => ({
        reason,
        status: RequestStatus.PENDING
    }), [reason]);

    const { data: enrollmentRequestsRaw } = useFetch(
        getAllEnrollmentRequests, [filters]);
    const enrollmentRequests = enrollmentRequestsRaw ?? [];
    const { data: withdrawalRequestsRaw } = useFetch(
        getAllWithdrawalRequests, [filters]);
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

    const components = requests.map(r =>
        r.type == "enrollment" ? (
            <EnrollmentRequestCard key={r.item.id} request={r.item} />
        ) : (
            <WithdrawalRequestCard key={r.item.id} request={r.item} />
        )
    );

    return (
        <Stack>
            <Stack gap="sm">
                <Title order={4}>
                    {t("requests")}
                </Title>
                <Searchbar
                    label={t("requestSearch")}
                    description={t("enterText")}
                    placeholder={t("text")}
                    onChange={e => setReason(e.currentTarget.value)}
                />
            </Stack>
            <Stack>
                {components}
            </Stack>
        </Stack>
    );
}

export default RequestPanel;