import { LucetteScheduleListDTO } from '@/store/funeral/lucette/dto/lucette.dto';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { ReactCalendarOptions } from '@toast-ui/react-calendar';
import dayjs from 'dayjs';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

type CustomMonthCalendarProps = {
  year: number;
  month: number;
  events: LucetteScheduleListDTO[];
  calendarOptions: ReactCalendarOptions['calendars'];
  className?: string;
  onClickEvent?: (event: any) => void;
};

const CustomMonthCalendar = ({ year, month, events, calendarOptions, className, onClickEvent }: CustomMonthCalendarProps) => {
  const { t: tCommon } = useTranslation('common');

  const todayStr = dayjs().format(DATE_FORMAT.DATE.HYPHEN);

  const eventMap = events.reduce<Record<string, LucetteScheduleListDTO[]>>((acc, event) => {
    const eventDate = dayjs(event.start).format(DATE_FORMAT.DATE.HYPHEN);
    acc[eventDate] = acc[eventDate] || [];
    acc[eventDate].push(event);
    return acc;
  }, {});

  const getEventStyle = (calendarId: string): Record<string, string> => {
    const option = calendarOptions.find((opt: any) => opt.id === calendarId);
    return option
      ? {
          backgroundColor: option.backgroundColor,
          borderColor: option.borderColor,
          color: option.color,
        }
      : {};
  };

  return (
    <div className={twMerge('flex flex-col gap-6', className)}>
      {Array.from({ length: dayjs(`${year}-${month}`).daysInMonth() }, (_, index) => {
        const date = dayjs(`${year}-${month}-${index + 1}`);
        const dateStr = date.format(DATE_FORMAT.DATE.HYPHEN);
        const dayIndex = date.day(); // 0 = Sunday, 6 = Saturday
        const events = eventMap[dateStr] || [];

        return (
          <div className='flex gap-2 items-start justify-start' key={dateStr}>
            <div
              className={twMerge(
                'flex flex-col gap-0.5 items-center justify-center w-full max-w-7 text-center text-neutral-80',
                dayIndex === 0 && 'text-red-600', // Sunday
                dayIndex === 6 && 'text-blue-700', // Saturday
              )}>
              <div className='flex items-center justify-center w-full h-4 text-xs font-normal truncate'>{date.format('ddd')}</div>
              <div
                className={twMerge(
                  'flex items-center justify-center w-full h-auto aspect-square text-lg font-semibold shrink-0 rounded-full truncate',
                  dateStr === todayStr && 'bg-primary-850 text-neutral-50',
                )}>
                {date.format('D')}
              </div>
            </div>

            <div className='flex flex-col w-full gap-2'>
              {events.length > 0 ? (
                events.map((event) => {
                  const eventStyle = getEventStyle(event.calendarId);
                  return (
                    <button
                      onClick={() => onClickEvent?.(event.id)}
                      className='px-2 py-1 text-sm rounded-md border font-medium text-neutral-100'
                      key={event.id}
                      type='button'
                      form='_noexist'
                      style={eventStyle}>
                      {event.title}
                    </button>
                  );
                })
              ) : (
                <div className='text-xs text-gray-400'>{tCommon('calendar.no_events')}</div>
              )}
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default CustomMonthCalendar;
