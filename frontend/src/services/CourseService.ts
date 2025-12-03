import type {Course, CourseDto, CourseFilterDto} from "../models/Course";
import type { Page, PageableParams } from "../models/Page";
import api from "./api";

export const getCourseById = async (id: string | null): Promise<Course | null> => {
    if (!id)
        return null;
    const response = await api.get<Course>(`/course/${id}`);
    return response.data;
};

export const getAllCourses = async (
    params: PageableParams = {},
    filters: CourseFilterDto = {},
): Promise<Page<Course>> => {
    const response = await api.get<Page<Course>>("/course", {
        params: {
            ...filters,
            page: params.page ?? 0,
            size: params.size ?? 15,
            sort: params.sort,
        },
    });
    return response.data;
};

export const getAllCoursesByYear = async (
    params: PageableParams = {},
    year: number | null
): Promise<Page<Course>> => {
    if (year == null) {
        return ({
            content: [],
            page: {
                size: 0,
                number: 0,
                totalElements: 0,
                totalPages: 0
            }
        });
    }

    const response = await api.get<Page<Course>>(`/course/year/${year}`, {
        params: {
            page: params.page ?? 0,
            size: params.size ?? 15,
            sort: params.sort,
        },
    });
    return response.data;
};

export const addCourse = async (dto: CourseDto): Promise<Course> => {
    const response = await api.post<Course>('/course', dto);
    return response.data;
}

export const updateCourse = async (id: string, dto: CourseDto): Promise<Course> => {
    const response = await api.patch<Course>(`/course/${id}`, dto);
    return response.data;
}

export const deleteCourse = async (id: string): Promise<void> => {
    await api.delete<void>(`/course/${id}`);
}
