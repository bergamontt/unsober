import api from './api.ts';
import type { CourseClass } from '../models/CourseClass.ts';

export const getAllClassesByCourseId = async (courseId: string | null): 
Promise<CourseClass[]> => {
    if(courseId == null)
        return [];
    const response = await api.get<CourseClass[]>(`/course-class/course/${courseId}`);
    return response.data;
};