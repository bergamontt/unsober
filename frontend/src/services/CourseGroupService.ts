import api from './api.ts';
import type { CourseGroup } from '../models/CourseGroup.ts';

export const getAllGroupsByCourseId = async (courseId: string | null): 
Promise<CourseGroup[]> => {
    if(courseId == null)
        return [];
    const response = await api.get<CourseGroup[]>(`/course-group/course/${courseId}`);
    return response.data;
};