import api from "./api";
import type { Building, BuildingDto } from "../models/Building";

export const getAllBuildings = async (
    filters: BuildingDto = {}
): Promise<Building[]> => {
    const response = await api.get<Building[]>('/building', {
        params: filters
    });
    return response.data;
};

export const getBuildingById = async (id: string | null): Promise<Building | null> => {
    if (!id)
        return null;
    const response = await api.get<Building>(`/building/${id}`);
    return response.data;
};

export const addBuilding = async (dto: BuildingDto): Promise<Building> => {
    const response = await api.post<Building>('/building', dto);
    return response.data;
};

export const updateBuilding = async (id: string, dto: BuildingDto): Promise<Building> => {
    const response = await api.patch<Building>(`/building/${id}`, dto);
    return response.data;
};

export const deleteBuilding = async (id: string): Promise<void> => {
    await api.delete<void>(`/building/${id}`);
};
