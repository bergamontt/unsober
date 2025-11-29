import type { AppState } from '../models/AppState.ts';
import api from './api.ts';

export const getAppState = async (): Promise<AppState> => {
    const response = await api.get<AppState>('app-state');
    return response.data;
};

export const setCurrentYear = async (year: number): Promise<void> => {
  await api.patch('app-state/year', null, { params: { year } });
};

export const setCurrentTerm = async (term: string): Promise<void> => {
  await api.patch('app-state/term', null, { params: { term } });
};

export const setCurrentStage = async (stage: string): Promise<void> => {
  await api.patch('app-state/stage', null, { params: { stage } });
};