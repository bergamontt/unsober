import PrismaticBurst from "../components/background/PrismaticBurst";
import {Center, Fieldset, TextInput, PasswordInput, Title, Button, Text} from "@mantine/core";
import '../styles/pages/LoginPage.css'

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
                    className="login-fieldset"
                    mt="md"
                    p="2em"
                >
                    <Title order={2}>
                        Увійти в обліковий запис
                    </Title>
                    <Text size="sm" mt="xs">
                        Увійдіть, щоби переглянути список дисциплін <br/> та ваш персональний розклад
                    </Text>
                    <TextInput
                        label="Робоча пошта"
                        placeholder="Ваша пошта"
                        size="lg"
                        mt="lg"
                        radius="md"
                        withAsterisk
                    />
                    <PasswordInput
                        label="Пароль"
                        placeholder="Ваш пароль"
                        mt="md"
                        size="lg"
                        radius="md"
                        withAsterisk
                    />
                    <Button
                        variant="outline"
                        size="lg"
                        radius="xl"
                        mt="xl"
                        fullWidth
                    >
                        Увійти
                    </Button>
                </Fieldset>
            </Center>
            <PrismaticBurst
                animationType="rotate"
                intensity={0.5}
                speed={0.08}
                distort={4.2}
                rayCount={0}
                colors={['#000000ff', '#11035cff', '#000000ff']}
            />
        </div>
    );
}

export default LoginPage