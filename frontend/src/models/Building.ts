export interface Building {
    id: string;
    name: string;
    address: string;
    latitude: number;
    longitude: number;
    photo: Uint8Array;
}

export interface BuildingDto {
    name?: string;
    address?: string;
    latitude?: number;
    longitude?: number;
}