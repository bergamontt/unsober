import {Button, NumberInput, Stack} from "@mantine/core";
import {useCallback, useState} from "react";
import {useTranslation} from "react-i18next";
import {notifications} from "@mantine/notifications";
import axios from "axios";
import type {CourseGroup, CourseGroupDto} from "../../../models/CourseGroup.ts";
import {addCourseGroup, updateCourseGroup} from "../../../services/CourseGroupService";
import {modals} from "@mantine/modals";
import {validateCourseGroupDto} from "../../../validation/courseGroupValidator";

type GroupModalProps = {
    courseId: string;
    groupNums: number[];
    group: CourseGroup | null;
}

function getDefaultGroupNum(groupNums: number[]) {
    for (let i = 1; i < groupNums.length; i++) {
        if (groupNums[i - 1] !== groupNums[i] - 1) {
            return i + 1;
        }
    }
    return groupNums.length + 1;
}

function GroupModal({courseId, groupNums, group}: GroupModalProps) {
    const {t} = useTranslation("coursePanel");

    const [groupNumber, setGroupNumber] = useState<number | undefined>(group ? group.groupNumber : getDefaultGroupNum(groupNums));
    const [maxStudents, setMaxStudents] = useState<number | undefined>(group ? group.maxStudents : 10);

    const [isAdding, setIsAdding] = useState(false);

    const handleSubmit = useCallback(async () => {
        const dto: CourseGroupDto = {
            courseId: courseId,
            groupNumber: groupNumber,
            maxStudents: maxStudents
        };

        const errs = validateCourseGroupDto(dto, groupNums, (k, p) => t(k, p));
        if (errs.length > 0) {
            notifications.show({
                title: t("adminMenu:error"),
                message: errs.join('\n'),
                color: "red",
            });
            return;
        }

        setIsAdding(true);
        try {
            if (group) await updateCourseGroup(group.id, dto);
            else await addCourseGroup(dto);
            notifications.show({
                title: t("success"),
                message: group ? t("groupEdited") : t("groupAdded"),
                color: "green",
            });
            modals.closeAll();
        } catch (err: unknown) {
            let errorMessage = t("unknownAddError");
            if (axios.isAxiosError(err)) {
                const data = err.response?.data;
                if (typeof data === "string" && data.trim()) {
                    errorMessage = data;
                }
            }
            notifications.show({
                title: t("error"),
                message: errorMessage,
                color: "red",
            });
        } finally {
            setIsAdding(false);
        }
    }, [courseId, groupNumber, maxStudents, groupNums, t, group]);

    return (
        <Stack p="xs" pt="0">
            <NumberInput
                label={t("maxStudentsLabel")}
                placeholder={t("maxStudentsPlaceholder")}
                min={1} withAsterisk
                value={maxStudents}
                onChange={(e) => {
                    if (typeof e === "number") setMaxStudents(e)
                    else setMaxStudents(undefined)
                }}
            />
            <NumberInput
                label={t("groupNumberLabel")}
                placeholder={t("groupNumberPlaceholder")}
                min={1} withAsterisk
                value={groupNumber}
                onChange={(e) => {
                    if (typeof e === "number") setGroupNumber(e)
                    else setGroupNumber(undefined)
                }}
            />
            <Button
                variant="filled"
                color="green"
                onClick={handleSubmit}
                loading={isAdding}
                disabled={isAdding}
            >
                {group ? t("editGroup") : t("addGroup")}
            </Button>
        </Stack>
    );
}

export default GroupModal;
