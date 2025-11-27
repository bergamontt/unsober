import type { Department } from "./Department";

export interface Speciality {
    id: string;
    department?: Department;
    name: string;
    description: string;
}

export interface SpecialityDto {
    departmentId?: string;
    name?: string;
    description?: string;
}