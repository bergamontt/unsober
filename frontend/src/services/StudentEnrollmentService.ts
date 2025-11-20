import api from './api.ts';
import type { StudentEnrollment } from '../models/StudentEnrollment.ts';

export const getAllEnrollmentsByStudentId = async (studentId: string | null):
    Promise<StudentEnrollment[]> => {
    if (studentId == null)
        return [];
    const response = await api.get<StudentEnrollment[]>(`/student-enrollment/student/${studentId}`);
    return response.data;
};

export const getAllEnrollmentsByCourseId = async (courseId: string | null):
    Promise<StudentEnrollment[]> => {
    if (courseId == null)
        return [];
    const response = await api.get<StudentEnrollment[]>(`/student-enrollment/course/${courseId}`);
    return response.data;
};