import type { WithdrawalRequestDto } from "../models/Request";
import { isBlank, type Translator } from "./utils";

export function validateWithdrawalRequestDto(
    dto: WithdrawalRequestDto,
    t: Translator
): string[] {
    const errors: string[] = [];
    if (dto.studentEnrollmentId === undefined || dto.studentEnrollmentId === null) {
        errors.push(t("withdrawal.studentEnrollmentId.required"));
    }
    if (isBlank(dto.reason)) {
        errors.push(t("withdrawal.reason.required"));
    } else if ((dto.reason ?? "").length > 3000) {
        errors.push(t("withdrawal.reason.size", { max: 3000 }));
    }

    return errors;
}
