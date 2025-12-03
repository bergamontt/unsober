import type { Building } from "./Building";
import type { CourseGroup } from "./CourseGroup";
import type { Teacher } from "./Teacher";

export const ClassType = {
    PRACTICE: 'PRACTICE',
    SEMINAR: 'SEMINAR',
    LECTURE: 'LECTURE',
    CONSULTATION: 'CONSULTATION',
    CREDIT: 'CREDIT',
    EXAM: 'EXAM'
} as const;

export type ClassType = typeof ClassType[keyof typeof ClassType];

export const WeekDay = {
    MONDAY: 'MONDAY',
    TUESDAY: 'TUESDAY',
    WEDNESDAY: 'WEDNESDAY',
    THURSDAY: 'THURSDAY',
    FRIDAY: 'FRIDAY',
    SATURDAY: 'SATURDAY',
    SUNDAY: 'SUNDAY'
} as const;

export type WeekDay = typeof WeekDay[keyof typeof WeekDay];

export interface CourseClass {
    id: string;
    group: CourseGroup;
    title: string;
    type: ClassType;
    weeksList: number[];
    weekDay: WeekDay;
    classNumber: number;
    location?: string;
    building?: Building;
    teacher?: Teacher;
}

export interface CourseClassDto {
    groupId?: string;
    title?: string;
    type?: ClassType;
    weeksList?: number[];
    weekDay?: WeekDay;
    classNumber?: number;
    location?: string;
    buildingId?: string;
    teacherId?: string;
}
