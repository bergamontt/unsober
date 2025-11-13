import {createViewWeek, createViewDay, createViewMonthGrid, createViewMonthAgenda} from '@schedule-x/calendar'
import { useCalendarApp, ScheduleXCalendar} from '@schedule-x/react'
import { createEventsServicePlugin } from '@schedule-x/events-service'
import 'temporal-polyfill/global'
import '@schedule-x/theme-default/dist/index.css'

function zdt(date: string, time: string, timeZone = 'Europe/Kyiv') {
    const plain = Temporal.PlainDateTime.from(`${date}T${time}`)
    return plain.toZonedDateTime(timeZone)
}

const foo = [
    {
        id: '1',
        title: 'Математика',
        start: zdt('2025-10-12', '09:00'),
        end: zdt('2025-10-12', '10:30'),
    },
    {
        id: '2',
        title: 'Фізика',
        start: zdt('2025-10-13', '11:00'),
        end: zdt('2025-10-13', '12:30'),
        calendarId: 'school',
    },
]

function Schedule() {
    const eventsService = createEventsServicePlugin()
    const calendar = useCalendarApp({
        views: [
            createViewWeek(),
            createViewDay(),
            createViewMonthGrid(),
            createViewMonthAgenda(),
        ],
        events: foo,
        plugins: [eventsService],
    });
    return (
        <ScheduleXCalendar calendarApp={calendar} />
    );
}

export default Schedule