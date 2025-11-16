import api from './api.ts';
import type { Admin } from '../models/Admin.ts'

export const getAdminByEmail = async (email: string): Promise<Admin> => {
    const response = await api.get<Admin>(`/admin/email/${email}`);
    return response.data;
};