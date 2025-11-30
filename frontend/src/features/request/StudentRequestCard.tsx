import { Badge, Box, Card, Group, Stack, Text } from "@mantine/core";
import { RequestStatus } from "../../models/Request";
import Icon from "../../common/icon/Icon";
import hourglass from "../../assets/hourglass.svg";
import check from "../../assets/green-check.svg";
import cross from "../../assets/red-cross.svg";
import { useTranslation } from "react-i18next";

interface StudentRequestCardProps {
    title: React.ReactNode;
    reason: string;
    status: RequestStatus;
    createdAt: string;
}

function StudentRequestCard({ title, reason, status, createdAt }: StudentRequestCardProps) {
    const { t } = useTranslation("studentRequests");

    const getBadge = (status: RequestStatus) => {
        var color: string;
        var icon: React.ReactNode;
        var text: string;
        switch (status) {
            case RequestStatus.ACCEPTED:
                color = "green";
                icon = <Icon src={check} />;
                text = "accepted";
                break;
            case RequestStatus.DECLINED:
                color = "red";
                icon = <Icon src={cross} />;
                text = "declined";
                break;
            default:
                color = "yellow";
                icon = <Icon src={hourglass} />;
                text = "pending"
                break;
        }
        return (
            <Badge
                variant="outline"
                color={color}
                size="xl"
                radius="md"
                rightSection={icon}
            >
                {t(text)}
            </Badge>
        );
    }
    return (
        <Card
            shadow="xs"
            padding="md"
            radius="md"
            withBorder
        >
            <Stack gap="sm">
                <Group>
                    <Box flex={1}>
                        {title}
                        <Text size="xs" c="dimmed">
                            {new Date(createdAt).toLocaleString()}
                        </Text>
                    </Box>
                    {getBadge(status)}
                </Group>
                <Text size="sm">
                    {reason}
                </Text>
            </Stack>
        </Card>
    );
}

export default StudentRequestCard;