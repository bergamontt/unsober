import {Flex} from "@mantine/core";
import {Outlet} from "react-router-dom";
import Header from "../components/overlay/Header.tsx";

function Overlay() {
    return(
        <Flex
            direction="column"
        >
            <Header />
            <Outlet />
        </Flex>
    );
}

export default Overlay