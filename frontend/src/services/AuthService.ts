import type { AuthRequest, AuthResponse } from '../models/Auth';
import api from './api';

export const login = async (params: AuthRequest): Promise<AuthResponse> => {
    const response = await api.post<AuthResponse>('/auth/sign-in', params);
    return response.data;
};