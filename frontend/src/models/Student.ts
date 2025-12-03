import type { Speciality } from "./Speciality";

export const StudentStatus = {
    STUDYING: 'STUDYING',
    EXPELLED: 'EXPELLED',
    GRADUATED: 'GRADUATED'
} as const;

export type StudentStatus = typeof StudentStatus[keyof typeof StudentStatus];

export interface Student {
    id: string;
    firstName: string;
    lastName: string;
    patronymic: string;
    recordBookNumber: string;
    email: string;
    speciality: Speciality;
    studyYear: number;
    status: StudentStatus;
}

export interface StudentDto {
    firstName?: string;
    lastName?: string;
    patronymic?: string;
    recordBookNumber?: string;
    email?: string;
    password?: string;
    specialityId?: string;
    studyYear?: number;
    status?: StudentStatus;
}

export interface StudentFilterDto {
    email?: string;
}