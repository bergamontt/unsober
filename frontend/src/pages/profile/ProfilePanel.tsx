import { Stack, Title, Text } from "@mantine/core";
import { useTranslation } from "react-i18next";

function ProfilePanel() {
    const {t} = useTranslation();
    const profile = {
        firstName: "Мамбо",
        lastName: "Джамбо",
        patronymic: "Кілселот",
        recordBookNumber: "І 001/22 бп",
        email: "mumbojumbo@ukma.edu.ua",
        speciality: "Інженерія програмного забезпечення",
        studyYear: 4
    };
    return(
        <Stack pl="6em" gap="4">
            <Title order={1} pb="0.4em">
                {t('profile')}
            </Title>
            <Title order={3}>
                {t('user')}
            </Title>
            <Text size="lg">
                {profile.lastName} {profile.firstName} {profile.patronymic}
            </Text>
            <Title order={3}>
                {t('speciality')}
            </Title>
            <Text size="lg">
                {profile.speciality}, {profile.studyYear}-тий рік навчання
            </Text>
            <Title order={3}>
                {t('email')}
            </Title>
            <Text size="lg">
                {profile.email}
            </Text>
            <Title order={3}>
                {t('recordBookNumber')}
                </Title>
            <Text size="lg">
                {profile.recordBookNumber}
            </Text>
        </Stack>
    );
}

export default ProfilePanel