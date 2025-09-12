import PrismaticBurst from "../components/background/PrismaticBurst";
import {Center, Fieldset, TextInput, PasswordInput, Title, Button, Text} from "@mantine/core";
import Icon from "../components/common/Icon";
import '../styles/pages/LoginPage.css'
import login from '../assets/login.svg'

function LoginPage() {
    return(
        <div
            style={{
                width: '100%',
                height: 'calc(100vh - 60px)',
                zIndex: '1'
            }}
        >
            <Center className="login-page-container">
                <Fieldset
                    radius="md"
                    bg="#303033"
                    bd="1px solid #6F6F71"
                    mt="md"
                    p="2em"
                >
                    <Title order={2} c="white">
                        Увійти в обліковий запис
                    </Title>
                    <Text size="sm" mt="xs" c="white">
                        Увійдіть, щоби переглянути список дисциплін <br/> та ваш персональний розклад
                    </Text>
                    <TextInput
                        label="Робоча пошта"
                        placeholder="Ваша пошта"
                        size="md"
                        mt="lg"
                        radius="md"
                        withAsterisk
                        c="white"
                    />
                    <PasswordInput
                        label="Пароль"
                        placeholder="Ваш пароль"
                        mt="md"
                        size="md"
                        radius="md"
                        withAsterisk
                        c="white"
                    />
                    <Button
                        variant="outline"
                        size="md"
                        radius="xl"
                        mt="xl"
                        c="white"
                        bd="1px solid #6F6F71"
                        rightSection={<Icon src={login} />}
                        justify="space-between"
                        fullWidth
                    >
                        Увійти
                    </Button>
                </Fieldset>
            </Center>
            <PrismaticBurst
                animationType="rotate"
                intensity={0.65}
                speed={0.08}
                distort={4.2}
                rayCount={0}
                colors={['#000000ff', '#11035cff', '#000000ff']}
            />
        </div>
    );
}

export default LoginPage