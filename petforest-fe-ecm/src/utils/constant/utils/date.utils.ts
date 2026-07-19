import { parseTimeStampToDayjs } from '@/utils/parser/date-time.parser';
import { Day } from '@hassanmojab/react-modern-calendar-datepicker';
import { DATE_FORMAT } from '../date.constant';
import { MAPPING_WEEK_DAYS } from '../reservation/mapping.constant';

export const getDaysBeforeToday = (): Day[] => {
  const today = new Date();
  const daysBeforeToday: Day[] = [];

  // Create a date representing the start of the month
  const startOfMonth = new Date(today.getFullYear(), today.getMonth(), 1);

  // Loop through each day of the month
  for (let date = startOfMonth; date.getDate() < today.getDate(); date.setDate(date.getDate() + 1)) {
    const currentDate = new Date(date);
    daysBeforeToday.push({
      year: currentDate.getFullYear(),
      month: currentDate.getMonth() + 1,
      day: currentDate.getDate(),
    });
  }

  return daysBeforeToday;
};

export const getToday = (): Day => {
  const today = new Date();
  const todayValue: Day = {
    year: today.getFullYear(),
    month: today.getMonth() + 1,
    day: today.getDate(),
  };

  return todayValue;
};

export const getWeekday = (dateInput: string): string => {
  const date = new Date(dateInput);
  const weekdays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

  const dayOfWeek = date.getDay();

  return weekdays[dayOfWeek];
};

export const formatKoreanDate = (timestamp: number): string => {
  const date = parseTimeStampToDayjs(timestamp);
  const weekdayName = getWeekday(date?.format(DATE_FORMAT.DATE.HYPHEN) ?? '');
  const koreanDay = MAPPING_WEEK_DAYS[weekdayName]?.short ?? '';

  const year = date?.year() ?? '';
  const month = date?.month() ? date?.month() + 1 : '';
  const day = date?.date() ?? '';

  return `${year}년 ${month}월 ${day}일 (${koreanDay})`;
};
