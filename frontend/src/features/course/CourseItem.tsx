import { Group, Pill, Text } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { Recommendation } from "../../models/SubjectRecommendation";
import type { Course } from "../../models/Course";
import { getRecommendationBySubjectAndSpeciality, recommendationExistsBySubjectAndSpeciality } from "../../services/SubjectRecommendationService";
import { useStudentStore } from "../../hooks/studentStore";
import useFetch from "../../hooks/useFetch";
import { useEffect, useState } from "react";

type CourseProps = {
    course: Course
}

function CourseItem({ course }: CourseProps) {
    const { t } = useTranslation("courseSearch");
    const { user: student } = useStudentStore();
    const subjId = course?.subject.id;
    const specId = student?.speciality.id ?? null;
    const { data: recommExists } = useFetch(
        recommendationExistsBySubjectAndSpeciality, [subjId, specId]);
    const [isMandatory, setIsMandatory] = useState(false);
    const [isRecommended, setIsRecommended] = useState(false);
    useEffect(() => {
        const updateRecommendation = async () => {
            const recomm = await getRecommendationBySubjectAndSpeciality(subjId, specId);
            setIsMandatory(recomm?.recommendation == Recommendation.MANDATORY);
            setIsRecommended(recomm?.recommendation == Recommendation.PROF_ORIENTED);
        }
        if(recommExists)
            updateRecommendation();
    }, [recommExists])

    return (
        <Group
            justify="space-between"
            className="course"
            pr="1em"
        >
            <Text>{course.subject.name}</Text>
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
                <Pill radius="xs" bg="black" c="white" fw="bold">ФІ</Pill>
            </Group>
        </Group>
    );
}

export default CourseItem