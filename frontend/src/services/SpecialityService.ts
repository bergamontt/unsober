import api from './api.ts';
import type { Speciality, SpecialityDto } from '../models/Speciality.ts'

export const getAllSpecialities = async (): Promise<Speciality[]> => {
    const response = await api.get<Speciality[]>('/speciality');
    return response.data;
};

export const getSpecialityById = async (id: string | null): Promise<Speciality | null> => {
    if (!id)
        return null;
    const response = await api.get<Speciality>(`/speciality/${id}`);
    return response.data;
};

export const addSpeciality = async (dto: SpecialityDto): Promise<Speciality> => {
    const response = await api.post<Speciality>('/speciality', dto);
    return response.data;
}

export const updateSpeciality = async (id: string, dto: SpecialityDto): Promise<Speciality> => {
    const response = await api.patch<Speciality>(`/speciality/${id}`, dto);
    return response.data;
}

export const deleteSpeciality = async (id: string): Promise<void> => {
    await api.delete<void>(`/speciality/${id}`);
}