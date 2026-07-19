import { fDate } from '@/utils/helpers/datetime.helper';
import dayjs from 'dayjs';
import { useMemo } from 'react';
import { Event } from 'react-big-calendar';
import { twMerge } from 'tailwind-merge';

export type TCalendarVariant = 'scheduled' | 'desired' | 'mixed' | 'shortcut';
type ScheduleCalendarItemProps = {
  event: Event;
  variant?: TCalendarVariant;
};

const theme = {
  scheduled: {
    cont: 'bg-green-50 py-2 px-1 w-full',
    text: 'text-primary-500 mb-2',
  },
  desired: {
    cont: 'bg-slate-50 py-2 px-1 w-full',
    text: 'text-slate-800 mb-2',
  },
  mixed: {
    cont: 'bg-slate-50 py-2 px-1 w-full',
    text: 'text-primary-500 mb-2',
  },
  shortcut: {
    cont: 'px-1 py-1 mx-4 overflow-hidden',
    text: 'text-primary-500 truncate',
  },
};

const ScheduleCalendarItem = ({ event, variant = 'desired' }: ScheduleCalendarItemProps): JSX.Element => {
  const { title, detail } = useMemo(() => {
    switch (variant) {
      case 'shortcut':
        return {
          title: event?.title,
          detail: null,
        };
      default:
        return {
          title: fDate(event?.start),
          detail: `${dayjs(event?.start).locale('en').format('h:mm A')} - ${dayjs(event?.end).locale('en').format('h:mm A')}`,
        };
    }
  }, [event, variant]);

  return (
    <div className={twMerge('h-full flex-flex-col py-2 px-1', theme[variant].cont)}>
      <div title={typeof title === 'string' ? title : undefined} className={twMerge('font-medium text-xs', theme[variant].text)}>
        {title}
      </div>
      <div className={twMerge('text-xs', theme[variant].text)}>{detail}</div>
    </div>
  );
};

export default ScheduleCalendarItem;
