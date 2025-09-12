import {Center, Button, Stack, Group} from "@mantine/core";
import DarkVeil from "../components/background/DarkVeil.tsx";
import '../styles/pages/MainPage.css'
import {useNavigate} from "react-router";

function MainPage() {
    const navigate = useNavigate();
    const handleLogin = () => {navigate('/login', { replace: true })};
    return(
        <div 
            style={{
                width: '100%',
                height: 'calc(100vh - 60px)'
            }}>
            <Center className="annotation-container">
                <Stack align="center">
                    Система автоматизованого <br/> запису на вибиркові дисципліни
                    <Group
                        grow
                        w="80%"
                    >
                        <Button
                            variant="white"
                            radius="xl"
                            onClick={handleLogin}
                        >
                            Увійти
                        </Button>
                        <Button
                            variant="outline"
                            color="indigo"
                            radius="xl"
                        >
                            Новини
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
