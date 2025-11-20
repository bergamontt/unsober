import type { Term } from "./Subject";

export interface TermInfo {
    id: string;
    year: number;
    term: Term;
    startDate: string;
    endDate: string;
    lenWeeks: number;
}