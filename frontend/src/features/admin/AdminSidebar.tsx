import { NavLink, Stack } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router";
import Icon from "../../common/icon/Icon.tsx";
import student from '../../assets/user.svg'
import request from '../../assets/request.svg'
import teacher from '../../assets/teacher.svg'
import course from '../../assets/course.svg'
import subject from '../../assets/subject.svg'
import speciality from '../../assets/speciality.svg'
import building from '../../assets/building.svg'
import faculty from '../../assets/faculty.svg'
import department from '../../assets/department.svg'
import state from '../../assets/server.svg'

function AdminSidebar() {
    const { t } = useTranslation("adminMenu");
    const navigate = useNavigate();

    const links = [
        { to: "/admin/student", label: t("students"), icon: student },
        { to: "/admin/request", label: t("requests"), icon: request },
        { to: "/admin/teacher", label: t("teachers"), icon: teacher },
        { to: "/admin/course", label: t("courses"), icon: course },
        { to: "/admin/subject", label: t("subjects"), icon: subject },
        { to: "/admin/speciality", label: t("specialities"), icon: speciality },
        { to: "/admin/faculty", label: t("faculties"), icon: faculty },
        { to: "/admin/department", label: t("departments"), icon: department },
        { to: "/admin/building", label: t("buildings"), icon: building },
        { to: "/admin/state", label: t("state"), icon: state }
    ];

    const navLinks = links.map(({ to, label, icon }) => (
        <NavLink
            key={to}
            label={label}
            onClick={() => navigate(to)}
            leftSection={<Icon src={icon} />}
            active={window.location.href.endsWith(to)}
        />
    ));

    return (
        <Stack gap={0}>
            {navLinks}
        </Stack>
    );
}


export default AdminSidebar
