import { Center, Stack } from "@mantine/core";
import type { ReactNode } from "react";

type PageWrapperProps = {
    children?: ReactNode;
};

function PageWrapper({children} : PageWrapperProps) {
    return (
        <Center w="100%" mih='calc(100vh - 60px)'>
            <Stack w='1200px' pt="3em" mih='calc(100vh - 60px)' pl="1em" pr="1em" pb="1em">
                {children}
            </Stack>
        </Center>
    );
}

export default PageWrapper