import type { RequestStatus, WithdrawalRequest, WithdrawalRequestDto } from "../models/Request";
import api from "./api";

export const getAllWithdrawalRequests = async (): Promise<WithdrawalRequest[]> => {
    const response = await api.get<WithdrawalRequest[]>('/withdrawal-request');
    return response.data;
};

export const getWithdrawalRequestsByStatus = async (status: RequestStatus):
    Promise<WithdrawalRequest[]> => {
    const response = await api.get<WithdrawalRequest[]>(`/withdrawal-request/status/${status}`);
    return response.data;
};

export const getWithdrawalRequestById = async (id: string | null):
    Promise<WithdrawalRequest | null> => {
    if (!id)
        return null;
    const response = await api.get<WithdrawalRequest>(`/withdrawal-request/${id}`);
    return response.data;
};

export const addWithdrawalRequest = async (dto: WithdrawalRequestDto):
    Promise<WithdrawalRequest> => {
    const response = await api.post<WithdrawalRequest>('/withdrawal-request', dto);
    return response.data;
};

export const updateWithdrawalRequest = async (id: string, dto: WithdrawalRequestDto):
    Promise<WithdrawalRequest> => {
    const response = await api.patch<WithdrawalRequest>(`/withdrawal-request/${id}`, dto);
    return response.data;
};