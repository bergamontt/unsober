import type { Course } from "./Course";

export interface CourseGroup {
    id: string;
    course: Course;
    groupNumber: number;
    maxStudents: number;
    numEnrolled: number;
}

export interface CourseGroupDto {
    courseId?: string;
    groupNumber?: number;
    maxStudents?: number;
}