import type { Page, PageableParams } from '../models/Page';
import type { Subject } from '../models/Subject';
import api from './api';

export const getSubjects = async (
    params: PageableParams = {}
): Promise<Page<Subject>> => {
    const response = await api.get<Page<Subject>>("/subject", {
        params: {
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