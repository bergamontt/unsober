export const Term = {
    AUTUMN: 'AUTUMN',
    SPRING: 'SPRING',
    SUMMER: 'SUMMER'
} as const;

export type Term = typeof Term[keyof typeof Term];

export interface Subject {
    id: string;
    name: string;
    annotation?: string;
    credits: number;   
    term: Term; 
}