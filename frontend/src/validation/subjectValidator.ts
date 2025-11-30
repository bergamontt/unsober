import { EducationLevel, Term, type SubjectDto } from "../models/Subject";
import { isBlank, isIntegerLike, matchesDigits, type Translator } from "./utils";

export function validateSubjectDto(
    dto: SubjectDto,
    t: Translator
): string[] {
    const errors: string[] = [];
    if (isBlank(dto.name)) {
        errors.push(t("subject.name.required"));
    } else if ((dto.name ?? "").length > 255) {
        errors.push(t("subject.name.size", { max: 255 }));
    }
    if (dto.annotation != null && dto.annotation.length > 5000) {
        errors.push(t("subject.annotation.size", { max: 5000 }));
    }
    if (dto.facultyName != null && dto.facultyName.length > 500) {
        errors.push(t("subject.facultyName.size", { max: 500 }));
    }
    if (dto.departmentName != null && dto.departmentName.length > 500) {
        errors.push(t("subject.departmentName.size", { max: 500 }));
    }
    if (dto.educationLevel === undefined || dto.educationLevel === null) {
        errors.push(t("subject.educationLevel.required"));
    } else {
        const allowedEdu = Object.values(EducationLevel) as string[];
        if (!allowedEdu.includes(String(dto.educationLevel))) {
            errors.push(t("subject.educationLevel.required"));
        }
    }
    if (dto.credits === undefined || dto.credits === null) {
        errors.push(t("subject.credits.required"));
    } else if (!matchesDigits(dto.credits, 2, 1)) {
        errors.push(t("subject.credits.digits"));
    }
    if (dto.hoursPerWeek === undefined || dto.hoursPerWeek === null) {
        errors.push(t("subject.hoursPerWeek.required"));
    } else if (!isIntegerLike(dto.hoursPerWeek) || dto.hoursPerWeek < 0) {
        errors.push(t("subject.hoursPerWeek.positive"));
    }
    if (dto.term === undefined || dto.term === null) {
        errors.push(t("subject.term.required"));
    } else {
        const allowedTerms = Object.values(Term) as string[];
        if (!allowedTerms.includes(String(dto.term))) {
            errors.push(t("subject.term.required"));
        }
    }
    return errors;
}
