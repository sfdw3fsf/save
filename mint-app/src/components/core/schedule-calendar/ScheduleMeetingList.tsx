import { fDate } from '@/utils/helpers/datetime.helper';
import dayjs from 'dayjs';
import { ReactNode } from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import Spinner from '../spinner/Spinner';

export type ScheduleMeetingItem = {
  title?: ReactNode;
  start?: Date;
  end?: Date;
  url?: string;
};

type ScheduleMeetingListProps = {
  meetings?: ScheduleMeetingItem[];
  enabledForTodayOnly?: boolean;
  className?: string;
  isLoading?: boolean;
};

const ScheduleMeetingList = ({
  meetings = [],
  enabledForTodayOnly = false,
  className,
  isLoading = false,
}: ScheduleMeetingListProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const isToday = (start: Date) => dayjs(new Date()).isSame(start, 'day');

  if (meetings.length < 1) return <></>;

  if (isLoading) return <Spinner raw className='mx-auto block size-10' />;

  return (
    <div className={twMerge('w-full overflow-x-auto static-scrollbar', className)}>
      <div className='flex gap-5'>
        {meetings.map(({ start, end, title, url }, index) => {
          if (!start || !end) return <></>;

          const today = isToday(start);
          const disabled = enabledForTodayOnly && !today;

          return (
            <div
              key={index}
              className='flex w-[267px] h-[106px] shrink-0 flex-col border border-comp-border-neutral-soft rounded-lg gap-3 py-2 px-3 truncate bg-white'>
              <div className='flex w-full flex-col gap-1 text-left'>
                <p className='text-sm font-medium text-comp-text-default'>{title ?? tCommon('calendar.project')}</p>
                <p className='text-sm font-normal text-comp-text-secondary'>
                  {fDate(start, 'HH:mm')} - {fDate(end, 'HH:mm')}
                </p>
              </div>
              <div className='flex w-full text-xs font-medium bg-primary-50 rounded-lg justify-between items-center gap-1 p-2'>
                <p className='text-left w-full text-comp-text-brand'>
                  {today ? `${tCommon('calendar.today')} (${fDate(start, 'YYYY.MM.DD')})` : fDate(start, 'YYYY.MM.DD')}
                </p>
                {!!url && (
                  <Link
                    target='_blank'
                    className={twMerge('underline', disabled ? 'text-comp-disabled' : 'text-comp-text-brand')}
                    to={url}
                    rel='noopener noreferrer'>
                    {tCommon('calendar.join_meeting')}
                  </Link>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ScheduleMeetingList;
