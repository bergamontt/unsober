import { Button, Title } from "@mantine/core";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import CourseDetails from "./CourseDetails.tsx";
import EnrolledStudents from "./EnrolledStudents.tsx";
import { useNavigate, useParams } from "react-router";
import useFetch from "../../hooks/useFetch.ts";
import { useEffect } from "react";
import { useAuthStore } from "../../hooks/authStore";
import { getCourseById } from "../../services/CourseService.ts";
import { useTranslation } from "react-i18next";

function CoursePage() {
    const { id } = useParams();
    const { isAuthenticated, loadingAuth } = useAuthStore();
    const { t } = useTranslation("studentEnrollment");
    const navigate = useNavigate();
    useEffect(() => {
        if (!isAuthenticated && !loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, loadingAuth, navigate]);
    const { data: course } = useFetch(getCourseById, [id ?? null]);
    if (!course)
        return <></>;
    return (
        <PageWrapper>
            <Title>{course.subject.name}</Title>
            <CourseDetails course={course} />
            <Button variant="outline" color="green">
                {t("enroll")}
            </Button>
            <EnrolledStudents courseId={course.id}/>
        </PageWrapper>
    );
}

export default CoursePage