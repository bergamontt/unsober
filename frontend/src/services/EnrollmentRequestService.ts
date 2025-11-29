import type { EnrollmentRequest, EnrollmentRequestDto, RequestStatus } from "../models/Request";
import api from "./api";

export const getAllEnrollmentRequests = async (): Promise<EnrollmentRequest[]> => {
    const response = await api.get<EnrollmentRequest[]>('/enrollment-request');
    return response.data;
};

export const getEnrollmentRequestsByStatus = async (status: RequestStatus):
    Promise<EnrollmentRequest[]> => {
    const response = await api.get<EnrollmentRequest[]>(`/enrollment-request/status/${status}`);
    return response.data;
};

export const getEnrollmentRequestById = async (id: string | null):
    Promise<EnrollmentRequest | null> => {
    if (!id)
        return null;
    const response = await api.get<EnrollmentRequest>(`/enrollment-request/${id}`);
    return response.data;
};

export const addEnrollmentRequest = async (dto: EnrollmentRequestDto):
    Promise<EnrollmentRequest> => {
    const response = await api.post<EnrollmentRequest>('/enrollment-request', dto);
    return response.data;
};

export const updateEnrollmentRequest = async (id: string, dto: EnrollmentRequestDto):
    Promise<EnrollmentRequest> => {
    const response = await api.patch<EnrollmentRequest>(`/enrollment-request/${id}`, dto);
    return response.data;
};

export const updateEnrollmentRequestStatus = async (id: string, status: RequestStatus):
    Promise<EnrollmentRequest> => {
    const response = await api.patch<EnrollmentRequest>(`/enrollment-request/update-status/${id}`,
        null, { params: { status } });
    return response.data;
};