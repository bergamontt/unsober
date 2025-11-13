import { Route, Routes } from 'react-router-dom';
import Overlay from "./Overlay.tsx";
import MainPage from "../main/MainPage.tsx";
import LoginPage from "../auth/LoginPage.tsx";
import ProfilePage from '../profile/ProfilePage.tsx';
import SubjectsPage from '../subject/SubjectsPage.tsx';
import SubjectPage from '../subject/SubjectPage.tsx';
import SchedulePage from '../schedule/SchedulePage.tsx';

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Overlay />}>
                <Route index element={<MainPage />}/>
                <Route path="login" element={<LoginPage />}/>
                <Route path="profile" element={<ProfilePage />}/>
                <Route path="subjects" element={<SubjectsPage />}/>
                <Route path="subject/:id" element={<SubjectPage />}/>
                <Route path="schedule" element={<SchedulePage />}/>
            </Route>
        </Routes>
    );
}

export default AppRoutes;