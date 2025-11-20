import { Group, Pill, Text } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { Recommendation } from "../../models/SubjectRecommendation";

type CourseProps = {
    name: string;
    recommendation?: Recommendation;
}

function CourseItem({ name, recommendation: r }: CourseProps) {
    const { t } = useTranslation("courseSearch");
    const isMandatory = r && r == Recommendation.MANDATORY;
    const isRecommended = r && r == Recommendation.PROF_ORIENTED;
    return (
        <Group
            justify="space-between"
            className="course"
            pr="1em"
        >
            <Text>{name}</Text>
            <Group grow gap="xs">
                {isRecommended &&
                    <Pill radius="xs" bg="indigo" c="white" fw="bold">
                        {t('recommended')}
                    </Pill>
                }
                {isMandatory &&
                    <Pill radius="xs" bg="grey" c="white" fw="bold">
                        {t('mandatory')}
                    </Pill>
                }
            </Group>
        </Group>
    );
}

export default CourseItem