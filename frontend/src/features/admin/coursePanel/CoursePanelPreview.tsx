import {Accordion, Group, Pill, Text} from "@mantine/core";
import type {Course} from "../../../models/Course.ts";
import CoursePanelInfo from "./CoursePanelInfo.tsx";

interface CoursePreviewProps {
    course: Course
}

function getYears(course: Course) {
    return `${course.courseYear}/${course.courseYear + 1}`;
}

function CoursePanelPreview({course}: CoursePreviewProps) {
    return (
        <Accordion variant="separated">
            <Accordion.Item key={course.id} value={course.id}>
                <Accordion.Control>
                    <Group
                        justify="space-between"
                        className="course"
                        pr="1em"
                    >
                        <Text>{`${course.subject.name}`}</Text>
                        <Pill radius="xs" bg="gray" c="white" fw="bold">
                            {getYears(course)}
                        </Pill>
                    </Group>
                </Accordion.Control>
                <Accordion.Panel>
                    <CoursePanelInfo course={course}/>
                </Accordion.Panel>
            </Accordion.Item>
        </Accordion>
    );
}

export default CoursePanelPreview
