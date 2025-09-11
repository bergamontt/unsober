import { Group, Flex, NavLink  } from '@mantine/core';
import '../../styles/common/Header.css'

function Header() {
    return(
        <Group
            wrap="nowrap"
            className="header-container"
            justify="space-between"
        >
            <Flex>
                <NavLink
                    href="#required-for-focus"
                    label={
                        <span style={
                            {fontWeight: "bold",
                             fontSize: "1.2em"}
                        }>
                            UNSOBER
                        </span>
                    }
                    bg="black"
                />
            </Flex>
            <Flex>
                <NavLink
                    href="#required-for-focus"
                    label="Дисципліни"
                    bg="black"
                />
                <NavLink
                    href="#required-for-focus"
                    label="Розклад"
                    bg="black"
                />
                <NavLink
                    href="#required-for-focus"
                    label="Профіль"
                    bg="black"
                />
            </Flex>
        </Group>
    );
}

export default Header