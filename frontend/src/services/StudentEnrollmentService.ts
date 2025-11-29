import api from './api.ts';
import type { StudentEnrollment } from '../models/StudentEnrollment.ts';

export const getAllEnrollmentsByStudentId = async (studentId: string | null):
    Promise<StudentEnrollment[]> => {
    if (studentId == null)
        return [];
    const response = await api.get<StudentEnrollment[]>(`/student-enrollment/student/${studentId}`);
    return response.data;
};

export const getAllEnrollmentsByStudentIdAndYear = async (
    studentId: string | null,
    year: number | null
): Promise<StudentEnrollment[]> => {
    if (studentId == null || year == null)
        return [];
    const response = await api.get<StudentEnrollment[]>('/student-enrollment/student-and-year', {
        params: { studentId, year },
    });
    return response.data;
};

export const getAllEnrollmentsByCourseId = async (courseId: string | null):
    Promise<StudentEnrollment[]> => {
    if (courseId == null)
        return [];
    const response = await api.get<StudentEnrollment[]>(`/student-enrollment/course/${courseId}`);
    return response.data;
};

export const getAllYearsByStudentId = async (studentId: string | null):
    Promise<number[]> => {
    if (studentId == null)
        return [];
    const response = await api.get<number[]>(`/student-enrollment/years/${studentId}`);
    return response.data;
};

export const existsEnrollment = async (studentId: string | null, courseId: string | null):
    Promise<boolean> => {
    if (studentId == null || courseId == null)
        return false;
    const response = await api.get<boolean>("/student-enrollment/exists", {
        params: { studentId, courseId },
    });
    return response.data;
}

export const enrollSelf = async (courseId: string):
    Promise<StudentEnrollment> => {
    console.log(courseId);
    const response = await api.post<StudentEnrollment>("/student-enrollment/enroll-self",
        null, { params: { courseId } },);
    return response.data;
}

export const changeGroup = async (enrollmentId: string, groupId: string):
    Promise<StudentEnrollment> => {
    const response = await api.patch<StudentEnrollment>("/student-enrollment/change-group", null, {
        params: { enrollmentId, groupId },
    });
    return response.data;
}

export const withdrawFromGroup = async (enrollmentId: string): Promise<void> => {
    await api.patch<void>(`/student-enrollment/clear-group/${enrollmentId}`);
}