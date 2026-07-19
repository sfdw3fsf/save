import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import EN_COMMON from './locales/en/common.json';
import EN_FAQ from './locales/en/faq.json';
import EN_FORM from './locales/en/form.json';

import KO_COMMON from './locales/ko/common.json';
import KO_FAQ from './locales/ko/faq.json';
import KO_FORM from './locales/ko/form.json';

export const i18nLanguages = { en: 'EN', ko: 'KR' };

export const i18nResources = {
  en: {
    common: EN_COMMON,
    form: EN_FORM,
    faq: EN_FAQ,
  },
  ko: {
    common: KO_COMMON,
    form: KO_FORM,
    faq: KO_FAQ,
  },
} as const;

export type I18nLanguage = keyof typeof i18nLanguages;
const i18nDefaultLanguage: I18nLanguage = 'ko';

const i18nNamespaces = ['common', 'form', 'faq'] as const;
type I18nNamespace = (typeof i18nNamespaces)[number];
export const i18nDefaultNamespace: I18nNamespace = 'common';

const getInitialLanguage = (): I18nLanguage => {
  const savedLanguage = localStorage.getItem('language') as I18nLanguage;
  if (savedLanguage && Object.keys(i18nLanguages).includes(savedLanguage)) {
    return savedLanguage;
  }
  localStorage.setItem('language', i18nDefaultLanguage); // Set to default if invalid
  return i18nDefaultLanguage;
};

// eslint-disable-next-line import/no-named-as-default-member
i18n.use(initReactI18next).init({
  resources: i18nResources,
  lng: getInitialLanguage(),
  fallbackLng: i18nDefaultLanguage,
  ns: i18nNamespaces,
  defaultNS: i18nDefaultNamespace,
  interpolation: {
    escapeValue: false,
  },
});

// eslint-disable-next-line import/no-named-as-default-member
const originalChangeLanguage = i18n.changeLanguage;
// eslint-disable-next-line import/no-named-as-default-member
i18n.changeLanguage = function (lng, callback) {
  const result = originalChangeLanguage.call(this, lng, callback);
  localStorage.setItem('language', lng ?? i18nDefaultLanguage);
  return result;
};

export default i18n;
