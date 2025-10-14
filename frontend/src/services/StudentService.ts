import api from './api';
import type { Student } from '../models/Student'

export const getStudentByEmail = async (email: string): Promise<Student> => {
    const response = await api.get<Student>(`/student/email/${email}`);
    return response.data;
};