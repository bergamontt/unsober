import { BrowserRouter } from 'react-router-dom';
import { MantineProvider } from '@mantine/core';
import { Notifications } from '@mantine/notifications';
import AppRoutes from "./AppRoutes.tsx";
import '@mantine/core/styles.css';
import '@mantine/notifications/styles.css';
import './App.css';
import { AuthProvider } from '../hooks/authStore';
import {ModalsProvider} from '@mantine/modals';


function App() {
    return (
        <AuthProvider>
            <MantineProvider>
                <ModalsProvider>
                    <Notifications />
                    <BrowserRouter>
                        <AppRoutes />
                    </BrowserRouter>
                </ModalsProvider>
            </MantineProvider>
        </AuthProvider>
    );
}

export default App;
