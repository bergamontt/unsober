import { Center, Tabs  } from "@mantine/core";
import { useTranslation } from "react-i18next";
import LanguageMenu from "../components/common/LanguageMenu";

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
                orientation="vertical"
                defaultValue="gallery"
                mt="5em"
            >
                <Tabs.List>
                    <Tabs.Tab value="profile">
                        {t('profile')}
                    </Tabs.Tab>
                    <Tabs.Tab value="schedule" >
                        {t('schedule')}
                    </Tabs.Tab>
                    <Tabs.Tab value="settings">
                        {t('settings')}
                    </Tabs.Tab>
                </Tabs.List>

                <Tabs.Panel value="profile">
                    Profile tab content
                </Tabs.Panel>

                <Tabs.Panel value="schedule">
                    Schedule tab content
                </Tabs.Panel>

                <Tabs.Panel value="settings">
                    <LanguageMenu />
                </Tabs.Panel>

            </Tabs>
        </Center>
    );
}

export default ProfilePage