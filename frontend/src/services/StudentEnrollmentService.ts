import api from './api.ts';
import type { StudentEnrollment } from '../models/StudentEnrollment.ts';

export const getAllEnrollmentsByStudentId = async (studentId: string | null):
    Promise<StudentEnrollment[]> => {
    if(studentId == null)
        return [];
    const response = await api.get<StudentEnrollment[]>(`/student-enrollment/student/${studentId}`);
    return response.data;
};