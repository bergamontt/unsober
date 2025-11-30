import { Button, Title } from "@mantine/core";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import CourseDetails from "./CourseDetails.tsx";
import EnrolledStudents from "./EnrolledStudents.tsx";
import { useParams } from "react-router";
import useFetch from "../../hooks/useFetch.ts";
import { useCallback, useState } from "react";
import { getCourseById } from "../../services/CourseService.ts";
import { useTranslation } from "react-i18next";
import { getAppState } from "../../services/AppStateService.ts";
import { EnrollmentStage } from "../../models/AppState.ts";
import { enrollSelf, existsEnrollment } from "../../services/StudentEnrollmentService.ts";
import { notifications } from "@mantine/notifications";
import { useStudentStore } from "../../hooks/studentStore.ts";
import axios from "axios";

function CoursePage() {
    const { id } = useParams();
    const { t } = useTranslation("studentEnrollment");
    const { data: course } = useFetch(getCourseById, [id ?? null]);
    const { data: state } = useFetch(getAppState, []);
    const stage = state?.enrollmentStage;
    const enrollmentAllowed = stage && (stage == EnrollmentStage.CORRECTION
        || stage == EnrollmentStage.COURSES);
    const { user: student } = useStudentStore();
    const isStudent = !!student;
    const { data: isEnrolled } = useFetch(existsEnrollment, [student?.id ?? null, id ?? null]);
    const [loading, setIsLoading] = useState(false);

    const handleEnroll = useCallback(async () => {
        if (!id || !isStudent || isEnrolled)
            return;
        try {
            setIsLoading(true);
            const enrollment = await enrollSelf(id);
            notifications.show({
                title: t("success"),
                message: t("enrolledInCourse", { courseName: enrollment.course.subject.name }),
                color: 'green',
            });
            window.location.reload();
        } catch (err: unknown) {
            let errorMessage = t("unknownEnrollmentError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data == "string" && data.trim()) {
                    errorMessage = data;
                }
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: 'red',
            });
        } finally {
            setIsLoading(false);
        }
    }, [t]);

    if (!course)
        return <></>;
    return (
        <PageWrapper>
            <Title>{course.subject.name}</Title>
            <CourseDetails course={course} />
            {
                enrollmentAllowed && isStudent &&
                <Button
                    variant="outline"
                    color="green"
                    disabled={isEnrolled ?? false}
                    onClick={handleEnroll}
                    loading={loading}
                >
                    {isEnrolled ? t("enrolled") : t("enroll")}
                </Button>
            }
            <EnrolledStudents courseId={course.id} />
        </PageWrapper>
    );
}

export default CoursePage