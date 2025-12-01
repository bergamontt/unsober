import { Group, Stack, Text, ThemeIcon, Title, Modal, Paper } from "@mantine/core";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import board from "../../assets/board.svg";
import glasses from "../../assets/glasses.svg";
import studentCap from "../../assets/student-cap.svg";
import pin from "../../assets/map-pin.svg";
import clock from "../../assets/clock.svg";
import Icon from "../../common/icon/Icon";
import type { CalendarEventWithCourse } from "./Schedule";

function formatHourRange(
    start: Temporal.ZonedDateTime,
    end: Temporal.ZonedDateTime
): string {
    const formatTime = (zdt: Temporal.ZonedDateTime) => 
        `${zdt.hour}:${zdt.minute.toString().padStart(2, '0')}`;
    return `${formatTime(start)}-${formatTime(end)}`;
}

function CourseClassEvent({ calendarEvent }: { calendarEvent: CalendarEventWithCourse }) {
    const [opened, setOpened] = useState(false);
    const { t } = useTranslation("scheduleDisplay");
    const courseClass = calendarEvent.courseClass;
    const teacher = courseClass.teacher;
    const teacherName = teacher ?
        `${teacher?.lastName} ${teacher?.firstName} ${teacher?.patronymic ?? ""}`
        : t("unknown");
    const location = courseClass.location ?? t("online");
    const groupNumber = courseClass.group?.groupNumber ?? "—";
    const time = formatHourRange(
        calendarEvent.start as Temporal.ZonedDateTime, 
        calendarEvent.end as Temporal.ZonedDateTime);
    const textColor = "#0d47a1";
    const badgeColor = "#1565c0";

    return (
        <>
            <Paper
                h="100%"
                w="100%"
                withBorder
                radius="sm"
                p="xs"
                bg="#bbdefb"
                display="inline-block"
                onClick={() => setOpened(true)}
                style={{ cursor: "pointer" }}
                bd="2px solid #64b5f6"
            >
                <Stack gap="xs">
                    <Title order={5} c={textColor} lineClamp={2}>
                        {courseClass.title}
                    </Title>

                    <Group gap="xs">
                        <ThemeIcon variant="light" color={badgeColor}>
                            <Icon src={clock} />
                        </ThemeIcon>
                        <Text size="sm" c={textColor}>
                            {time}
                        </Text>
                    </Group>

                    <Group gap="xs">
                        <ThemeIcon variant="light" color={badgeColor}>
                            <Icon src={pin} />
                        </ThemeIcon>
                        <Text size="sm" c={textColor}>
                            {location}
                        </Text>
                    </Group>
                </Stack>
            </Paper>

            <Modal
                opened={opened}
                onClose={() => setOpened(false)}
                title={courseClass.title}
                centered
            >
                <Stack gap="sm">
                    <Group gap="xs">
                        <Icon src={board} />
                        <Text size="sm">
                            {t(courseClass.type)}
                        </Text>
                    </Group>

                    <Group gap="xs">
                        <Icon src={clock} />
                        <Text size="sm">
                            {`${t("time")}: ${time}`}
                        </Text>
                    </Group>

                    <Group gap="xs">
                        <Icon src={pin} />
                        <Text size="sm">
                            {`${t("location")}: ${location}`}
                        </Text>
                    </Group>

                    <Group gap="xs">
                        <Icon src={studentCap} />
                        <Text size="sm">
                            {`${groupNumber} ${t("group")}`}
                        </Text>
                    </Group>

                    <Group gap="xs">
                        <Icon src={glasses} />
                        <Text size="sm">
                            {`${t("teacher")}: ${teacherName}`}
                        </Text>
                    </Group>
                </Stack>
            </Modal>
        </>
    );
}

export default CourseClassEvent;
