import axios from "axios"
import { getValidToken } from "../hooks/authStore";
import i18n from "../i18n/config";

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use((config) => {
    const token = getValidToken();
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    } else {
        delete config.headers.Authorization;
    }

    const lang = i18n.language || 'en';
    config.headers['Accept-Language'] = lang;

    return config;
}, (error) => {
    return Promise.reject(error);
});

export default api