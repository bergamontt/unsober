export const Term = {
    Fall: 'Fall',
    Spring: 'Spring',
    Summer: 'Summer'
} as const;

export type Term = typeof Term[keyof typeof Term];

export interface Subject {
    id: string;
    name: string;
    annotation: string;
    credits: number;   
    term : Term; 
}