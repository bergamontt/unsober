import { Menu, Tabs } from '@mantine/core';
import { useNavigate } from 'react-router';
import userSvg from '../../assets/user.svg';
import settings from '../../assets/settings.svg'
import admin from '../../assets/admin.svg'
import Icon from '../Icon';
import { useTranslation } from 'react-i18next';
import { useUserDetailsStore } from '../../hooks/userDetailsStore';
import { useAdminStore } from '../../hooks/adminStore';

function ProfileMenu() {
    const navigate = useNavigate();
    const { t } = useTranslation("sections");
    const { user: adminUser } = useAdminStore();
    const { firstName, lastName } = useUserDetailsStore();
    return (
        <Menu shadow="md">
            <Menu.Target>
                <Tabs.Tab
                    value="profile"
                    bg="black"
                    onClick={(e) => e.preventDefault()}>
                    {t("profile")}
                </Tabs.Tab>
            </Menu.Target>
            <Menu.Dropdown>
                <Menu.Label>
                    {(firstName ?? "") + " " + (lastName ?? "")}
                </Menu.Label>
                <Menu.Divider />
                <Menu.Item
                    leftSection={<Icon src={userSvg} />}
                    onClick={() => { navigate('/profile') }}
                >
                    {t("aboutMe")}
                </Menu.Item>
                <Menu.Item
                    leftSection={<Icon src={settings} />}
                    onClick={() => { navigate('/profile') }}
                >
                    {t("settings")}
                </Menu.Item>
                {
                    adminUser &&
                    <>
                        <Menu.Divider />
                        <Menu.Item
                            leftSection={<Icon src={admin} />}
                            onClick={() => { navigate('/admin') }}
                        >
                            {t("adminPanel")}
                        </Menu.Item>
                    </>
                }
            </Menu.Dropdown>
        </Menu>
    );
}

export default ProfileMenu