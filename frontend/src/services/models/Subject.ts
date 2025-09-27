export const Term = {
    AUTUMN: 'AUTUMN',
    SPRING: 'Spring',
    SUMMER: 'Summer'
} as const;

export type Term = typeof Term[keyof typeof Term];

export interface Subject {
    id: string;
    name: string;
    annotation?: string;
    credits: number;   
    term: Term; 
}