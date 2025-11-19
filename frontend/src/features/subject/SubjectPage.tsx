import { Button, Title } from "@mantine/core";
import PageWrapper from "../../common/pageWrapper/PageWrapper.tsx";
import SubjectDetails from "./SubjectDetails.tsx";
import EnrolledStudents from "./EnrolledStudents.tsx";
import { useNavigate, useParams } from "react-router";
import { getSubject } from "../../services/SubjectService.ts";
import useFetch from "../../hooks/useFetch.ts";
import { useEffect } from "react";
import { useAuthStore } from "../../hooks/authStore.ts";

function SubjectPage() {
    const { id } = useParams();
    const { isAuthenticated, loadingAuth } = useAuthStore();
    const navigate = useNavigate();
    useEffect(() => {
        if (!isAuthenticated && !loadingAuth) {
            navigate('/login');
        }
    }, [isAuthenticated, loadingAuth, navigate]);
    const {data : subject} = useFetch(
        getSubject, [id],
    );
    if(!subject)
        return <></>;
    return (
        <PageWrapper>
            <Title>{subject.name}</Title>
            <SubjectDetails subject={subject}/>
            <Button variant="outline" color="green">
                Записатися
            </Button>
            <EnrolledStudents />
        </PageWrapper>
    );
}

export default SubjectPage