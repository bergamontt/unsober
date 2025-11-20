import type { Course } from "../models/Course";
import type { Page, PageableParams } from "../models/Page";
import api from "./api";

export const getCourseById = async (id: string | null): Promise<Course | null> => {
    if (!id)
        return null;
    const response = await api.get<Course>(`/course/${id}`);
    return response.data;
};

export const getAllCourses = async (
    params: PageableParams = {}
): Promise<Page<Course>> => {
    const response = await api.get<Page<Course>>("/course", {
        params: {
            page: params.page ?? 0,
            size: params.size ?? 15,
            sort: params.sort,
        },
    });
    return response.data;
};