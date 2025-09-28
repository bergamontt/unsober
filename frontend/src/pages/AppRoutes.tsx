import { Route, Routes } from 'react-router-dom';
import Overlay from "./Overlay.tsx";
import MainPage from "./MainPage.tsx";
import LoginPage from "./LoginPage.tsx";
import ProfilePage from './ProfilePage.tsx';
import SubjectsPage from './SubjectsPage.tsx';
import SubjectPage from './SubjectPage.tsx';

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Overlay />}>
                <Route index element={<MainPage />}/>
                <Route path="login" element={<LoginPage />}/>
                <Route path="profile" element={<ProfilePage />}/>
                <Route path="subjects" element={<SubjectsPage />}/>
                <Route path="subject/:id" element={<SubjectPage />}/>
            </Route>
        </Routes>
    );
}

export default AppRoutes;