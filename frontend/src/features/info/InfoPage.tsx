import { Title, Stack, Text } from "@mantine/core";
import { useTranslation } from "react-i18next";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import useFetch from "../../hooks/useFetch.ts";
import { getAppState } from "../../services/AppStateService.ts";
import AuthGuard from "../../common/wrappers/AuthGuard.tsx";

function InfoPage() {
    const { t } = useTranslation(["infoPage", "stageDescription"]);
    const { data: appState } = useFetch(getAppState, []);

    if (!appState)
        return <></>;

    return (
        <AuthGuard>
            <PageWrapper>
                <Stack pl="6em" gap="4">
                    <Title order={3} pb="0.4em">
                        {t("infoPage:enrollmentInfo")}
                    </Title>
                    <Title order={5}>
                        {t("infoPage:currentYear")}
                    </Title>
                    <Text size="lg">
                        {appState.currentYear + "-" + (appState.currentYear + 1)}
                    </Text>
                    <Title order={5}>
                        {t("infoPage:currentTerm")}
                    </Title>
                    <Text size="lg">
                        {t(`infoPage:${appState.term}`)}
                    </Text>
                    <Title order={5}>
                        {t("infoPage:enrollmentStage")}
                    </Title>
                    <Text size="lg">
                        {t(`infoPage:${appState.enrollmentStage}`)}
                    </Text>
                    <Text size="lg">
                        {t(`stageDescription:${appState.enrollmentStage}`)}
                    </Text>
                </Stack>
            </PageWrapper>
        </AuthGuard>
    );
}

export default InfoPage