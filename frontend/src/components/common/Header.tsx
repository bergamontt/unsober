import { Tabs  } from '@mantine/core';
import { useNavigate } from 'react-router';
import { useTranslation } from "react-i18next";
import '../../styles/common/Header.css';

function Header() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const logoStyle = {
        fontWeight: "bold",
        fontSize: "1.2em"
    };
    return(
        <Tabs
            variant='unstyled'
            className='header-container'
        >
            <Tabs.List >
               <Tabs.Tab
                    value="logo"
                    bg="black"
                    mr="auto"
                        onClick={() => navigate('/main')}
                >
                    <span style={logoStyle}>
                        UNSOBER
                    </span>
                </Tabs.Tab>
                <Tabs.Tab
                    value="disciplines"
                    bg="black"
                >
                    {t("disciplines")}
                </Tabs.Tab>
                <Tabs.Tab
                    value='schedule'
                    bg='black'
                >
                    {t("schedule")}
                </Tabs.Tab>
                <Tabs.Tab
                    value='profile'
                    bg="black"
                >
                    {t("profile")}
                </Tabs.Tab>
            </Tabs.List>
        </Tabs>
    );
}

export default Header