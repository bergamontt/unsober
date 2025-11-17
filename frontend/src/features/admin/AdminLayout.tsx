import { Outlet, useNavigate } from "react-router";
import PageWrapper from "../../common/PageWrapper";
import AdminSidebar from "./AdminSidebar";
import { Flex, Group } from "@mantine/core";
import { useEffect } from "react";
import { useAuthStore } from "../../hooks/authStore";

function AdminLayout() {
    const { isAuthenticated, loadingAuth } = useAuthStore();
    const navigate = useNavigate();
    useEffect(() => {
        if (!isAuthenticated && !loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, navigate]);
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