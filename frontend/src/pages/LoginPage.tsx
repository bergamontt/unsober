import PrismaticBurst from "../components/background/PrismaticBurst";
import {Center, Fieldset, TextInput, PasswordInput, Title, Button, Text} from "@mantine/core";
import Icon from "../components/common/Icon";
import '../styles/pages/LoginPage.css';
import login from '../assets/login.svg';
import { useTranslation } from "react-i18next";

function LoginPage() {
    const { t } = useTranslation("auth");
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
                        {t("logInHeader")}
                    </Title>
                    <Text size="sm" mt="xs" c="white" style={{ whiteSpace: "pre-line" }}>
                        {t("logInSubtext")}
                    </Text>
                    <TextInput
                        label={t("workEmail")}
                        placeholder={t("yourEmail")}
                        size="md"
                        mt="lg"
                        radius="md"
                        withAsterisk
                        c="white"
                    />
                    <PasswordInput
                        label={t("password")}
                        placeholder={t("yourPassword")}
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
                        {t("logIn")}
                    </Button>
                </Fieldset>
            </Center>
            <PrismaticBurst
                animationType="rotate"
                intensity={0.65}
                speed={0.1}
                distort={4.2}
                rayCount={0}
                colors={['#000000ff', '#11035cff', '#000000ff']}
            />
        </div>
    );
}

export default LoginPage