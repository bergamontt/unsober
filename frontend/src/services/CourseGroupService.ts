import api from './api.ts';
import type {CourseGroup, CourseGroupDto} from '../models/CourseGroup.ts';

export const getAllGroupsByCourseId = async (courseId: string | null):
Promise<CourseGroup[]> => {
    if(courseId == null)
        return [];
    const response = await api.get<CourseGroup[]>(`/course-group/course/${courseId}`);
    return response.data;
};

export const addCourseGroup = async (dto: CourseGroupDto): Promise<CourseGroup> => {
    const response = await api.post<CourseGroup>('/course-group', dto);
    return response.data;
}

export const updateCourseGroup = async (id: string, dto: CourseGroupDto): Promise<CourseGroup> => {
    const response = await api.patch<CourseGroup>(`/course-group/${id}`, dto);
    return response.data;
}

export const deleteCourseGroup = async (id: string): Promise<void> => {
    await api.delete<void>(`/course-group/${id}`);
}
