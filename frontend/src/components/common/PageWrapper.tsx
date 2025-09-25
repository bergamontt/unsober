import { Center } from "@mantine/core";
import type { ReactNode } from "react";

type PageWrapperProps = {
    children?: ReactNode;
};

function PageWrapper({children} : PageWrapperProps) {
    return (
        <Center w="100%" mih='calc(100vh - 60px)'>
            {children}
        </Center>
    );
}

export default PageWrapper