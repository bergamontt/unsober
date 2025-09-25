import { Center, Button, Stack, Group } from "@mantine/core";
import DarkVeil from "../components/background/DarkVeil.tsx";
import { useNavigate } from "react-router";
import { useTranslation } from "react-i18next";
import '../styles/pages/MainPage.css';

function MainPage() {
    const { t } = useTranslation(["common", "sections", "auth"]);
    const navigate = useNavigate();
    const handleLogin = () => { navigate('/login', { replace: true }) };
    return (
        <div
            style={{
                width: '100%',
                height: 'calc(100vh - 60px)'
            }}>
            <Center className="annotation-container">
                <Stack align="center" style={{ whiteSpace: "pre-line" }}>
                    {t("common:mainPageGreeting")}
                    <Group
                        grow
                        w="80%"
                    >
                        <Button
                            variant="white"
                            radius="xl"
                            onClick={handleLogin}
                        >
                            {t("auth:logIn")}
                        </Button>
                        <Button
                            variant="outline"
                            color="indigo"
                            radius="xl"
                        >
                            {t("sections:news")}
                        </Button>
                    </Group>
                </Stack>
            </Center>
            <DarkVeil
                hueShift={25}
                speed={0.6}
            />
        </div>
    );
}

export default MainPage;
