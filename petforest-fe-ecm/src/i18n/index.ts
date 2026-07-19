/* eslint-disable import/no-named-as-default-member */
import i18next from 'i18next';
import { initReactI18next } from 'react-i18next';
import EN_COMMON from './locales/en/common.json';
import KR_COMMON from './locales/kr/common.json';

export type I18nLanguage = keyof typeof i18nLanguages;
export const i18nLanguages = { kr: 'Korean', en: 'English' };
export const i18nDefaultLang: I18nLanguage = 'kr';

export const i18nResources = {
  kr: {
    common: KR_COMMON,
  },
  en: {
    common: EN_COMMON,
  },
};

const i18nNamespaces = ['common'] as const;
export type I18nNamespace = (typeof i18nNamespaces)[number];
export const i18nDefaultNs: I18nNamespace = 'common';

i18next.use(initReactI18next).init({
  resources: i18nResources,
  lng: i18nDefaultLang,
  fallbackLng: i18nDefaultLang,
  ns: i18nNamespaces,
  defaultNS: i18nDefaultNs,
  interpolation: {
    escapeValue: false, // react already safes from xss
  },
});

export default i18next;
