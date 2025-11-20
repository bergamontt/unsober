import type { Course } from "./Course";
import type { CourseGroup } from "./CourseGroup";
import type { Student } from "./Student";

export interface StudentEnrollment {
    id: string;
    student: Student;
    course: Course;
    courseGroup?: CourseGroup;
    status: string;
    enrollmentYear: number;
    createdAt: string;
}
