import type { DepartmentDto } from "../models/Department";
import { isBlank, type Translator } from "./utils";

export function validateDepartmentDto(
    dto: DepartmentDto,
    t: Translator
): string[] {
    const errors: string[] = [];
    if (dto.facultyId === undefined || dto.facultyId === null) {
        errors.push(t("department.facultyId.required"));
    }
    if (isBlank(dto.name)) {
        errors.push(t("department.name.required"));
    } else if ((dto.name ?? "").length > 100) {
        errors.push(t("department.name.size", { max: 100 }));
    }
    if (dto.description && dto.description.length > 1000) {
        errors.push(t("department.description.size", { max: 1000 }));
    }
    return errors;
}
