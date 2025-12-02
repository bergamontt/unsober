import api from "./api";

export const getScheduleTemplate = async (): Promise<Blob> => {
    const response = await api.get('/static/schedule_template.xlsx', {
        responseType: 'blob',
    });
    return response.data;
};
