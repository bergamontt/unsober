import type { AppState } from '../models/AppState.ts';
import api from './api.ts';

export const getAppState = async (): Promise<AppState> => {
    const response = await api.get<AppState>('app-state');
    return response.data;
};