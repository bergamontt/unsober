import type { Term } from "./Subject";

export const EnrollmentStage = {
    COURSES: 'COURSES',
    GROUPS: 'GROUPS',
    CORRECTION: 'CORRECTION',
    CLOSED: 'CLOSED'
} as const;

export type EnrollmentStage = typeof EnrollmentStage[keyof typeof EnrollmentStage];

export interface AppState {
    currentYear: number;
    term: Term;
    enrollmentStage: EnrollmentStage;
    updateTime: string;
}