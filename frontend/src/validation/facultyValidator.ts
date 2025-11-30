import type { FacultyDto } from "../models/Faculty";

type Translator = (key: string, params?: Record<string, any>) => string;

function isBlank(value?: string | null): boolean {
    return value == null || value.trim().length == 0;
}

export function validateFacultyDto(
    dto: FacultyDto,
    t: Translator
): string[] {
    const errors: string[] = [];
    if (isBlank(dto.name)) {
        errors.push(t("faculty.name.required"));
    } else if ((dto.name ?? "").length > 100) {
        errors.push(t("faculty.name.size", { max: 100 }));
    }
    if (dto.description && dto.description.length > 1000) {
        errors.push(t("faculty.description.size", { max: 1000 }));
    }
    return errors;
}
