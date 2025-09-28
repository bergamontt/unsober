import type { Subject } from "../../services/models/Subject";
import { Stack, Button } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router";
import SubjectDetails from "./SubjectDetails";

interface SubjectPanelProps {
    subject: Subject;
}

function SubjectPanel({subject} : SubjectPanelProps) {
    const navigate = useNavigate();
    const {t} = useTranslation("subjectPreview"); 
    return(
        <Stack gap={0}>
            <SubjectDetails subject={subject}/>
            <Button
                variant="outline"
                color="indigo"
                mt="md" fullWidth
                onClick={() => navigate(`/subject/${subject.id}`)}
            >
                {t('details')}
            </Button>
        </Stack>
    );
}

export default SubjectPanel