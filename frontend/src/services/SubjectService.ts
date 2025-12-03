import type { Page, PageableParams } from '../models/Page.ts';
import type { Subject, SubjectDto, SubjectFilterDto } from '../models/Subject.ts';
import api from './api.ts';

export const getSubjects = async (
    params: PageableParams = {},
    filters: SubjectFilterDto = {}
): Promise<Page<Subject>> => {
    const response = await api.get<Page<Subject>>("/subject", {
        params: {
            ...filters,
            page: params.page ?? 0,
            size: params.size ?? 15,
            sort: params.sort,
        },
    });
    return response.data;
};

export const getSubject = async (
    id: string | undefined | null
): Promise<Subject | null> => {
    if (!id) return null;
    const response = await api.get<Subject>(`/subject/${id}`);
    return response.data;
};

export const addSubject = async (dto: SubjectDto): Promise<Subject> => {
    const response = await api.post<Subject>('/subject', dto);
    return response.data;
}

export const updateSubject = async (id: string, dto: SubjectDto): Promise<Subject> => {
    const response = await api.patch<Subject>(`/subject/${id}`, dto);
    return response.data;
}

export const deleteSubject = async (id: string): Promise<void> => {
    await api.delete<void>(`/subject/${id}`);
}