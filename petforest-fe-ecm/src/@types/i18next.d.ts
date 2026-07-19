import 'i18next';
import { i18nDefaultNs, i18nResources } from '@/i18n';

declare module 'i18next' {
  interface CustomTypeOptions {
    defaultNS: typeof i18nDefaultNs;
    resources: (typeof i18nResources)['kr']; // kr should be change to default lang when adjust
  }
}
