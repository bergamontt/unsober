import type { Department, DepartmentDto } from "../models/Department";
import api from "./api";

export const getAllDepartments = async (): Promise<Department[]> => {
    const response = await api.get<Department[]>('/department');
    return response.data;
};

export const getDepartmentById = async (id: string | null): Promise<Department | null> => {
    if (!id)
        return null;
    const response = await api.get<Department>(`/department/${id}`);
    return response.data;
};

export const addDepartment = async (dto: DepartmentDto): Promise<Department> => {
    const response = await api.post<Department>('/department', dto);
    return response.data;
}

export const updateDepartment = async (id: string, dto: DepartmentDto): Promise<Department> => {
    const response = await api.patch<Department>(`/department/${id}`, dto);
    return response.data;
}

export const deleteDepartment = async (id: string): Promise<void> => {
    await api.delete<void>(`/department/${id}`);
}