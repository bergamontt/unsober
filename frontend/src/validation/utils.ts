export type Translator = (key: string, params?: Record<string, any>) => string;

export function isBlank(value?: string | null): boolean {
    return value == null || value.trim().length == 0;
}

export function isNumber(value: any): value is number {
    return typeof value === "number" && !isNaN(value);
}

export function isPositiveInt(value?: number | null): boolean {
    return typeof value === "number"
        && Number.isFinite(value)
        && value > 0
        && Math.trunc(value) === value;
}

export function isValidEmail(value: string): boolean {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(value);
}

export function isIntegerLike(value: unknown): boolean {
    return typeof value === "number" && Number.isFinite(value) && Math.trunc(value) === value;
}

export function matchesDigits(value: unknown, maxInt: number, maxFrac: number): boolean {
    if (typeof value !== "number" || !Number.isFinite(value))
        return false;
    const s = Math.abs(value).toString();
    const parts = s.includes("e") ? Number(Math.abs(value)).toFixed(Math.max(maxFrac, 6)) : s;
    const normalized = parts;
    const [intPart, fracPart] = normalized.split(".");
    const intDigits = intPart.replace(/^0+(?=\d)|^$/g, (m) => (m === "" ? "0" : ""));
    const fracDigits = fracPart ?? "";
    if (intDigits.length > maxInt)
        return false;
    if (fracDigits.length > maxFrac)
        return false;
    return true;
}
