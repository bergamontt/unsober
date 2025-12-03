import type {Translator} from "./utils";
import type {CourseClassDto} from "../models/CourseClass";

export function validateCourseClassDto(
    dto: CourseClassDto,
    t: Translator
): string[] {
    const errors: string[] = [];
    if (!dto.title) {
        errors.push(t("class.title.required"));
    }
    if (!dto.location) {
        errors.push(t("class.location.required"));
    }
    if(!dto.weeksList || dto.weeksList.length == 0) {
        errors.push(t("class.weeksList.required"));
    }
    return errors;
}
