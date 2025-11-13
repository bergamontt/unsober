import { Route, Routes } from 'react-router-dom';
import Overlay from "../common/Overlay.tsx";
import MainPage from "../features/main/MainPage.tsx";
import LoginPage from "../features/auth/LoginPage.tsx";
import ProfilePage from '../features/profile/ProfilePage.tsx';
import SubjectsPage from '../features/subject/SubjectsPage.tsx';
import SubjectPage from '../features/subject/SubjectPage.tsx';
import SchedulePage from '../features/schedule/SchedulePage.tsx';
import AdminPage from '../features/admin/AdminPage.tsx';

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
                <Route path="admin" element={<AdminPage />}/>
            </Route>
        </Routes>
    );
}

export default AppRoutes;