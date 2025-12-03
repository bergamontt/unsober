export interface Teacher {
    id: string;
    firstName: string;
    lastName: string;
    patronymic: string;
    email: string;
}

export interface TeacherDto {
    firstName?: string;
    lastName?: string;
    patronymic?: string;
    email?: string;
}

export interface TeacherFilterDto {
    email?: string;
}