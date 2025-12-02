import { NavLink, Stack } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router";
import Icon from "../../common/icon/Icon.tsx";
import user from '../../assets/user.svg';
import settings from '../../assets/settings.svg';
import requests from '../../assets/request.svg';
import groups from '../../assets/student-hat-light.svg';
import enrollments from '../../assets/study-plan.svg';
import { useStudentStore } from "../../hooks/studentStore.ts";

function ProfileSidebar() {
    const { t } = useTranslation("sections");
    const { user: student } = useStudentStore();
    const navigate = useNavigate();

    const links = [
        { to: "/profile", label: t("profile"), icon: user },
        { to: "/profile/settings", label: t("settings"), icon: settings },
        ...(student
            ? [
                { to: "/profile/enrollments", label: t("enrollments"), icon: enrollments },
                { to: "/profile/groups", label: t("groups"), icon: groups },
                { to: "/profile/requests", label: t("requests"), icon: requests },
            ]
            : [])
    ];

    const navLinks = links.map(({ to, label, icon }) => (
        <NavLink
            key={to}
            label={label}
            onClick={() => navigate(to)}
            leftSection={<Icon src={icon} />}
        />
    ));

    return (
        <Stack gap={0}>
            {navLinks}
        </Stack>
    );
}


export default ProfileSidebar;