import type { AuthRequest, AuthResponse } from '../models/Auth.ts';
import api from '../common/api/api.ts';

export const login = async (params: AuthRequest): Promise<AuthResponse> => {
    const response = await api.post<AuthResponse>('/auth/sign-in', params);
    return response.data;
};