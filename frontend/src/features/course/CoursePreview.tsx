import { Accordion } from "@mantine/core";
import type { Course } from "../../models/Course.ts";
import CoursePreviewPanel from "./CoursePreviewPanel.tsx";
import CourseItem from "./CourseItem.tsx";

interface CoursePreviewProps {
    course: Course
}

function CoursePreview({ course }: CoursePreviewProps) {
    return (
        <Accordion >
            <Accordion.Item key={course.id} value={course.id}>
                <Accordion.Control>
                    <CourseItem
                        course={course}
                    />
                </Accordion.Control>
                <Accordion.Panel>
                    <CoursePreviewPanel course={course} />
                </Accordion.Panel>
            </Accordion.Item>
        </Accordion>
    );
}

export default CoursePreview