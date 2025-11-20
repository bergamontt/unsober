import { Stack, Button } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router";
import type { Course } from "../../models/Course.ts";
import CourseDetails from "./CourseDetails.tsx";

interface CoursePanelProps {
    course: Course
}

function CoursePreviewPanel({course} : CoursePanelProps) {
    const navigate = useNavigate();
    const {t} = useTranslation("coursePreview"); 
    return(
        <Stack gap={0}>
            <CourseDetails course={course}/>
            <Button
                variant="outline"
                color="indigo"
                mt="md" fullWidth
                onClick={() => navigate(`/course/${course.id}`)}
            >
                {t('details')}
            </Button>
        </Stack>
    );
}

export default CoursePreviewPanel