// import the original type declarations
import 'i18next';

import { i18nResources } from '@/i18n/i18n.resource';

declare module 'i18next' {
  // Extend CustomTypeOptions
  interface CustomTypeOptions {
    // custom namespace type, if you changed it
    defaultNS: 'common';
    // custom resources type
    resources: typeof i18nResources;
    // other
  }
}
