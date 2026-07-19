import dayjs from 'dayjs';

export const timeToMinutes = (time: string) => {
  const [hours, minutes] = time.split(':').map(Number);
  return hours * 60 + minutes;
};

export const getMonthRange = (month: number, year?: number) => {
  const resultYear = year ?? new Date().getFullYear();

  const firstDate = dayjs(`${resultYear}-${month}-01`).startOf('day');
  const lastDate = firstDate.endOf('month');

  return [firstDate, lastDate];
};

export const getTodayRange = () => {
  const today = dayjs();
  const startOfDay = today.startOf('day').valueOf();
  const endOfDay = today.endOf('day').valueOf();

  return [startOfDay, endOfDay];
};

export const getYearFromTodayRange = () => {
  const today = dayjs();
  const startOfDay = today.startOf('day').valueOf();
  const endOfDay = today.add(1, 'year').endOf('day').valueOf();

  return [startOfDay, endOfDay];
};

export const getCurrentMonthRange = () => {
  const startDate = dayjs().startOf('month').valueOf();
  const today = dayjs().endOf('month').valueOf();

  return [startDate, today];
};

export const getLastMonthRange = () => {
  const startDate = dayjs().subtract(1, 'month').startOf('day').valueOf();
  const today = dayjs().endOf('day').valueOf();

  return [startDate, today];
};

export const getToday = () => {
  return dayjs().valueOf();
};
