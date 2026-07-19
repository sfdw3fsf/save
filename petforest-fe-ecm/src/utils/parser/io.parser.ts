import { isNil } from 'lodash';

import { Nil } from '../utils/types/utils.type';
import { parseDateTime } from './date-time.parser';

export const orUndefined = (value?: any) => value || undefined;
export const orNull = (value?: any) => value || null;
export const orString = (value?: any) => value || '';
export const safeString = (value?: string | number | null, defaultValue = '') => (!isNil(value) ? `${value}` : defaultValue);
export const stringOrUndefined = (value: string | number | null | undefined | boolean) =>
  !isNil(value) ? `${value}` : undefined;
export const stringOrNull = (value?: string | number | null) => (value ? `${value}` : null);
export const numberOrNull = (value?: Nil<string | number>) =>
  !isNil(value) ? (typeof value === 'string' ? parseInt(value) : value) : null;
export const numberOrUndefined = (value?: Nil<string | number>) =>
  !isNil(value) ? (typeof value === 'string' ? parseInt(value) : value) : undefined;
export const numberOrZero = (value?: Nil<string | number>) => (!value ? 0 : typeof value === 'string' ? parseInt(value) : value);
export const parseImage = (url: Nil<string>) => url || 'https://www.sikkimexpress.com/assets/frontend/images/no-image.png';
export const formatMoney = (money: number) => new Intl.NumberFormat('en-EN').format(money);
export const isLocaleStringOrZero = (value: any) => {
  return typeof value === 'number' && !isNaN(value) ? value.toLocaleString() : 0;
};
export const orDateTime = (value: string) => {
  const date = new Date(value);

  return !isNaN(date.getTime()) ? parseDateTime(value) : value;
};

export const parseStringToNumber = <T = null>(input?: string | null, fallback: T = null as T): number | T => {
  if (!input) return fallback;
  const parsed = Number(input);
  return isNaN(parsed) ? fallback : parsed;
};
