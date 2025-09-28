import { Button, Title } from "@mantine/core";
import PageWrapper from "../components/common/PageWrapper";
import SubjectDetails from "../components/subject/SubjectDetails";
import EnrolledStudents from "../components/subject/EnrolledStudents";
import { useParams } from "react-router";
import { getSubject } from "../services/SubjectService";
import useFetch from "../hooks/useFetch";

function SubjectPage() {
    const { id } = useParams();
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