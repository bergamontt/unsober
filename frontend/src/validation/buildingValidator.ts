import type { BuildingDto } from "../models/Building";
import { isBlank, isNumber, type Translator } from "./utils";

export function validateBuildingDto(
    dto: BuildingDto,
    t: Translator
): string[] {
    const errors: string[] = [];
    if (isBlank(dto.name)) {
        errors.push(t("building.name.required"));
    } else if ((dto.name ?? "").length > 100) {
        errors.push(t("building.name.size", { max: 100 }));
    }
    if (isBlank(dto.address)) {
        errors.push(t("building.address.required"));
    } else if ((dto.address ?? "").length > 200) {
        errors.push(t("building.address.size", { max: 200 }));
    }
    if (dto.latitude === undefined || dto.latitude === null) {
        errors.push(t("building.latitude.required"));
    } else if (!isNumber(dto.latitude)) {
        errors.push(t("building.latitude.digits"));
    } else {
        const val = dto.latitude;
        if (val < -90) 
            errors.push(t("building.latitude.min", { min: -90 }));
        if (val > 90) 
            errors.push(t("building.latitude.max", { max: 90 }));
        const [intPart, fracPart] = val.toString().split(".");
        if (intPart.replace("-", "").length > 2 || (fracPart?.length ?? 0) > 8) {
            errors.push(t("building.latitude.digits"));
        }
    }

    if (dto.longitude === undefined || dto.longitude === null) {
        errors.push(t("building.longitude.required"));
    } else if (!isNumber(dto.longitude)) {
        errors.push(t("building.longitude.digits"));
    } else {
        const val = dto.longitude;

        if (val < -180) 
            errors.push(t("building.longitude.min", { min: -180 }));
        if (val > 180) 
            errors.push(t("building.longitude.max", { max: 180 }));

        const [intPart, fracPart] = val.toString().split(".");
        if (intPart.replace("-", "").length > 3 || (fracPart?.length ?? 0) > 8) {
            errors.push(t("building.longitude.digits"));
        }
    }

    return errors;
}
