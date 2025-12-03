import type { Course } from "./Course";
import type { Student } from "./Student";
import type { StudentEnrollment } from "./StudentEnrollment";

export const RequestStatus = {
    PENDING: "PENDING",
    ACCEPTED: "ACCEPTED",
    DECLINED: "DECLINED",
} as const;

export type RequestStatus = typeof RequestStatus[keyof typeof RequestStatus];

export interface WithdrawalRequest {
    id: string;
    studentEnrollment: StudentEnrollment;
    reason: string;
    status: RequestStatus;
    createdAt: string;
}

export interface WithdrawalRequestDto {
    studentEnrollmentId?: string;
    reason?: string;
}

export interface WithdrawalRequestFilterDto {
    reason?: string;
    status?: RequestStatus;
    studentId?: string;
}

export interface EnrollmentRequest {
    id: string;
    student: Student;
    course: Course;
    reason: string;
    status: RequestStatus;
    createdAt: string;
}

export interface EnrollmentRequestDto {
    studentId?: string;
    courseId?: string;
    reason?: string;
}

export interface EnrollmentRequestFilterDto {
    reason?: string;
    status?: RequestStatus;
    studentId?: string;
}