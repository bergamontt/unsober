import type { Faculty, FacultyDto, FacultyFilterDto } from "../models/Faculty";
import api from "./api";

export const getAllFaculties = async (
    filters: FacultyFilterDto = {}
): Promise<Faculty[]> => {
    const response = await api.get<Faculty[]>('/faculty', {
        params: filters
    });
    return response.data;
};

export const getFacultyById = async (id: string | null): Promise<Faculty | null> => {
    if (!id)
        return null;
    const response = await api.get<Faculty>(`/faculty/${id}`);
    return response.data;
};

export const addFaculty = async (dto: FacultyDto): Promise<Faculty> => {
    const response = await api.post<Faculty>('/faculty', dto);
    return response.data;
}

export const updateFaculty = async (id: string, dto: FacultyDto): Promise<Faculty> => {
    const response = await api.patch<Faculty>(`/faculty/${id}`, dto);
    return response.data;
}

export const deleteFaculty = async (id: string): Promise<void> => {
    await api.delete<void>(`/faculty/${id}`);
}