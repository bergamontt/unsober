import type { StudentDto } from "../models/Student";
import { isBlank, isPositiveInt, isValidEmail, type Translator } from "./utils";

export function validateStudentDto(
    dto: StudentDto,
    t: Translator,
    ignorePassword: boolean = false
): string[] {
    const errors: string[] = [];
    if (isBlank(dto.firstName)) {
        errors.push(t("student.firstName.required"));
    } else if ((dto.firstName ?? "").length > 100) {
        errors.push(t("student.firstName.size", { max: 100 }));
    }
    if (isBlank(dto.lastName)) {
        errors.push(t("student.lastName.required"));
    } else if ((dto.lastName ?? "").length > 100) {
        errors.push(t("student.lastName.size", { max: 100 }));
    }
    if (isBlank(dto.patronymic)) {
        errors.push(t("student.patronymic.required"));
    } else if ((dto.patronymic ?? "").length > 100) {
        errors.push(t("student.patronymic.size", { max: 100 }));
    }
    if (isBlank(dto.recordBookNumber)) {
        errors.push(t("student.recordBookNumber.required"));
    } else if ((dto.recordBookNumber ?? "").length > 50) {
        errors.push(t("student.recordBookNumber.size", { max: 50 }));
    }
    if (isBlank(dto.email)) {
        errors.push(t("student.email.required"));
    } else {
        const emailVal = dto.email ?? "";
        if (emailVal.length > 200) {
            errors.push(t("student.email.size", { max: 200 }));
        } else if (!isValidEmail(emailVal)) {
            errors.push(t("student.email.invalid"));
        }
    }
    if (!ignorePassword && isBlank(dto.password)) {
        errors.push(t("student.password.required"));
    }
    if (dto.specialityId === undefined || dto.specialityId === null) {
        errors.push(t("student.specialtyId.required"));
    }
    if (dto.studyYear === undefined || dto.studyYear === null) {
        errors.push(t("student.studyYear.required"));
    } else if (!isPositiveInt(dto.studyYear)) {
        errors.push(t("student.studyYear.positive"));
    }
    if (dto.status === undefined || dto.status === null) {
        errors.push(t("student.status.required"));
    }
    return errors;
}
