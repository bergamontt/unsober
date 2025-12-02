import { saveAs } from "file-saver";
import api from "./api";

export const generateStudyPlan = async (studentId: string): Promise<void> => {
    await api.post(`/study-plan/${studentId}/generate`);
}

export const getStudyPlan = async (
    studentId: string
): Promise<Blob> => {
    const response = await api.get(`/static/studyplans/study-plan-${studentId}.pdf`, {
        responseType: 'blob',
    });
    return response.data;
};

export const downloadStudyPlan = async (
    studentId: string | null
): Promise<void> => {
    if(studentId == null)
        return;
    await generateStudyPlan(studentId);
    const buffer = await getStudyPlan(studentId);
    saveAs(buffer, "StudyPlan.pdf");
}
