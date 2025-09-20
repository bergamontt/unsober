import { Group, Pill, Text } from "@mantine/core";
import '../../styles/pages/SubjectPage.css'
import { useTranslation } from "react-i18next";

type SubjectProps = {
    name: string;
    speciality: string;
    isRecomended?: boolean;
}

function Subject({name, speciality, isRecomended=false}: SubjectProps) {
    const {t} = useTranslation("subjectSearch");
    return (
        <Group
            justify="space-between"
            className="subject"
        >
            <Text>{name}</Text>
            <Group grow gap="xs">
                {isRecomended &&
                    <Pill
                        radius="xs" bg="indigo" c="white" fw="bold"
                    >
                        {t('recommended')}
                    </Pill>
                }
                <Pill
                    radius="xs" bg="black" c="white" fw="bold"
                >
                    {speciality}
                </Pill>
            </Group>
        </Group>
    );
}

export default Subject