import { BrowserRouter } from 'react-router-dom';
import { MantineProvider } from '@mantine/core';
import { Notifications } from '@mantine/notifications';
import AppRoutes from "./AppRoutes.tsx";
import '@mantine/core/styles.css';
import '@mantine/notifications/styles.css';
import './App.css';

function App(){
    return (
        <MantineProvider>
            <Notifications />
            <BrowserRouter>
                <AppRoutes/>
            </BrowserRouter>
        </MantineProvider>
    );
}

export default App;