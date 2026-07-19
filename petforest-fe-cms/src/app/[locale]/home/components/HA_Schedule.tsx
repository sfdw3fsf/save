'use client';
import CustomExtendCalendar from '@/components/common/calendar/CustomExtendCalendar';
import CustomButton from '@/components/common/CustomButton';
import CustomIconButton from '@/components/common/CustomIconButton';
import CustomText from '@/components/common/Text';
import { useCalendar } from '@/hook/useCalendar';
import { AnnouncementSchedule } from '@/store/home/dto/home.dto';
import { AnnouncementScheduleInput } from '@/store/home/input/home.input';
import { COLORS } from '@/theme';
import { ANNOUNCEMENT, ANNOUNCEMENT_INACTIVE } from '@/utils/enums/home/home.enums';
import { getMonthRange } from '@/utils/helpers/date-time.helper';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import ToastUIReactCalendar, { ReactCalendarOptions, ReactEventObject } from '@toast-ui/react-calendar';
import { Dayjs } from 'dayjs';
import { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';

type HA_ScheduleProps = {
  month: AnnouncementScheduleInput;
  onChangeMonth: ({ startDate, endDate }: AnnouncementScheduleInput) => void;
  data: AnnouncementSchedule[];
  filter: AnnouncementScheduleInput;
  onFilterChange: (calendarDateRange: [Dayjs, Dayjs]) => void;
};

const HA_Schedule = ({ month, onChangeMonth, data, filter, onFilterChange }: HA_ScheduleProps) => {
  const { t: tCommon } = useTranslation('common');

  const calendarRef = useRef<ToastUIReactCalendar | null>(null);
  const {
    currentMonth,
    currentYear,
    selectedDateRangeText,
    handleClickNextButton,
    handleClickPreButton,
    handleClickTodayButton,
    calendarDateRange,
  } = useCalendar(calendarRef);

  const [events, setEvents] = useState<Partial<ReactEventObject>[]>(data);

  const initialCalendars: ReactCalendarOptions['calendars'] = [
    {
      id: ANNOUNCEMENT.FUNERAL,
      backgroundColor: COLORS.green['light-pastel'],
      borderColor: COLORS.green['bright-sage'],
      color: COLORS.neutral[100],
      dragBackgroundColor: COLORS.green['light-pastel'],
    },
    {
      id: ANNOUNCEMENT.NOT_ACCOMPANY,
      backgroundColor: COLORS.orange.light,
      borderColor: COLORS.orange.bright,
      color: COLORS.neutral[100],
      dragBackgroundColor: COLORS.orange.light,
    },
    {
      id: ANNOUNCEMENT.ETC,
      backgroundColor: COLORS.blue.soft,
      borderColor: COLORS.blue.sky,
      color: COLORS.neutral[100],
      dragBackgroundColor: COLORS.blue.soft,
    },
    {
      id: ANNOUNCEMENT_INACTIVE.FUNERAL,
      backgroundColor: COLORS.green['soft-mint'],
      borderColor: COLORS.green['soft-sage'],
      color: COLORS.neutral[60],
      dragBackgroundColor: COLORS.green['soft-mint'],
    },
    {
      id: ANNOUNCEMENT_INACTIVE.NOT_ACCOMPANY,
      backgroundColor: COLORS.orange.soft,
      borderColor: COLORS.orange.hidden,
      color: COLORS.neutral[60],
      dragBackgroundColor: COLORS.green.mint,
    },
    {
      id: ANNOUNCEMENT_INACTIVE.ETC,
      backgroundColor: COLORS.blue.hidden,
      borderColor: COLORS.blue.misty,
      color: COLORS.neutral[60],
      dragBackgroundColor: COLORS.blue.hidden,
    },
  ];

  useEffect(() => {
    setEvents(data);
    const monthRange = getMonthRange(currentMonth, currentYear);
    onChangeMonth({ startDate: monthRange[0], endDate: monthRange[1] });
  }, [currentMonth, currentYear, data]);

  useEffect(() => {
    if (!calendarDateRange) return;
    if (calendarDateRange[0].isSame(filter.startDate, 'day') && calendarDateRange[1].isSame(filter.endDate, 'day')) {
      return;
    }
    onFilterChange(calendarDateRange as [Dayjs, Dayjs]);
  }, [calendarDateRange, onFilterChange, filter]);

  return (
    <div>
      <div className='flex flex-col gap-3 justify-center md:my-6 md:flex-row'>
        <div className='flex gap-1 md:gap-3 items-center'>
          <CustomIconButton
            icon={<LeftOutlined style={{ fontSize: '10px', color: COLORS.neutral[70] }} />}
            onClick={handleClickPreButton}
            className='!border !border-neutral-40 !h-[39px] !w-[39px]'
          />
          <CustomText variant='title-3' className='px-2 hidden md:block'>
            {selectedDateRangeText}
          </CustomText>
          {/* <CustomRangePicker value={[month.startDate, month.endDate]} classNameLabel='block md:hidden flex-1' /> */}
          <CustomText variant='headline-1' className='px-2 block md:hidden !text-neutral-80 !font-extrabold'>
            {selectedDateRangeText}
          </CustomText>
          <CustomIconButton
            icon={<RightOutlined style={{ fontSize: '10px', color: COLORS.neutral[70] }} />}
            onClick={handleClickNextButton}
            className='!border !border-neutral-40 !h-[39px] !w-[39px]'
          />
          <CustomButton type='default' onClick={handleClickTodayButton} className='!hidden md:!block'>
            {tCommon('date.today')}
          </CustomButton>
        </div>
      </div>

      <div className='border-b-[1px] border-neutral-40'>
        <CustomExtendCalendar calendarEventType={initialCalendars} event={events} calendarRef={calendarRef} />
      </div>
    </div>
  );
};

export default HA_Schedule;
