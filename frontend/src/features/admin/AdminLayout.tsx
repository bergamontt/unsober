import { Outlet } from "react-router";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import AdminSidebar from "./AdminSidebar";
import { Flex, Group } from "@mantine/core";

function AdminLayout() {
    return (
        <PageWrapper>
            <Flex align="flex-start">
                <AdminSidebar />
                <Group w="100%" ml="md" grow>
                    <Outlet />
                </Group>
            </Flex>
        </PageWrapper>
    );
}

export default AdminLayout