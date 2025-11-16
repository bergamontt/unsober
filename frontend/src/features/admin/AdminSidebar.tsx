import { NavLink, Stack } from "@mantine/core";
import Icon from "../../common/Icon";
import student from '../../assets/user.svg'
import request from '../../assets/request.svg'
import teacher from '../../assets/teacher.svg'
import course from '../../assets/course.svg'
import subject from '../../assets/subject.svg'
import speciality from '../../assets/speciality.svg'
import building from '../../assets/building.svg'
import faculty from '../../assets/faculty.svg'
import department from '../../assets/department.svg'

function AdminSidebar() {
    return(
        <Stack gap={0}>
            <NavLink
                label="Студенти"
                href="/admin/students"
                leftSection={<Icon src={student}/>}
            />
            <NavLink
                label="Заявки"
                href="/admin/enrollment"
                leftSection={<Icon src={request}/>}
            />
            <NavLink
                label="Викладачі"
                href="/admin/teachers"
                leftSection={<Icon src={teacher}/>}
            />
            <NavLink
                label="Курси"
                href="/admin/courses"
                leftSection={<Icon src={course}/>}
            />
            <NavLink
                label="Дисципліни"
                href="/admin/subjects"
                leftSection={<Icon src={subject}/>}
            />
            <NavLink
                label="Спеціальності"
                href="/admin/specialities"
                leftSection={<Icon src={speciality}/>}
            />
            <NavLink
                label="Факультети"
                href="/admin/faculties"
                leftSection={<Icon src={faculty}/>}
            />
            <NavLink
                label="Кафедри"
                href="/admin/departments"
                leftSection={<Icon src={department}/>}
            />
            <NavLink
                label="Корпуси"
                href="/admin/buildings"
                leftSection={<Icon src={building}/>}
            />
        </Stack>
    );
}

export default AdminSidebar