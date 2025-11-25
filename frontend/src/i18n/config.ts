import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import Backend from 'i18next-http-backend';

i18n
    .use(initReactI18next)
    .use(Backend)
    .init({
        lng: "uk",
        fallbackLng: "en",
        debug: false,
        ns: ["adminMenu", "auth", "common", "coursePreview",
            "courseSearch", "infoPage", "manageStudents", "profile",
            "sections", "stageDescription", "studentEnrollment",],
        defaultNS: "common",
        backend: {
            loadPath: "/locales/{{lng}}/{{ns}}.json",
        },
    });


export default i18n;
