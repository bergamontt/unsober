import type { Course } from "./Course";
import type { CourseGroup } from "./CourseGroup";
import type { Student } from "./Student";

export const EnrollmentStatus = {
    ENROLLED: 'ENROLLED',
    FORCE_ENROLLED: 'FORCE_ENROLLED',
    WITHDRAWN: 'WITHDRAWN'
} as const;

export type EnrollmentStatus = typeof EnrollmentStatus[keyof typeof EnrollmentStatus];

export interface StudentEnrollment {
    id: string;
    student: Student;
    course: Course;
    group?: CourseGroup;
    status: EnrollmentStatus;
    enrollmentYear: number;
    createdAt: string;
}

export interface StudentEnrollmentDto {
    studentId?: string;
    courseId?: string;
    groupId?: string;
    enrollmentYear?: number;
}
