import i18n, { I18nLanguage } from '@/i18n';
import dayjs from 'dayjs';
import { FORMAT, FORMAT_BY_LANGUAGE } from '../constants/datetime.constant';

export const toLocaleDateString = (date?: Date): string => {
  if (!date) return '_';
  const dayjsDate = dayjs(date);
  switch (i18n.language as I18nLanguage) {
    case 'en':
      return dayjsDate.format('MMMM DD, YYYY');
    case 'ko':
    default:
      return dayjsDate.format('YYYY년 MM월 DD일');
  }
};

export const convertDateStringToDate = (dateString: string, format: string = FORMAT['YYYY-MM-DD']): Date | null => {
  const parsedDate = dayjs(dateString, format);
  return parsedDate.isValid() ? parsedDate.toDate() : null;
};

const getDateWithSuffix = (date: Date) => {
  const day = dayjs(date).date();
  const month = dayjs(date).format(FORMAT_BY_LANGUAGE['MM'][i18n.language as I18nLanguage]);
  if (i18n.language === 'ko') return `${month} ${day}일`;

  const modTen = day % 10;
  const modHundred = day % 100;
  if (modTen === 1 && modHundred !== 11) return `${day}st`;
  if (modTen === 2 && modHundred !== 12) return `${day}nd`;
  if (modTen === 3 && modHundred !== 13) return `${day}rd`;
  return `${month} ${day}th`;
};

export type FORMAT_TYPE = 'MMMM DD' | 'HH:mm' | 'YYYY.MM.DD' | 'D ddd' | 'ddd' | 'D[th]' | 'MMMM' | 'h A' | 'MM' | 'YYYY MM DD';
export const fDate = (date?: Date, format?: FORMAT_TYPE): string => {
  if (!date) return '_';
  if (format !== 'D[th]') {
    return dayjs(date).format(FORMAT_BY_LANGUAGE[format || 'MMMM DD'][i18n.language as I18nLanguage]);
  }
  return getDateWithSuffix(date);
};
