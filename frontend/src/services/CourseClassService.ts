import api from './api.ts';
import type {CourseClass, CourseClassDto} from '../models/CourseClass.ts';

export const getAllClassesByCourseId = async (courseId: string | null):
Promise<CourseClass[]> => {
    if(courseId == null)
        return [];
    const response = await api.get<CourseClass[]>(`/course-class/course/${courseId}`);
    return response.data;
};

export const addCourseClass = async (dto: CourseClassDto): Promise<CourseClass> => {
    const response = await api.post<CourseClass>('/course-class', dto);
    return response.data;
}

export const updateCourseClass = async (id: string, dto: CourseClassDto): Promise<CourseClass> => {
    const response = await api.patch<CourseClass>(`/course-class/${id}`, dto);
    return response.data;
}

export const deleteCourseClass = async (id: string): Promise<void> => {
    await api.delete<void>(`/course-class/${id}`);
}
