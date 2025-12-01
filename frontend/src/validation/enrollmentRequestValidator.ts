import type { EnrollmentRequestDto } from "../models/Request";
import { isBlank, type Translator } from "./utils";

export function validateEnrollmentRequestDto(
    dto: EnrollmentRequestDto,
    t: Translator
): string[] {
    const errors: string[] = [];
    if (dto.studentId === undefined || dto.studentId === null) {
        errors.push(t("enrollment.studentId.required"));
    }
    if (dto.courseId === undefined || dto.courseId === null) {
        errors.push(t("enrollment.courseId.required"));
    }
    if (isBlank(dto.reason)) {
        errors.push(t("enrollment.reason.required"));
    } else if ((dto.reason ?? "").length > 3000) {
        errors.push(t("enrollment.reason.size", { max: 3000 }));
    }

    return errors;
}
