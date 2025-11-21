import { BrowserRouter } from 'react-router-dom';
import { MantineProvider } from '@mantine/core';
import { Notifications } from '@mantine/notifications';
import AppRoutes from "./AppRoutes.tsx";
import '@mantine/core/styles.css';
import '@mantine/notifications/styles.css';
import './App.css';
import { AuthProvider } from '../hooks/authStore';

function App() {
    return (
        <AuthProvider>
            <MantineProvider>
                <Notifications />
                <BrowserRouter>
                    <AppRoutes />
                </BrowserRouter>
            </MantineProvider>
        </AuthProvider>
    );
}

export default App;