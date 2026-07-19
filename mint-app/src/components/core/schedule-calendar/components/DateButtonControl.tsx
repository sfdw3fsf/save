import CaretLeft from '@/components/icons/CaretLeftIcon';
import CaretRight from '@/components/icons/CaretRightIcon';
import { getEndDateByView } from '@/utils/helpers/calendar.helper';
import { fDate } from '@/utils/helpers/datetime.helper';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import Button from '../../button/Button';
import IconButton from '../../icon-button/IconButton';
import { ScheduleCalendarView } from '../ScheduleCalendar';

type DateButtonControlProps = {
  startDate: Date;
  endDate: Date;
  onBack: () => void;
  onNext: () => void;
  showToday?: boolean;
  onClickToday?: () => void;
  view: ScheduleCalendarView;
  className?: string;
};
const DateButtonControl = ({
  startDate,
  endDate,
  onBack,
  onNext,
  showToday = false,
  onClickToday,
  view,
  className,
}: DateButtonControlProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const dateString = useMemo(() => {
    switch (view) {
      case 'month':
        return fDate(startDate, 'MMMM');
      case 'day': // return fDate(startDate, 'YYYY.MM.DD');
      case 'week':
      default: {
        const start = fDate(startDate, 'YYYY.MM.DD'),
          end = fDate(endDate ?? getEndDateByView(startDate, view), 'YYYY.MM.DD');
        return start === end ? start : `${start} - ${end}`;
      }
    }
  }, [endDate, startDate, view]);

  return (
    <div className={twMerge('flex md:w-fit w-full items-center justify-center gap-6 max-md:flex-col max-md:gap-2', className)}>
      {showToday && <Button onClick={onClickToday}>{tCommon('calendar.today')}</Button>}
      <div className='flex md:w-fit w-full items-center justify-center gap-2'>
        <IconButton icon={<CaretLeft />} sizing='xs' onClick={onBack} />
        <div title={dateString} className='text-slate-800 text-md truncate'>
          {dateString}
        </div>
        <IconButton icon={<CaretRight />} sizing='xs' onClick={onNext} />
      </div>
    </div>
  );
};

export default DateButtonControl;
