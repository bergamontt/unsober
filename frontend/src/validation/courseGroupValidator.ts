import { type Translator } from "./utils";
import type {CourseGroupDto} from "../models/CourseGroup";

export function validateCourseGroupDto(
    dto: CourseGroupDto,
    groupNums: number[],
    t: Translator
): string[] {
    const errors: string[] = [];
    if (!dto.groupNumber) {
        errors.push(t("group.groupNumber.required"));
    }
    if (dto.groupNumber && groupNums.includes(dto.groupNumber)) {
        errors.push(t("group.groupNumber.unique"));
    }
    if (!dto.maxStudents) {
        errors.push(t("group.maxStudents.required"));
    }
    return errors;
}
