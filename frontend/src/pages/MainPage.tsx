import {Center, Button, Stack, Group} from "@mantine/core";
import DarkVeil from "../components/background/DarkVeil.tsx";
import '../styles/pages/MainPage.css'

function MainPage() {
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
