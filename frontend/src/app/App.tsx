import { BrowserRouter } from 'react-router-dom';
import { MantineProvider } from '@mantine/core';
import AppRoutes from "../pages/AppRoutes.tsx";
import '../styles/app/App.css';
import '@mantine/core/styles.css';

function App(){
    return (
        <MantineProvider>
            <BrowserRouter>
                <AppRoutes/>
            </BrowserRouter>
        </MantineProvider>
    );
}

export default App;