import type { FacultyDto } from "../models/Faculty";
import { isBlank, type Translator } from "./utils";

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
