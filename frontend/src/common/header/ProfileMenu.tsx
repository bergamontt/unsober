import { Menu, Tabs } from '@mantine/core';
import { useNavigate } from 'react-router';
import userSvg from '../../assets/user.svg';
import settings from '../../assets/settings.svg';
import admin from '../../assets/admin.svg';
import requests from '../../assets/request.svg';
import groups from '../../assets/student-hat-light.svg';
import enrollments from '../../assets/study-plan.svg';
import logOut from '../../assets/log-out.svg';
import Icon from '../icon/Icon.tsx';
import { useTranslation } from 'react-i18next';
import { useUserDetailsStore } from '../../hooks/userDetailsStore';
import { useAdminStore } from '../../hooks/adminStore';
import { useStudentStore } from '../../hooks/studentStore.ts';
import { useAuthStore } from '../../hooks/authStore.tsx';

function ProfileMenu() {
    const navigate = useNavigate();
    const { t } = useTranslation("sections");
    const { removeToken } = useAuthStore();
    const { user: adminUser } = useAdminStore();
    const { user: student } = useStudentStore();
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
                    onClick={() => { navigate('/profile/settings') }}
                >
                    {t("settings")}
                </Menu.Item>

                {
                    student &&
                    <>
                        <Menu.Item
                            leftSection={<Icon src={enrollments} />}
                            onClick={() => { navigate('/profile/enrollments') }}
                        >
                            {t("enrollments")}
                        </Menu.Item>
                        <Menu.Item
                            leftSection={<Icon src={groups} />}
                            onClick={() => { navigate('/profile/groups') }}
                        >
                            {t("groups")}
                        </Menu.Item>
                        <Menu.Item
                            leftSection={<Icon src={requests} />}
                            onClick={() => { navigate('/profile/requests') }}
                        >
                            {t("requests")}
                        </Menu.Item>
                    </>
                }

                {
                    adminUser &&
                    <>
                        <Menu.Divider />
                        <Menu.Item
                            leftSection={<Icon src={admin} />}
                            onClick={() => { navigate('/admin/student') }}
                        >
                            {t("adminPanel")}
                        </Menu.Item>
                    </>
                }

                <Menu.Item
                    color="red"
                    leftSection={<Icon src={logOut} />}
                    onClick={() => { 
                        removeToken();
                        navigate('/admin/student');
                    }}
                >
                    {t("logOut")}
                </Menu.Item>
            </Menu.Dropdown>
        </Menu>
    );
}

export default ProfileMenu