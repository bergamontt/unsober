import { Outlet, useNavigate } from "react-router";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import AdminSidebar from "./AdminSidebar";
import { Flex, Group } from "@mantine/core";
import { useEffect } from "react";
import { useAuthStore } from "../../hooks/authStore";

function AdminLayout() {
    const {isAuthenticated, loadingAuth, currentRoles} = useAuthStore();
    const isAdmin = currentRoles.includes("ADMIN");
    const navigate = useNavigate();
    useEffect(() => {
        if ((!isAuthenticated || !isAdmin) && !loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, loadingAuth, isAdmin, navigate]);
    return (
        <PageWrapper>
            <Flex align="flex-start">
                <AdminSidebar/>
                <Group w="100%" ml="md" grow>
                    <Outlet />
                </Group>
            </Flex>
        </PageWrapper>
    );
}

export default AdminLayout