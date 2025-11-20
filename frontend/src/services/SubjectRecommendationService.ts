import api from './api.ts';
import type { SubjectRecommendation } from '../models/SubjectRecommendation.ts';

export const getRecommendationBySubjectAndSpeciality = async (
    subjectId: string, specialityId: string):
    Promise<SubjectRecommendation> => {
    const response = await api.get<SubjectRecommendation>(
        '/subject-recommendation/subject-and-speciality', {
        params: { subjectId, specialityId }
    });
    return response.data;
};