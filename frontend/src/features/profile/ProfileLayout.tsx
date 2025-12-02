import { Flex, Group } from "@mantine/core";
import PageWrapper from "../../common/pageWrapper/PageWrapper";
import { Outlet } from "react-router";
import ProfileSidebar from "./ProfileSidebar";

function ProfileLayout() {
    return (
        <PageWrapper>
            <Flex align="flex-start">
                <ProfileSidebar />
                <Group w="100%" ml="md">
                    <Outlet />
                </Group>
            </Flex>
        </PageWrapper>
    );
}

export default ProfileLayout;