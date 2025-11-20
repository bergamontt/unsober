import type { Subject } from "./Subject";

export interface Course {
    id: string;
    subject: Subject;
    maxStudents?: number;
    numEnrolled: number;
    courseYear: number;
}