import api from './api.ts';
import type { TermInfo } from '../models/TermInfo.ts';
import type { Term } from '../models/Subject.ts';

export const getAllTermInfo = async (): Promise<TermInfo[]> => {
    const response = await api.get<TermInfo[]>('/term-info');
    return response.data;
};

export const getTermInfoByYearAndTerm = async (year: number, term: Term):
    Promise<TermInfo> => {
    const response = await api.get<TermInfo>('/term-info/year-and-term', {
        params: { year, term }
    });
    return response.data;
};