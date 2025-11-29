import { useCallback, useState } from "react";
import { Card, Group, Avatar, Text, Stack, Button } from "@mantine/core";
import { RequestStatus, type WithdrawalRequest } from "../../../models/Request";
import cross from "../../../assets/red-cross.svg";
import check from "../../../assets/green-check.svg";
import Icon from "../../../common/icon/Icon";
import type { Student } from "../../../models/Student";
import { useTranslation } from "react-i18next";
import { updateWithdrawalRequestStatus } from "../../../services/WithdrawalRequestService";
import { notifications } from "@mantine/notifications";
import axios from "axios";
import { deleteStudentEnrollment } from "../../../services/StudentEnrollmentService";

export interface WithdrawalRequestCardProps {
    request: WithdrawalRequest;
}

const initials = (s: Student) => `${s?.firstName?.at(0) ?? '?'}${s?.lastName?.at(0) ?? '?'}`.toUpperCase();

export default function WithdrawalRequestCard({ request }: WithdrawalRequestCardProps) {
    const { t } = useTranslation("manageRequests");
    const [localStatus, setLocalStatus] = useState<RequestStatus>(request.status);
    const [loadingAction, setLoadingAction] = useState<"approve" | "decline" | null>(null);

    const student = request.studentEnrollment.student;
    const createdAt = new Date(request.createdAt).toLocaleString();

    const handleApprove = useCallback(async () => {
        setLoadingAction("approve");
        try {
            await deleteStudentEnrollment(request.studentEnrollment.id);
            await updateWithdrawalRequestStatus(request.id, RequestStatus.ACCEPTED);
            notifications.show({
                title: t("success"),
                message: t("requestAccepted"),
                color: "green",
            });
            setLocalStatus(RequestStatus.ACCEPTED);
        } catch (err: unknown) {
            let errorMessage = t("unknownAcceptError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim()) {
                    errorMessage = data;
                }
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: "red",
            });
        } finally {
            setLoadingAction(null);
        }
    }, [t, localStatus, loadingAction]);

    const handleDecline = useCallback(async () => {
        setLoadingAction("decline");
        try {
            await updateWithdrawalRequestStatus(request.id, RequestStatus.DECLINED);
            notifications.show({
                title: t("success"),
                message: t("requestDeclined"),
                color: "green",
            });
            setLocalStatus(RequestStatus.DECLINED);
        } catch (err: unknown) {
            let errorMessage = t("unknownDeclineError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim()) {
                    errorMessage = data;
                }
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: "red",
            });
        } finally {
            setLoadingAction(null);
        }
    }, [t, localStatus, loadingAction]);

    return (
        <Card
            shadow="xs"
            padding="md"
            radius="md"
            withBorder
        >
            <Group align="flex-start" gap="md">
                <Avatar radius="xl">
                    {initials(student)}
                </Avatar>
                <Stack gap={0}>
                    <Text style={{ fontWeight: 500 }}>
                        {`${student.firstName} ${student.lastName} ${student.patronymic}`}
                    </Text>
                    <Text size="xs" c="dimmed">
                        {createdAt}
                    </Text>
                </Stack>
            </Group>

            <Stack gap="sm" mt="md">
                <Text size="sm" c="dimmed">
                    {t("withdrawalRequestFrom") + request.studentEnrollment.course.subject.name}
                </Text>
                <Text size="sm">{request.reason}</Text>

                <Group gap="sm">
                    <Button
                        onClick={handleApprove}
                        leftSection={<Icon src={check} />}
                        disabled={localStatus !== RequestStatus.PENDING || loadingAction !== null}
                        loading={loadingAction === "approve"}
                        variant="outline"
                        color="green"
                    >
                        {t("approve")}
                    </Button>

                    <Button
                        onClick={handleDecline}
                        leftSection={<Icon src={cross} />}
                        disabled={localStatus !== RequestStatus.PENDING || loadingAction !== null}
                        loading={loadingAction === "decline"}
                        variant="outline"
                        color="red"
                    >
                        {t("decline")}
                    </Button>
                </Group>
            </Stack>
        </Card>
    );
}
