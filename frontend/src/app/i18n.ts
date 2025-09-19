import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import enTranslation from "../locales/en/translation.json";
import ukTranslation from "../locales/uk/translation.json";
import zhTranslation from "../locales/zh/translation.json";

i18n
  .use(initReactI18next)
  .init({
    resources: {
      en: { translation: enTranslation },
      uk: { translation: ukTranslation },
      zh: { translation: zhTranslation }
    },
    lng: "zh",
    fallbackLng: "uk",
    interpolation: { escapeValue: false },
  });

export default i18n;
