import api from './api.ts';
import type { Student } from '../models/Student.ts'

export const getStudentByEmail = async (email: string): Promise<Student> => {
    const response = await api.get<Student>(`/student/email/${email}`);
    return response.data;
};

export const getAllStudents = async (): Promise<Student[]> => {
    const response = await api.get<Student[]>('/student');
    return response.data;
};