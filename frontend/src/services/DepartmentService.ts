import type { Department } from "../models/Department";
import api from "./api";

export const getAllDepartments = async (): Promise<Department[]> => {
    const response = await api.get<Department[]>('/department');
    return response.data;
};