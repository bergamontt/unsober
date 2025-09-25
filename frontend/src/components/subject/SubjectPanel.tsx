import { Group, Button } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router";
import SubjectDetails from "./SubjectDetails";

function SubjectPanel() {
    const navigate = useNavigate();
    const {t} = useTranslation("subjectPreview"); 
    return(
        <Group gap={0}>
            <SubjectDetails/>
            <Button
                variant="outline"
                color="indigo"
                mt="md" fullWidth
                onClick={() => navigate('/subject')}
            >
                {t('details')}
            </Button>
        </Group>
    );
}

export default SubjectPanel