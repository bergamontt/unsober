import { Stack, Title, Text } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useUserDetailsStore } from "../../hooks/userDetailsStore";
import { useStudentStore } from "../../hooks/studentStore";

function ProfilePanel() {
    const { t } = useTranslation(["sections", "profile"]);
    const { firstName, lastName, patronymic, email } = useUserDetailsStore();
    const { user: student } = useStudentStore();
    return (
        <Stack pl="6em" gap="4">
            <Title order={3} pb="0.4em">
                {t('sections:profile')}
            </Title>
            <Title order={5}>
                {t('profile:user')}
            </Title>
            <Text size="lg">
                {lastName} {firstName} {patronymic}
            </Text>
            {
                student &&
                <>
                    <Title order={5}>
                        {t('profile:speciality')}
                    </Title>
                    <Text size="lg">
                        {student.speciality.name}, {student.studyYear} рік навчання
                    </Text>
                </>
            }
            <Title order={5}>
                {t('profile:email')}
            </Title>
            <Text size="lg">
                {email}
            </Text>
            {
                student &&
                <>
                    <Title order={5}>
                        {t('profile:recordBookNumber')}
                    </Title>
                    <Text size="lg">
                        {student.recordBookNumber}
                    </Text>
                </>
            }
        </Stack>
    );
}

export default ProfilePanel