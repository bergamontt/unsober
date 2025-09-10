import { Route, Routes } from 'react-router-dom';
import MainPage from './MainPage.tsx';

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<MainPage />} />
        </Routes>
    )
}

export default AppRoutes;