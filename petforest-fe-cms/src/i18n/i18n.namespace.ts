import { I18NResource, i18nResources } from '@/i18n/i18n.resource';

export const i18nNameSpace = Object.keys(i18nResources) as (keyof I18NResource)[];
