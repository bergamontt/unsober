import type { Speciality } from "./Speciality";
import type { Subject } from "./Subject";

export const Recommendation = {
    MANDATORY: 'MANDATORY',
    PROF_ORIENTED: 'PROF_ORIENTED'
} as const;

export type Recommendation = typeof Recommendation[keyof typeof Recommendation];

export interface SubjectRecommendation {
    id: string;
    subject: Subject;
    speciality: Speciality;
    recommendation: Recommendation;
}
