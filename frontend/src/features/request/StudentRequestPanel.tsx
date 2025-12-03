import { Stack, Title, Text, Group, NativeSelect, Space, Box } from "@mantine/core";
import { useTranslation } from "react-i18next";
import SearchWithAdd from "../admin/SearchWithAdd";
import { useDisclosure } from "@mantine/hooks";
import StudentRequestList from "./StudentRequestList";
import AddRequestModal from "./AddRequestModal";
import { RequestStatus } from "../../models/Request";
import { useState } from "react";

function StudentRequestPanel() {
    const { t } = useTranslation("studentRequests");
    const [addOpened, { open, close }] = useDisclosure(false);
    const [status, setStatus] = useState<RequestStatus | undefined>();
    const [reason, setReason] = useState<string>("");

    return (
        <Stack pl="2em">
            <Stack gap="4">
                <Title order={2} pb="0.4em">
                    {t("studentRequestHeader")}
                </Title>
                <Text c="dimmed">
                    {t("studentRequestExplanation")}
                </Text>

                <Space h="xl" />

                <Group align="flex-end">
                    <Box flex={1}>
                        <SearchWithAdd
                            label={t("requestSearch")}
                            description={t("enterText")}
                            placeholder={t("text")}
                            onAdd={open}
                            onChange={e => setReason(e.currentTarget.value)}
                        />
                    </Box>
                    <NativeSelect
                        description={t("requests")}
                        data={[
                            { value: "", label: t("all") },
                            { value: RequestStatus.ACCEPTED, label: t(RequestStatus.ACCEPTED) },
                            { value: RequestStatus.DECLINED, label: t(RequestStatus.DECLINED) },
                            { value: RequestStatus.PENDING, label: t(RequestStatus.PENDING) }
                        ]}
                        value={status ?? undefined}
                        onChange={(e) =>
                            setStatus((e.currentTarget.value as RequestStatus) || undefined)
                        }
                    />
                </Group>

                <Space h="xs" />

                <StudentRequestList
                    status={status}
                    reason={reason}
                />
            </Stack>

            <AddRequestModal
                opened={addOpened}
                close={close}
            />
        </Stack>
    );
}

export default StudentRequestPanel;