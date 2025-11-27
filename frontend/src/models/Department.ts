import type { Faculty } from "./Faculty";

export interface Department {
    id: string;
    faculty: Faculty;
    name: string;
    description: string;
}

export interface DepartmentDto {
    facultyId?: string;
    name?: string;
    description?: string;
}