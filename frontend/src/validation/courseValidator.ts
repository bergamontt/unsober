import type { CourseDto } from "../models/Course";
import { isNumber, type Translator } from "./utils";

export function validateCourseDto(
    dto: CourseDto,
    t: Translator
): string[] {
    const errors: string[] = [];
    if (dto.subjectId === undefined || dto.subjectId === null) {
        errors.push(t("course.subjectId.required"));
    }
    if (dto.maxStudents !== undefined && dto.maxStudents !== null) {
        if (!isNumber(dto.maxStudents) || dto.maxStudents < 0) {
            errors.push(t("course.maxStudents.non-negative"));
        }
    }
    if (dto.courseYear === undefined || dto.courseYear === null) {
        errors.push(t("course.courseYear.required"));
    }

    return errors;
}
