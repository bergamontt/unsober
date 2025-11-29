import { useTranslation } from "react-i18next";
import type { StudentEnrollment } from "../../models/StudentEnrollment";
import type { CourseGroup } from "../../models/CourseGroup";
import { NativeSelect } from "@mantine/core";
import { getAllGroupsByCourseId } from "../../services/CourseGroupService";
import useFetch from "../../hooks/useFetch";
import { useCallback, useEffect, useState } from "react";
import { changeGroup, withdrawFromGroup } from "../../services/StudentEnrollmentService";
import { notifications } from "@mantine/notifications";
import axios from "axios";

interface GroupSelectorProps {
    enrollment: StudentEnrollment;
}

function GroupSelector({ enrollment }: GroupSelectorProps) {
    const { t } = useTranslation("groups");
    const { data } = useFetch(getAllGroupsByCourseId, [enrollment.course.id]);
    const groups = data ?? [];
    const [groupId, setGroupId] = useState<string | undefined>(enrollment.group?.id);
    useEffect(() => {
        if (!enrollment)
            return;
        setGroupId(enrollment.group?.id);
    }, [enrollment]);

    const groupToStr = (g: CourseGroup): string => {
        return `${g.groupNumber} ${t("group")} (${g.numEnrolled}/${g.maxStudents})`;
    }

    const handleChangeGroup = useCallback(async (enrollmentId: string, groupId: string | undefined) => {
        try {
            if (!groupId) {
                await withdrawFromGroup(enrollmentId);
            } else {
                await changeGroup(enrollmentId, groupId);
            }
            notifications.show({
                title: t("success"),
                message: t("changedGroupForCourse", { courseName: enrollment.course.subject.name }),
                color: 'green',
            });
            setGroupId(groupId);
        } catch (err: unknown) {
            let errorMessage = t("unknownGroupError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data == "string" && data.trim()) {
                    errorMessage = data;
                }
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: 'red',
            });
        }
    }, [t]);

    return (
        <NativeSelect
            data={[
                { value: '', label: t("notSelected") },
                ...groups.map(group => ({
                    label: groupToStr(group),
                    value: group.id
                }))
            ]}
            value={groupId ?? undefined}
            onChange={e => handleChangeGroup(enrollment.id, e.currentTarget.value)}
        />
    );
}

export default GroupSelector;