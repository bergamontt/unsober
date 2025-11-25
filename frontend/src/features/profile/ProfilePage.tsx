import { Tabs, Divider } from "@mantine/core";
import { useTranslation } from "react-i18next";
import ProfilePanel from "./ProfilePanel.tsx";
import SchedulePanel from "./SchedulePanel.tsx";
import SettingsPanel from "./SettingsPanel.tsx";
import Icon from "../../common/icon/Icon.tsx";
import user from '../../assets/user.svg'
import settings from '../../assets/settings.svg'
import schedule from '../../assets/schedule.svg'
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import { useNavigate } from "react-router";
import { useAuthStore } from "../../hooks/authStore";
import { useEffect } from "react";

function ProfilePage() {
    const { t } = useTranslation("sections");
    const { isAuthenticated, loadingAuth } = useAuthStore();
    const navigate = useNavigate();
    useEffect(() => {
        if (!isAuthenticated && !loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, loadingAuth, navigate]);
    
    return (
        <PageWrapper>
            <Tabs
                w='1200px'
                h='calc(100vh - 60px)'
                variant="pills"
                color="indigo"
                orientation="vertical"
                defaultValue="profile"
                pt="3em"
            >
                <Tabs.List w="150px">
                    <Tabs.Tab
                        value="profile"
                        leftSection={<Icon src={user} />}
                    >
                        {t('profile')}
                    </Tabs.Tab>
                    <Tabs.Tab
                        value="schedule"
                        leftSection={<Icon src={schedule} />}
                    >
                        {t('schedule')}
                    </Tabs.Tab>
                    <Tabs.Tab
                        value="settings"
                        leftSection={<Icon src={settings} />}
                    >
                        {t('settings')}
                    </Tabs.Tab>
                    <Divider size="sm" />
                </Tabs.List>

                <Tabs.Panel value="profile">
                    <ProfilePanel />
                </Tabs.Panel>

                <Tabs.Panel value="schedule">
                    <SchedulePanel />
                </Tabs.Panel>

                <Tabs.Panel value="settings">
                    <SettingsPanel />
                </Tabs.Panel>

            </Tabs>
        </PageWrapper>
    );
}

export default ProfilePage