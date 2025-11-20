import { createViewWeek, createViewDay, createViewMonthGrid, createViewMonthAgenda, type CalendarEventExternal } from '@schedule-x/calendar'
import { useCalendarApp, ScheduleXCalendar } from '@schedule-x/react'
import { createEventsServicePlugin } from '@schedule-x/events-service'
import 'temporal-polyfill/global'
import '@schedule-x/theme-default/dist/index.css'
import useFetch from '../../hooks/useFetch'
import { useStudentStore } from '../../hooks/studentStore'
import { getAllEnrollmentsByStudentId } from '../../services/StudentEnrollmentService'
import type { CourseClass, WeekDay } from '../../models/CourseClass'
import { getAllClassesByCourseId } from '../../services/CourseClassService'
import { useEffect, useState } from 'react'
import { getTermInfoByYearAndTerm } from '../../services/TermInfoService'

const CLASS_SLOTS: Record<number, { start: [number, number]; end: [number, number] }> = {
    1: { start: [8, 30], end: [9, 50] },
    2: { start: [10, 0], end: [11, 20] },
    3: { start: [11, 40], end: [13, 0] },
    4: { start: [13, 30], end: [14, 50] },
    5: { start: [15, 0], end: [16, 20] },
    6: { start: [16, 30], end: [17, 50] },
    7: { start: [18, 0], end: [19, 20] }
};

function getClassStartTime(
    classNumber: number,
    date: Temporal.PlainDate,
    timeZone: string
): Temporal.ZonedDateTime {
    const slot = CLASS_SLOTS[classNumber];
    const [hour, minute] = slot.start;
    return date
        .toPlainDateTime({ hour, minute })
        .toZonedDateTime(timeZone);
}

function getClassEndTime(
    classNumber: number,
    date: Temporal.PlainDate,
    timeZone: string
): Temporal.ZonedDateTime {
    const slot = CLASS_SLOTS[classNumber];
    const [hour, minute] = slot.end;
    return date
        .toPlainDateTime({ hour, minute })
        .toZonedDateTime(timeZone);
}

async function convertClass(courseClass: CourseClass): Promise<CalendarEventExternal[]> {
    const year = courseClass.course.courseYear;
    const term = courseClass.course.subject.term;
    const termInfo = await getTermInfoByYearAndTerm(year, term);
    const termStart = Temporal.PlainDate.from(termInfo.startDate);
    const timeZone = 'Europe/Kyiv';
    const WEEKDAY_TO_ISO: Record<WeekDay, number> = {
        MONDAY: 1,
        TUESDAY: 2,
        WEDNESDAY: 3,
        THURSDAY: 4,
        FRIDAY: 5,
        SATURDAY: 6,
        SUNDAY: 7
    };
    const events = courseClass.weeksList
        .map((weekNum) => {
            const weekStart = termStart.add({ days: (weekNum - 1) * 7 });
            const targetDow = WEEKDAY_TO_ISO[courseClass.weekDay];
            const offsetDays = targetDow - weekStart.dayOfWeek;
            const classDate = weekStart.add({ days: offsetDays });
            const startZdt = getClassStartTime(courseClass.classNumber, classDate, timeZone);
            const endZdt = getClassEndTime(courseClass.classNumber, classDate, timeZone);
            const event: CalendarEventExternal = {
                id: `${courseClass.id}-${weekNum}`,
                title: courseClass.title,
                start: startZdt,
                end: endZdt,
                location: courseClass.location
            };

            return event;
        });

    return events;
}

function Schedule() {
    const { user: student } = useStudentStore();
    const { data: enrollments } = useFetch(getAllEnrollmentsByStudentId, [student?.id ?? null]);
    const [allClasses, setAllClasses] = useState<CourseClass[]>([]);
    const [events, setEvents] = useState<CalendarEventExternal[]>([]);
    const eventsService = useState(() => createEventsServicePlugin())[0];

    const calendar = useCalendarApp({
        views: [
            createViewWeek(),
            createViewDay(),
            createViewMonthGrid(),
            createViewMonthAgenda(),
        ],
        events: events,
        plugins: [eventsService],
    });

    useEffect(() => {
        if (!enrollments) return;
        const load = async () => {
            const results = await Promise.all(
                enrollments.map(async e => {
                    const classes = await getAllClassesByCourseId(e.course.id)
                    return classes.filter(courseClass =>
                        !e.courseGroup || courseClass.group.id == e.courseGroup.id)
                })
            )
            const classes = results.flat();
            setAllClasses(classes);
        };
        load();
    }, [enrollments]);

    useEffect(() => {
        if (!allClasses.length) return;
        const loadEvents = async () => {
            const eventsArrays = await Promise.all(allClasses.map(convertClass));
            const newEvents = eventsArrays.flat();
            setEvents(newEvents);
        };
        loadEvents();
    }, [allClasses]);

    useEffect(() => {
        if (!eventsService)
            return;
        eventsService.set(events);
    }, [events, eventsService]);

    return <ScheduleXCalendar calendarApp={calendar} />;
}

export default Schedule