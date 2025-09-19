import { Group, Flex, NavLink, Space  } from '@mantine/core';
import '../../styles/common/Header.css';
import { useNavigate } from 'react-router';
import { useTranslation } from "react-i18next";
import LanguageMenu from './LanguageMenu';

function Header() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    return(
        <Group
            wrap="nowrap"
            className="header-container"
            justify="space-between"
        >
            <Flex>
                <NavLink
                    onClick={() => navigate('/main')}
                    label={
                        <span style={
                            {fontWeight: "bold",
                             fontSize: "1.2em"}
                        }>
                            UNSOBER
                        </span>
                    }
                    bg="black"
                />
            </Flex>
            <Flex>
                <LanguageMenu />

                <Space w="xl" />

                <NavLink
                    href="#required-for-focus"
                    label={t("disciplines")}
                    bg="black"
                />
                <NavLink
                    href="#required-for-focus"
                    label={t("schedule")}
                    bg="black"
                />
                <NavLink
                    href="#required-for-focus"
                    label={t("profile")}
                    bg="black"
                />
            </Flex>
        </Group>
    );
}

export default Header