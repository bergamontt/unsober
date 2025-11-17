import api from './api.ts';
import type { Student, StudentDto } from '../models/Student.ts'

export const getStudentById = async (id: string | null): Promise<Student | null> => {
    if(!id)
        return null;
    const response = await api.get<Student>(`/student/uuid/${id}`);
    return response.data;
};

export const getStudentByEmail = async (email: string): Promise<Student> => {
    const response = await api.get<Student>(`/student/email/${email}`);
    return response.data;
};

export const getAllStudents = async (): Promise<Student[]> => {
    const response = await api.get<Student[]>('/student');
    return response.data;
};

export const addStudent = async (dto: StudentDto): Promise<Student> => {
    const response = await api.post<Student>('/student', dto);
    return response.data;
}

export const updateStudent = async (id: string, dto: StudentDto): Promise<Student> => {
    const response = await api.patch<Student>(`/student/${id}`, dto);
    return response.data;
}

export const deleteStudent = async (id: string): Promise<void> => {
    await api.delete<Student>(`/student/${id}`);
}