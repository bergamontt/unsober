import PrismaticBurst from "../components/background/PrismaticBurst";
import {Center, Fieldset, TextInput, PasswordInput, Title, Button, Text} from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { useCallback, useState } from "react";
import { notifications } from "@mantine/notifications";
import { useAuthStore } from "../hooks/authStore";
import { login } from "../services/AuthService";
import loginIcon from '../assets/login.svg';
import Icon from "../components/common/Icon";
import '../styles/pages/LoginPage.css';

function LoginPage() {
    const { t } = useTranslation("auth");
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const { setToken } = useAuthStore();

    const validateInput = useCallback(() => {
        if (email.length < 5) {
            notifications.show({
                title: 'Error',
                message: 'Email must be at least 5 characters long',
                color: 'red',
            });
            return false;
        }
        if (password.length < 7) {
            notifications.show({
                title: 'Error',
                message: 'Password must be at least 7 characters long',
                color: 'red',
            });
            return false;
        }
        return true;
    }, [email, password]);

    const handleLogin = useCallback(async () => {
        if (!validateInput()) return;
        try {
            const response = await login({ email, password });
            setToken(response.token);
            notifications.show({
                title: 'Success!',
                message: 'You have successfully logged in.',
                color: 'green',
                onClose: () => navigate('/profile'),
            });
        } catch (err) {
            notifications.show({
                title: 'Error',
                message: 'Login failed. Check your email or password.',
                color: 'red',
            });
        }
    }, [email, password]);

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
                        size="md" mt="lg" radius="md"
                        withAsterisk c="white"
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <PasswordInput
                        label={t("password")}
                        placeholder={t("yourPassword")}
                        mt="md" size="md" radius="md"
                        withAsterisk c="white"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <Button
                        variant="outline"
                        size="md" radius="xl" mt="xl"
                        c="white" bd="1px solid #6F6F71"
                        rightSection={<Icon src={loginIcon} />}
                        justify="space-between" fullWidth
                        onClick={(e) => {e.preventDefault(); handleLogin()}}
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