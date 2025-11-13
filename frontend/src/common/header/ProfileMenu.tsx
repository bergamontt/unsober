import { Menu, Tabs} from '@mantine/core';
import { useNavigate } from 'react-router';
import user from '../../assets/user.svg';
import settings from '../../assets/settings.svg'
import admin from '../../assets/admin.svg'
import Icon from '../Icon';

function ProfileMenu() {
    const navigate = useNavigate();
    return(
        <Menu shadow="md">
            <Menu.Target>
                <Tabs.Tab
                    value="profile"
                    bg="black"
                    onClick={(e) => e.preventDefault()}
                >
                    Профіль
                </Tabs.Tab>
            </Menu.Target>
            <Menu.Dropdown>
                <Menu.Label>
                    Джамбо Мамбо
                </Menu.Label>
                <Menu.Divider/>
                <Menu.Item
                    leftSection={<Icon src={user} />}
                    onClick={() => {navigate('/profile')}}
                >
                    Про себе
                </Menu.Item>
                <Menu.Item
                    leftSection={<Icon src={settings} />}
                    onClick={() => {navigate('/profile')}}
                >
                    Налаштування
                </Menu.Item>
                <Menu.Divider/>
                <Menu.Item
                    leftSection={<Icon src={admin} />}
                    onClick={() => {navigate('/admin')}}
                >
                    Панель адміна
                </Menu.Item>
            </Menu.Dropdown>
        </Menu>
    );
}

export default ProfileMenu