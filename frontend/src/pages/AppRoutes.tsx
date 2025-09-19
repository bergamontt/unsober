import { Route, Routes } from 'react-router-dom';
import Overlay from "./Overlay.tsx";
import MainPage from "./MainPage.tsx";
import LoginPage from "./LoginPage.tsx";
import ProfilePage from './ProfilePage.tsx';

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Overlay />}>
                <Route path="main" element={<MainPage />}/>
                <Route path="login" element={<LoginPage />}/>
                <Route path="profile" element={<ProfilePage />}/>
            </Route>
        </Routes>
    );
}

export default AppRoutes;