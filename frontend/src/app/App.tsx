import { BrowserRouter } from 'react-router-dom';
import AppRoutes from "./AppRoutes";
import { MantineProvider } from '@mantine/core';
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