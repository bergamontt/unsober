import api from './api.ts';
import type { SubjectRecommendation } from '../models/SubjectRecommendation.ts';

export const getRecommendationBySubjectAndSpeciality = async (
    subjectId: string | null, specialityId: string | null):
    Promise<SubjectRecommendation | null> => {
        if(subjectId == null || specialityId == null)
            return null;
    const response = await api.get<SubjectRecommendation>(
        '/subject-recommendation/subject-and-speciality', {
        params: { subjectId, specialityId }
    });
    return response.data;
};