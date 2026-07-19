import ArrowRightIcon from '@/components/icons/ArrowRightIcon';
import ClockIcon from '@/components/icons/ClockIcon';
import { fDate } from '@/utils/helpers/datetime.helper';
import dayjs from 'dayjs';
import { twMerge } from 'tailwind-merge';

type ScheduleCardItemProps = {
  start?: Date;
  end?: Date;
  rightSection?: React.ReactNode;
  className?: string;
  bottomSection?: React.ReactNode;
  title?: string | React.ReactNode;
};
const ScheduleCardItem = ({ start, end, rightSection, className, bottomSection, title }: ScheduleCardItemProps): JSX.Element => {
  return (
    <div className='border-slate-200 border-[1px] rounded-lg w-full flex flex-col py-4 px-4 overflow-hidden'>
      <div className={twMerge('w-full justify-between flex items-start gap-4', className)}>
        <div className='flex flex-col w-full'>
          {!!title && <div className='text-sm text-slate-800 mb-2'>{title}</div>}
          <div className='flex justify-between items-center'>
            <div className='flex gap-1 items-start'>
              <ClockIcon className='text-slate-500 shrink-0' />
              <div className='text-slate-500 text-sm whitespace-pre-line text-nowrap'>
                {dayjs(start).locale('en').format('h:mm A')}
              </div>
            </div>
            <div className='flex gap-1 items-start'>
              <ArrowRightIcon className='text-slate-500' />
              <div className='text-slate-500 text-sm whitespace-pre-line text-nowrap'>
                {dayjs(end).locale('en').format('h:mm A')}
              </div>
            </div>
          </div>
          <div className='text-slate-500 text-sm pl-6 mt-4'>{fDate(start, 'MMMM DD')}</div>
        </div>
        {!!rightSection && rightSection}
      </div>
      {!!bottomSection && bottomSection}
    </div>
  );
};

export default ScheduleCardItem;
