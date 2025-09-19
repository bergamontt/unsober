import { Center, Tabs, Divider  } from "@mantine/core";
import { useTranslation } from "react-i18next";
import ProfilePanel from "./ProfilePanel";
import SchedulePanel from "./SchedulePanel";
import SettingsPanel from "./SettingsPanel";
import Icon from "../../components/common/Icon";
import user from '../../assets/user.svg'
import settings from '../../assets/settings.svg'
import schedule from '../../assets/schedule.svg'

function ProfilePage() {
    const { t } = useTranslation();
    return (
        <Center
            w="100%"
            h='calc(100vh - 60px)'
        >
            <Tabs
                w='1200px'
                h='calc(100vh - 60px)'
                variant="pills"
                color="indigo"
                orientation="vertical"
                defaultValue="profile"
                pt="4em"
            >
                <Tabs.List
                    w="150px"
                >
                    <Tabs.Tab
                        value="profile"
                        leftSection={<Icon src={user}/>}
                    >
                        {t('profile')}
                    </Tabs.Tab>
                    <Tabs.Tab
                        value="schedule"
                        leftSection={<Icon src={schedule}/>}
                    >
                        {t('schedule')}
                    </Tabs.Tab>
                    <Tabs.Tab
                        value="settings"
                        leftSection={<Icon src={settings}/>}
                    >
                        {t('settings')}
                    </Tabs.Tab>
                    <Divider size="sm"/>
                </Tabs.List>

                <Tabs.Panel value="profile">
                    <ProfilePanel/>
                </Tabs.Panel>

                <Tabs.Panel value="schedule">
                    <SchedulePanel/>
                </Tabs.Panel>

                <Tabs.Panel value="settings">
                    <SettingsPanel/>
                </Tabs.Panel>

            </Tabs>
        </Center>
    );
}

export default ProfilePage