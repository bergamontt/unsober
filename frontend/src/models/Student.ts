import type { Speciality } from "./Speciality";

export interface Student {
    id: string;
    firstName: string;
    lastName: string;
    patronymic: string;
    recordBookNumber: string;
    email: string;
    speciality: Speciality;
    studyYear: number;
}