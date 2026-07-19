import i18n from '@/i18n';
import { getWeekdays } from '@/utils/helpers/calendar.helper';
import { fDate } from '@/utils/helpers/datetime.helper';
import dayjs from 'dayjs';
import { useMemo } from 'react';
import { twMerge } from 'tailwind-merge';

type ScheduleCalendarHeaderProps = {
  startDate: Date;
  endDate: Date;
  selectedDate: Date;
  setSelectedDate: (selectedDate: Date) => void;
  size?: 'lg' | 'sm';
  variant?: 'range' | 'weekday';
  className?: string;
};

const theme = {
  lg: {
    cont: 'pl-[68.34px] bg-slate-25 border-slate-200 border-[1px] rounded-lg py-[6.2px]',
  },
  sm: {
    cont: '',
  },
  cont: {
    range: '',
    weekday: 'pl-0',
  },
  spacing: {
    range: 'justify-center',
    weekday: ' justify-start px-3',
  },
};

const ScheduleCalendarHeader = ({
  startDate,
  endDate,
  selectedDate,
  setSelectedDate,
  size = 'lg',
  variant = 'range',
  className,
}: ScheduleCalendarHeaderProps): JSX.Element => {
  const dates = useMemo(() => {
    if (!startDate || !endDate) return [];
    const startDayjs = dayjs(startDate);
    const endDayjs = dayjs(endDate);
    const totalDays = endDayjs.diff(startDayjs, 'day') + 1; // Include both start and end dates

    return Array.from({ length: totalDays }, (_, index) => startDayjs.add(index, 'day').toDate());
  }, [endDate, startDate]);

  const isSameDate = (item: Date) => {
    if (dayjs(selectedDate).isSame(item, 'date')) return true;
    return false;
  };

  const weekdays = useMemo<string[]>(() => getWeekdays('ddd'), [i18n.language]);

  return (
    <div className={twMerge('w-full flex', theme[size].cont, theme.cont[variant], className)}>
      <div className='w-full flex items-center overflow-x-auto scrollbar-none'>
        {variant === 'weekday' &&
          weekdays.map((item, index) => (
            <div key={index} className={twMerge('w-full flex items-center', theme.spacing[variant])}>
              <div className='text-comp-text-secondary text-xs'>{item}</div>
            </div>
          ))}

        {variant === 'range' &&
          dates?.map((item, index) => {
            if (size === 'sm')
              return (
                <button
                  form='_noexist'
                  key={index}
                  className={twMerge(
                    'w-full flex flex-col min-w-[60.6px] items-center justify-center rounded-lg py-2',
                    isSameDate(item) && 'bg-primary-500',
                  )}
                  onClick={() => setSelectedDate(item)}>
                  <div className={twMerge('text-xs', isSameDate(item) ? 'text-white' : 'text-comp-text-secondary')}>
                    {fDate(item, 'ddd')}
                  </div>
                  <div className={twMerge('text-md', isSameDate(item) ? 'text-white' : 'text-comp-text-default')}>
                    {item.getDate()}
                  </div>
                </button>
              );
            return (
              <div
                key={index}
                className={twMerge('w-full flex items-center', dates.length === 1 ? 'justify-center' : 'justify-start')}>
                <div className='text-comp-text-secondary text-xs'>{fDate(item, 'D ddd')}</div>
              </div>
            );
          })}
      </div>
    </div>
  );
};

export default ScheduleCalendarHeader;
