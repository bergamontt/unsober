import { Route, Routes } from 'react-router-dom';
import Overlay from "../common/overlay/Overlay.tsx";
import MainPage from "../features/main/MainPage.tsx";
import LoginPage from "../features/auth/LoginPage.tsx";
import CoursesPage from '../features/course/CoursesPage.tsx';
import CoursePage from '../features/course/CoursePage.tsx';
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
import SubjectPanel from '../features/admin/subjectPanel/SubjectPanel.tsx';
import InfoPage from '../features/info/InfoPage.tsx';
import StatePanel from '../features/admin/state/StatePanel.tsx';
import AuthGuard from '../common/wrappers/AuthGuard.tsx';
import { UserRole } from '../models/Auth.ts';
import SettingsPanel from '../features/profile/SettingsPanel.tsx';
import ProfilePanel from '../features/profile/ProfilePanel.tsx';
import ProfileLayout from '../features/profile/ProfileLayout.tsx';
import EnrollmentsPanel from '../features/enrollments/EnrollmentsPanel.tsx';
import CourseGroupPanel from '../features/group/CourseGroupPanel.tsx';
import StudentRequestPanel from '../features/request/StudentRequestPanel.tsx';

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Overlay />}>
                <Route index element={<MainPage />} />
                <Route path="login" element={<LoginPage />} />
                <Route
                    path="profile"
                    element={
                        <AuthGuard>
                            <ProfileLayout />
                        </AuthGuard>
                    }
                >
                    <Route path="" element={<ProfilePanel />} />
                    <Route path="settings" element={<SettingsPanel />} />

                    <Route
                        path="enrollments"
                        element={
                            <AuthGuard roles={[UserRole.STUDENT]}>
                                <EnrollmentsPanel />
                            </AuthGuard>
                        }
                    />

                    <Route
                        path="groups"
                        element={
                            <AuthGuard roles={[UserRole.STUDENT]}>
                                <CourseGroupPanel />
                            </AuthGuard>
                        }
                    />

                    <Route
                        path="requests"
                        element={
                            <AuthGuard roles={[UserRole.STUDENT]}>
                                <StudentRequestPanel />
                            </AuthGuard>
                        }
                    />
                </Route>

                <Route
                    path="courses"
                    element={
                        <AuthGuard>
                            <CoursesPage />
                        </AuthGuard>
                    }
                />
                <Route
                    path="course/:id"
                    element={
                        <AuthGuard>
                            <CoursePage />
                        </AuthGuard>
                    }
                />
                <Route
                    path="info"
                    element={
                        <AuthGuard>
                            <InfoPage />
                        </AuthGuard>
                    }
                />

                <Route
                    path="schedule"
                    element={
                        <AuthGuard roles={[UserRole.STUDENT]}>
                            <SchedulePage />
                        </AuthGuard>
                    }
                />

                <Route
                    path="/admin"
                    element={
                        <AuthGuard roles={[UserRole.ADMIN]}>
                            <AdminLayout />
                        </AuthGuard>
                    }
                >
                    <Route path="student" element={<StudentPanel />} />
                    <Route path="request" element={<RequestPanel />} />
                    <Route path="teacher" element={<TeacherPanel />} />
                    <Route path="course" element={<CoursePanel />} />
                    <Route path="subject" element={<SubjectPanel />} />
                    <Route path="speciality" element={<SpecialityPanel />} />
                    <Route path="faculty" element={<FacultyPanel />} />
                    <Route path="department" element={<DepartmentPanel />} />
                    <Route path="building" element={<BuildingPanel />} />
                    <Route path="state" element={<StatePanel />} />
                </Route>
            </Route>
        </Routes>
    );
}

export default AppRoutes;
