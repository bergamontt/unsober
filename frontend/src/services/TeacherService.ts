import type { Teacher, TeacherDto } from '../models/Teacher.ts';
import api from './api.ts';

export const getTeacherById = async (id: string | null): Promise<Teacher | null> => {
    if(!id)
        return null;
    const response = await api.get<Teacher>(`/teacher/${id}`);
    return response.data;
};

export const getAllTeachers = async (): Promise<Teacher[]> => {
    const response = await api.get<Teacher[]>('/teacher');
    return response.data;
};

export const addTeacher = async (dto: TeacherDto): Promise<Teacher> => {
    const response = await api.post<Teacher>('/teacher', dto);
    return response.data;
}

export const updateTeacher = async (id: string, dto: TeacherDto): Promise<Teacher> => {
    const response = await api.patch<Teacher>(`/teacher/${id}`, dto);
    return response.data;
}

export const deleteTeacher = async (id: string): Promise<void> => {
    await api.delete<Teacher>(`/teacher/${id}`);
}