export const Term = {
    AUTUMN: 'AUTUMN',
    SPRING: 'SPRING',
    SUMMER: 'SUMMER'
} as const;

export type Term = typeof Term[keyof typeof Term];

export const EducationLevel = {
    BATCHELOR: 'BATCHELOR',
    MASTER: 'MASTER',
} as const;

export type EducationLevel = typeof EducationLevel[keyof typeof EducationLevel];

export interface Subject {
    id: string;
    name: string;
    annotation?: string;
    facultyName?: string;
    departmentName?: string;
    educationLevel: EducationLevel;
    credits: number;
    hoursPerWeek: number;
    term: Term;
}

export interface SubjectDto {
    name?: string;
    annotation?: string;
    facultyName?: string;
    departmentName?: string;
    educationLevel?: EducationLevel;
    credits?: number;
    hoursPerWeek?: number;
    term?: Term;
}

export interface SubjectFilterDto {
    name?: string;
}