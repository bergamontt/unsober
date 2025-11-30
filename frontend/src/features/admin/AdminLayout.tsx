import { Outlet } from "react-router";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import AdminSidebar from "./AdminSidebar";
import { Flex, Group } from "@mantine/core";
import AuthGuard from "../../common/wrappers/AuthGuard.tsx";
import { UserRole } from "../../models/Auth.ts";

function AdminLayout() {
    return (
        <AuthGuard roles={[UserRole.ADMIN]}>
            <PageWrapper>
                <Flex align="flex-start">
                    <AdminSidebar />
                    <Group w="100%" ml="md" grow>
                        <Outlet />
                    </Group>
                </Flex>
            </PageWrapper>
        </AuthGuard>
    );
}

export default AdminLayout