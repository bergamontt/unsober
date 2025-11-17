import { NavLink, Stack } from "@mantine/core";
import Icon from "../../common/Icon";
import student from '../../assets/user.svg'
import request from '../../assets/request.svg'
import teacher from '../../assets/teacher.svg'
import course from '../../assets/course.svg'
import subject from '../../assets/subject.svg'
import speciality from '../../assets/speciality.svg'
import building from '../../assets/building.svg'
import faculty from '../../assets/faculty.svg'
import department from '../../assets/department.svg'
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router";

function AdminSidebar() {
    const { t } = useTranslation("adminMenu");
    const navigate = useNavigate();

    const links = [
        { to: "/admin/students", label: t("students"), icon: student },
        { to: "/admin/requests", label: t("requests"), icon: request },
        { to: "/admin/teachers", label: t("teachers"), icon: teacher },
        { to: "/admin/courses", label: t("courses"), icon: course },
        { to: "/admin/subjects", label: t("subjects"), icon: subject },
        { to: "/admin/specialities", label: t("specialities"), icon: speciality },
        { to: "/admin/faculties", label: t("faculties"), icon: faculty },
        { to: "/admin/departments", label: t("departments"), icon: department },
        { to: "/admin/buildings", label: t("buildings"), icon: building },
    ];

    return (
        <Stack gap={0}>
            {links.map(({ to, label, icon }) => (
                <NavLink
                    key={to}
                    label={label}
                    onClick={() => navigate(to)}
                    leftSection={<Icon src={icon} />}
                />
            ))}
        </Stack>
    );
}


export default AdminSidebar