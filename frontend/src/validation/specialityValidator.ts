import type { SpecialityDto } from "../models/Speciality";
import { isBlank, type Translator } from "./utils";

export function validateSpecialityDto(dto: SpecialityDto, t: Translator): string[] {
    const errors: string[] = [];
    if (isBlank(dto.name)) {
        errors.push(t("speciality.name.required"));
    } else if ((dto.name ?? "").length > 100) {
        errors.push(t("speciality.name.size", { max: 100 }));
    }
    if (dto.description && dto.description.length > 1000) {
        errors.push(t("speciality.description.size", { max: 1000 }));
    }
    return errors;
}
