import type { SpecialityDto } from "../models/Speciality";

type Translator = (key: string, params?: Record<string, any>) => string;

function isBlank(value?: string | null): boolean {
    return value == null || value.trim().length === 0;
}

export function validateSpecialityDto(dto: SpecialityDto, t: Translator): string[] {
    const errors: string[] = [];
    if (isBlank(dto.name)) {
        errors.push(t("specialty.name.required"));
    } else if ((dto.name ?? "").length > 100) {
        errors.push(t("specialty.name.size", { max: 100 }));
    }
    if (dto.description && dto.description.length > 1000) {
        errors.push(t("specialty.description.size", { max: 1000 }));
    }
    return errors;
}
