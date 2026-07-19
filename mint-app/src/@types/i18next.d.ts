import 'i18next';

import { i18nDefaultNamespace, i18nResources } from '@/i18n';

declare module 'i18next' {
  interface CustomTypeOptions {
    defaultNS: typeof i18nDefaultNamespace;
    resources: (typeof i18nResources)['en'];
  }
}
