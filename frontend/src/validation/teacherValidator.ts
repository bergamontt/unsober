import type { TeacherDto } from "../models/Teacher";
import { isBlank, isValidEmail, type Translator } from "./utils";

export function validateTeacherDto(
    dto: TeacherDto,
    t: Translator
): string[] {
    const errors: string[] = [];
    if (isBlank(dto.firstName)) {
        errors.push(t("teacher.firstName.required"));
    } else if ((dto.firstName ?? "").length > 100) {
        errors.push(t("teacher.firstName.size", { max: 100 }));
    }
    if (isBlank(dto.lastName)) {
        errors.push(t("teacher.lastName.required"));
    } else if ((dto.lastName ?? "").length > 100) {
        errors.push(t("teacher.lastName.size", { max: 100 }));
    }
    if (isBlank(dto.patronymic)) {
        errors.push(t("teacher.patronymic.required"));
    } else if ((dto.patronymic ?? "").length > 100) {
        errors.push(t("teacher.patronymic.size", { max: 100 }));
    }
    if (isBlank(dto.email)) {
        errors.push(t("teacher.email.required"));
    } else {
        const emailVal = dto.email ?? "";
        if (emailVal.length > 200) {
            errors.push(t("teacher.email.size", { max: 200 }));
        } else if (!isValidEmail(emailVal)) {
            errors.push(t("teacher.email.invalid"));
        }
    }
    return errors;
}
