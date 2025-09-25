import { Button, Title } from "@mantine/core";
import PageWrapper from "../components/common/PageWrapper";
import SubjectDetails from "../components/subject/SubjectDetails";
import EnrolledStudents from "../components/subject/EnrolledStudents";

function SubjectPage() {
    return(
        <PageWrapper>
            <Title>Основи комп'ютерних алгоритмів</Title>
            <SubjectDetails/>
            <Button variant="outline" color="green">
                Записатися
            </Button>
            <EnrolledStudents/>
        </PageWrapper>
    );
}

export default SubjectPage