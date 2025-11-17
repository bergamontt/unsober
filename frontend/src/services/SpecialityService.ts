import api from './api.ts';
import type { Speciality } from '../models/Speciality.ts'

export const getAllSpecialities = async (): Promise<Speciality[]> => {
    const response = await api.get<Speciality[]>('/speciality');
    return response.data;
};