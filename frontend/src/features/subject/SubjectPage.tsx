import { Button, Title } from "@mantine/core";
import PageWrapper from "../../common/components/PageWrapper.tsx";
import SubjectDetails from "./SubjectDetails.tsx";
import EnrolledStudents from "./EnrolledStudents.tsx";
import { useParams } from "react-router";
import { getSubject } from "./SubjectService.ts";
import useFetch from "../../common/hooks/useFetch.ts";

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