import { Route, Routes } from 'react-router-dom';
import Overlay from "../common/overlay/Overlay.tsx";
import MainPage from "../features/main/MainPage.tsx";
import LoginPage from "../features/auth/LoginPage.tsx";
import ProfilePage from '../features/profile/ProfilePage.tsx';
import SubjectsPage from '../features/subject/SubjectsPage.tsx';
import SubjectPage from '../features/subject/SubjectPage.tsx';
import SchedulePage from '../features/schedule/SchedulePage.tsx';
import AdminLayout from '../features/admin/AdminLayout.tsx';
import StudentPanel from '../features/admin/studentPanel/StudentPanel.tsx';
import RequestPanel from '../features/admin/requestPanel/RequestPanel.tsx';
import TeacherPanel from '../features/admin/teacherPanel/TeacherPanel.tsx';
import CoursePanel from '../features/admin/coursePanel/CoursePanel.tsx';
import SpecialityPanel from '../features/admin/specialityPanel/SpecialityPanel.tsx';
import FacultyPanel from '../features/admin/facultyPanel/FacultyPanel.tsx';
import DepartmentPanel from '../features/admin/departmentPanel/DepartmentPanel.tsx';
import BuildingPanel from '../features/admin/buildingPanel/BuildingPanel.tsx';

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
                
                <Route path="/admin" element={<AdminLayout />}>
                    <Route path="student" element={<StudentPanel />}/>
                    <Route path="request" element={<RequestPanel />}/>
                    <Route path="teacher" element={<TeacherPanel />}/>
                    <Route path="course" element={<CoursePanel />}/>
                    <Route path="subject" element={<SubjectPage />}/>
                    <Route path="speciality" element={<SpecialityPanel />}/>
                    <Route path="faculty" element={<FacultyPanel />}/>
                    <Route path="department" element={<DepartmentPanel />}/>
                    <Route path="building" element={<BuildingPanel />}/>
                </Route>
            </Route>
        </Routes>
    );
}

export default AppRoutes;