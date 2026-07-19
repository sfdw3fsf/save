import dayjs, { Dayjs } from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import duration from 'dayjs/plugin/duration';
import relativeTime from 'dayjs/plugin/relativeTime';
import { isArray, isEmpty, isNull } from 'lodash';
import { DATE_FORMAT } from './../constants/date.constant';

import { Nil } from '../types/utils.type';

dayjs.extend(customParseFormat);
dayjs.extend(relativeTime);
dayjs.extend(duration);

export const parseDate = (
  date?: Nil<string> | Nil<Dayjs>,
  format = DATE_FORMAT.DATE.HYPHEN,
  defaultValue: string | null | undefined = null,
) => (!date ? defaultValue : dayjs(date).format(format));

export const parseDateDot = (
  date?: Nil<string>,
  format = DATE_FORMAT.DATE.DOT,
  defaultValue: string | null | undefined = null,
) => (!date ? defaultValue : dayjs(date).format(format));

export const parseDateTimeSecond = (date: string): string | null =>
  dayjs(date).isValid() ? dayjs(date).format(DATE_FORMAT.DATE_TIME_SECOND.HYPHEN) : null;

export const parseDateTimeMinute = (date: string): string | null =>
  dayjs(date).isValid() ? dayjs(date).format(DATE_FORMAT.DATE_TIME.HYPHEN) : null;

export const parseDateTime = (date: string) => dayjs(date).format(DATE_FORMAT.DATE_TIME.CUSTOM);
export const parseDateTimeAmPm = (date: string) => dayjs(date).format(DATE_FORMAT.DATE_TIME.AM_PM);

export const parseQueryDate = (date: Nil<Dayjs>, defaultValue: any = undefined) =>
  date ? dayjs(date).toISOString() : defaultValue;

export const parseQueryTime = (time: Nil<Dayjs>, defaultValue: any = undefined) =>
  time ? dayjs(time, DATE_FORMAT.TIME.PICKER)?.format(DATE_FORMAT.TIME.PICKER) : defaultValue;

export const parseQueryDateStr = (date: Nil<string>, defaultValue: any = null) =>
  date ? dayjs(date).format(DATE_FORMAT.DATE.HYPHEN) : defaultValue;

export const dayjsOrNull = (value: Nil<string>) => (value ? dayjs(value) : null);

export const parseTime = (hour: Nil<number>, minute: Nil<number>, format = DATE_FORMAT.TIME._12H) =>
  !isNull(hour) && !isNull(minute) ? dayjs(`${hour}:${minute}`, DATE_FORMAT.TIME._24H).format(format) : null;

export const parseTimePicker = (value?: string, format: string = DATE_FORMAT.TIME.PICKER) => {
  return value && dayjs(value, format).isValid() ? dayjs(value, format) : null;
};

export const parseDatePicker = (value?: string | null) => {
  if (!value) return null;
  if (dayjs(value).isValid()) return dayjs(value);
  return null;
};

export const parseRangeDate = (rangeString: string | Dayjs[]) => {
  if (isEmpty(rangeString) || !rangeString) {
    return [null, null];
  }

  if (!Array.isArray(rangeString) && typeof rangeString === 'string') {
    const [start, end] = rangeString.split(' - ');
    return [dayjs(start), dayjs(end)];
  }
  return rangeString;
};

export const getDayjs = (value?: Nil<Dayjs | string>) => dayjs(value);

export const parseDatetimeISO8601 = (date: string) => dayjs(date).format(DATE_FORMAT.DATE_TIME_SECOND.CUSTOM);

export const parseTimeLeftSmallFormat = (milliseconds: number) => dayjs(milliseconds).format(DATE_FORMAT.TIME._60P);

export const parseISO8601 = (date: Nil<Dayjs>, defaultValue = undefined) =>
  !date ? defaultValue : date.format(DATE_FORMAT.DATE_TIME_SECOND.ISO8601);

export function getRemainingTime(targetDate: string) {
  const now = dayjs();
  const end = dayjs(targetDate);
  const remainingTime = end.diff(now);

  // Create a duration object from the remaining time
  const remainingDuration = dayjs.duration(remainingTime);

  // Extract the components
  const years = remainingDuration.years();
  const months = remainingDuration.months();
  const days = remainingDuration.days();
  const hours = remainingDuration.hours();
  const minutes = remainingDuration.minutes();
  const seconds = remainingDuration.seconds();

  return { years, months, days, hours, minutes, seconds };
}

export const dayOfWeekKR = (date: string) => ['월', '화', '수', '목', '금', '토', '일'][dayjs(date).day()];

const dayAbbreviations = {
  Sun: '일',
  Mon: '월',
  Tue: '화',
  Wed: '수',
  Thu: '목',
  Fri: '금',
  Sat: '토',
};

export const shortDayOfWeekKR = (date: keyof typeof dayAbbreviations) => {
  return dayAbbreviations[date];
};

export const parseTimestampToDate = (timestamp: Nil<string>, format: string = DATE_FORMAT.DATE.HYPHEN): string => {
  if (!timestamp || Number(timestamp) === 0) return '';
  const date = dayjs(Number(timestamp));

  return date.isValid() ? date.format(format) : '';
};

export const parseTimestampToTime = (timestamp: Nil<string>, format: string = DATE_FORMAT.TIME._24H): string => {
  const date = dayjs(Number(timestamp));
  return date.isValid() ? date.format(format) : '';
};

const isLikelyDateString = (value: any) => {
  return typeof value === 'string' && /^\d{4}-\d{2}-\d{2}$/.test(value);
};

export const parseDatesInFilter = (filter: Record<string, any>) => {
  const formattedFilter = { ...filter };
  for (const key in formattedFilter) {
    const value = formattedFilter[key];

    if (isArray(value) && value.length === 2 && value.every((item) => dayjs(item).isValid())) {
      formattedFilter['startDate'] = parseDate(value[0]);
      formattedFilter['endDate'] = parseDate(value[1]);
      formattedFilter['date'] = undefined;
    } else if (isLikelyDateString(value) && dayjs(value).isValid()) {
      formattedFilter[key] = parseDate(value);
    } else {
      formattedFilter[key] = value;
    }
  }
  return formattedFilter;
};

export const mergeDateAndTime = (date: Nil<string>, time: string | Nil<Dayjs>, format: string = DATE_FORMAT.TIME._24H) => {
  return date && time ? `${date} ${dayjs(time)?.format(format)}` : '';
};

export const addTimeToDate = (date1: Dayjs | null, date2: Dayjs | null) => {
  if (!date1 || !date2) return;
  return date1
    .set('hour', date2.hour())
    .set('minute', date2.minute())
    .set('second', date2.second())
    .set('millisecond', date2.millisecond());
};

export const parseTimeStampToDayjs = (timestamp: string | number | null) => {
  if (!timestamp || Number(timestamp) === 0) return null;
  const parsedDate = dayjs(Number(timestamp));
  return parsedDate.isValid() ? parsedDate : null;
};
