import { Route, Routes } from 'react-router-dom';
import Overlay from "./Overlay.tsx";
import MainPage from "./MainPage.tsx";
import LoginPage from "./LoginPage.tsx";
import ProfilePage from './profile/ProfilePage.tsx';
import SubjectsPage from './SubjectsPage.tsx';

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Overlay />}>
                <Route index element={<MainPage />}/>
                <Route path="login" element={<LoginPage />}/>
                <Route path="profile" element={<ProfilePage />}/>
                <Route path="subjects" element={<SubjectsPage />}/>
            </Route>
        </Routes>
    );
}

export default AppRoutes;