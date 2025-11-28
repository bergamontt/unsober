import { Tabs } from '@mantine/core';
import { useNavigate } from 'react-router';
import { useTranslation } from "react-i18next";
import './Header.css';
import ProfileMenu from './ProfileMenu';
import { useAuthStore } from '../../hooks/authStore';
import { useStudentStore } from '../../hooks/studentStore';

function Header() {
    const navigate = useNavigate();
    const { isAuthenticated } = useAuthStore();
    const { user: student } = useStudentStore();
    const { t } = useTranslation("sections");
    const logoStyle = {
        fontWeight: "bold",
        fontSize: "1.2em"
    };
    return (
        <Tabs
            color="indigo"
            variant='unstyled'
            className='header-container'
            pos="sticky"
        >
            <Tabs.List>
                <Tabs.Tab
                    value="logo"
                    bg="black"
                    mr="auto"
                    onClick={() => navigate('/')}
                >
                    <span style={logoStyle}>
                        UNSOBER
                    </span>
                </Tabs.Tab>
                {
                    isAuthenticated &&
                    <>
                        <Tabs.Tab
                            value="info"
                            bg="black"
                            onClick={() => navigate('/info')}
                        >
                            {t("info")}
                        </Tabs.Tab>
                        <Tabs.Tab
                            value="courses"
                            bg="black"
                            onClick={() => navigate('/courses')}
                        >
                            {t("courses")}
                        </Tabs.Tab>
                        {
                            student &&
                            <>
                                <Tabs.Tab
                                    value='enrollments'
                                    bg='black'
                                    onClick={() => navigate('/enrollments')}
                                >
                                    {t("enrollments")}
                                </Tabs.Tab>
                                <Tabs.Tab
                                    value='schedule'
                                    bg='black'
                                    onClick={() => navigate('/schedule')}
                                >
                                    {t("schedule")}
                                </Tabs.Tab>
                            </>
                        }
                        <ProfileMenu />
                    </>
                }
            </Tabs.List>
        </Tabs>
    );
}

export default Header