import { Accordion } from "@mantine/core";
import type { Course } from "../../models/Course.ts";
import CoursePreviewPanel from "./CoursePreviewPanel.tsx";
import CourseItem from "./CourseItem.tsx";
import { useStudentStore } from "../../hooks/studentStore.ts";
import useFetch from "../../hooks/useFetch.ts";
import { getRecommendationBySubjectAndSpeciality } from "../../services/SubjectRecommendationService.ts";

interface CoursePreviewProps {
    course: Course
}

function CoursePreview({ course }: CoursePreviewProps) {
    const { user: student } = useStudentStore();
    const speciality = student?.speciality;
    const { data: recommendation } = useFetch(
        getRecommendationBySubjectAndSpeciality, 
        [course.subject.id, speciality?.id ?? null]);
    return (
        <Accordion >
            <Accordion.Item key={course.id} value={course.id}>
                <Accordion.Control>
                    <CourseItem
                        name={course.subject.name}
                        recommendation={recommendation?.recommendation}
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