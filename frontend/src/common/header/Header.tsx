import { Tabs } from '@mantine/core';
import { useNavigate } from 'react-router';
import { useTranslation } from "react-i18next";
import './Header.css';
import ProfileMenu from './ProfileMenu';

function Header() {
    const navigate = useNavigate();
    const { t } = useTranslation("sections");
    const logoStyle = {
        fontWeight: "bold",
        fontSize: "1.2em"
    };
    return(
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
                <Tabs.Tab
                    value="disciplines"
                    bg="black"
                    onClick={() => navigate('/subjects')}
                >
                    {t("subjects")}
                </Tabs.Tab>
                <Tabs.Tab
                    value='schedule'
                    bg='black'
                    onClick={() => navigate('/schedule')}
                >
                    {t("schedule")}
                </Tabs.Tab>
                <ProfileMenu />
            </Tabs.List>
        </Tabs>
    );
}

export default Header