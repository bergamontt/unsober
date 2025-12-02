import { Outlet } from "react-router";
import PageWrapper from "../../common/pageWrapper/PageWrapper";
import AdminSidebar from "./AdminSidebar";
import { Flex, Group, Box } from "@mantine/core";

function AdminLayout() {
    return (
        <PageWrapper>
            <Flex align="flex-start">
                <Box
                    pos="sticky" 
                    top={102} w={200}
                    mah="calc(100vh - 102px)"
                    style={{overflowY : "auto"}}
                >
                    <AdminSidebar/>
                </Box>
                <Group w="100%" ml="md" grow>
                    <Outlet />
                </Group>
            </Flex>
        </PageWrapper>
    );
}

export default AdminLayout