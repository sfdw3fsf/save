import { ScheduleCalendarView } from '@/components/core/schedule-calendar/ScheduleCalendar';
import i18n from '@/i18n';
import dayjs from 'dayjs';

export const getEndDateByView = (startDate: Date, view: ScheduleCalendarView) => {
  switch (view) {
    case 'month':
      return dayjs(startDate).add(1, 'M').toDate();
    case 'day': // return startDate;
    case 'week':
    default:
      return dayjs(startDate).add(6, 'd').toDate();
  }
};

export const getNextDateByView = (date: Date, selectedView: ScheduleCalendarView): Date => {
  switch (selectedView) {
    case 'month':
      return dayjs(date).add(1, 'M').toDate();
    case 'day': // return dayjs(date).add(1, 'd').toDate();
    case 'week':
    default:
      return dayjs(date).add(1, 'w').toDate();
  }
};

export const getPrevDateByView = (date: Date, view: ScheduleCalendarView): Date => {
  switch (view) {
    case 'month':
      return dayjs(date).subtract(1, 'M').toDate();
    case 'day': // return dayjs(date).subtract(1, 'd').toDate();
    case 'week':
    default:
      return dayjs(date).subtract(1, 'w').toDate();
  }
};

export const getStartDateByView = (date: Date, view: ScheduleCalendarView): Date => {
  switch (view) {
    case 'month':
      return dayjs(date).startOf('month').toDate();
    case 'day': // return date;
    case 'week':
    default:
      return dayjs(date).startOf('week').toDate();
  }
};

export const getWeekdays = (format: 'ddd' | 'dddd' = 'ddd'): string[] => {
  const weekdays = [];
  const locale = i18n.language;
  for (let i = 0; i < 7; i++) {
    weekdays.push(dayjs().day(i).locale(locale).format(format));
  }
  return weekdays;
};
