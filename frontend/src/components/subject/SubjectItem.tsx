import { Group, Pill, Text } from "@mantine/core";
import { useTranslation } from "react-i18next";

type SubjectProps = {
    name: string;
    speciality: string;
    isRecommended?: boolean;
}

function SubjectItem({name, speciality, isRecommended=false}: SubjectProps) {
    const {t} = useTranslation("subjectSearch");
    return (
        <Group
            justify="space-between"
            className="subject"
            pr="1em"
        >
            <Text>{name}</Text>
            <Group grow gap="xs">
                {isRecommended &&
                    <Pill radius="xs" bg="indigo" c="white" fw="bold">
                        {t('recommended')}
                    </Pill>
                }
                <Pill radius="xs" bg="black" c="white" fw="bold">
                    {speciality}
                </Pill>
            </Group>
        </Group>
    );
}

export default SubjectItem