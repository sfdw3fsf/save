'use client';
import '@/components/common/calendar/toastui-react-calendar.css';
import useResize from '@/hook/useResize';
import { LucetteScheduleListDTO } from '@/store/funeral/lucette/dto/lucette.dto';
import { COLORS } from '@/theme';
import { ENUM_STATUS, ENUM_STATUS_INACTIVE } from '@/utils/enums/status.enum';
import { shortDayOfWeekKR } from '@/utils/parser/date-time.parser';
import type { ReactCalendarOptions, ReactEventObject } from '@toast-ui/react-calendar';
import { default as ToastUIReactCalendar } from '@toast-ui/react-calendar';
import { useTranslation } from 'react-i18next';
import { twJoin } from 'tailwind-merge';
import CustomMonthCalendar from './CustomMonthCalendar';

type CustomCalendarProps = {
  event: Partial<ReactEventObject>[];
  calendarRef: React.MutableRefObject<ToastUIReactCalendar | null>;
  year: number;
  month: number;
};

const theme: ReactCalendarOptions['theme'] = {
  common: {
    border: '1px solid #ddd',
    backgroundColor: 'white',
    holiday: { color: COLORS.error.semantic },
    saturday: { color: COLORS.blue[700] },
    dayName: { color: COLORS.neutral[80] },
    today: { color: COLORS.white },
  },
  month: {
    dayName: {
      borderLeft: 'none',
      backgroundColor: 'inherit',
    },
    gridCell: {
      footerHeight: 30,
      headerHeight: 30,
    },
    holidayExceptThisMonth: { color: COLORS.error.except },
    dayExceptThisMonth: { color: COLORS.neutral[40] },
    weekend: { backgroundColor: COLORS.white },
    moreView: { boxShadow: 'none' },
    moreViewTitle: { backgroundColor: COLORS.white },
  },
};

const CustomCalendar = ({ event, calendarRef, year, month }: CustomCalendarProps) => {
  const { t: tCommon } = useTranslation('common');

  const { isResponsive } = useResize();

  const initialCalendars: ReactCalendarOptions['calendars'] = [
    {
      id: ENUM_STATUS.COMPLETE,
      name: 'completed',
      backgroundColor: COLORS.blue.light,
      borderColor: COLORS.blue.cloud,
      color: COLORS.neutral[100],
      dragBackgroundColor: COLORS.blue.light,
    },
    {
      id: ENUM_STATUS.INCOMPLETE,
      name: 'incomplete',
      backgroundColor: COLORS.primary[100],
      borderColor: COLORS.primary[500],
      color: COLORS.brand.forest,
      dragBackgroundColor: COLORS.primary[100],
    },
    {
      id: ENUM_STATUS_INACTIVE.COMPLETE,
      name: 'completed',
      backgroundColor: COLORS.blue.ice,
      borderColor: COLORS.blue.mist,
      color: COLORS.neutral[40],
      dragBackgroundColor: COLORS.blue.ice,
    },
    {
      id: ENUM_STATUS_INACTIVE.INCOMPLETE,
      name: 'incomplete',
      backgroundColor: COLORS.green.mint,
      borderColor: COLORS.green.pastel,
      color: COLORS.green.sage,
      dragBackgroundColor: COLORS.green.mint,
    },
  ];

  const handleClickEvent = (eventId: any) => {
    window.open(
      `/funeral/reservation/?id=${eventId}`,
      '_blank',
      `noopener,noreferrer,width=800,height=${window.screen.availHeight},left=0,top=0`,
    );
  };

  return (
    <>
      <CustomMonthCalendar
        className={twJoin(!isResponsive && 'hidden')}
        calendarOptions={initialCalendars}
        events={event as LucetteScheduleListDTO[]}
        year={year}
        month={month}
        onClickEvent={handleClickEvent}
      />

      <div className={twJoin('border-b-[1px] border-neutral-40', isResponsive && 'hidden')}>
        <ToastUIReactCalendar
          height='800px'
          onClickEvent={(event: any) => handleClickEvent(event.event.id)}
          calendars={initialCalendars}
          month={{ startDayOfWeek: 0, isAlways6Weeks: false }}
          events={event}
          template={{
            milestone(event: any) {
              return `<span style="color: #fff; background-color: ${event.backgroundColor};">${event.title}</span>`;
            },
            allday(event: any) {
              return `${event.title}`;
            },
            monthDayName(event: any) {
              return `${shortDayOfWeekKR(event.label)}`;
            },
            monthGridHeader: function (data: any) {
              const date = parseInt(data.date.split('-')[2], 10);
              const today = new Date();
              const inputDate = new Date(data.date);
              return today.toDateString() === inputDate.toDateString()
                ? `<span  class="toastui-calendar-weekday-grid-date toastui-calendar-weekday-grid-date-decorator toastui-calendar-template-monthGridHeader" style="margin-left: 4px; font-weight:500; background-color: #007D45; color: #fff"">${date}</span>`
                : `<span class="toastui-calendar-weekday-grid-date toastui-calendar-template-monthGridHeader">${date}</span>`;
            },
            monthGridHeaderExceed() {
              return '';
            },
            monthGridFooterExceed(hiddenEvents: string) {
              return `<span>${tCommon('calendar.and')}${hiddenEvents} ${tCommon('calendar.others')}</span>`;
            },
          }}
          theme={theme}
          timezone={{
            zones: [
              {
                timezoneName: 'Asia/Seoul',
                displayLabel: 'Seoul',
                tooltip: 'UTC+09:00',
              },
              {
                timezoneName: 'Pacific/Guam',
                displayLabel: 'Guam',
                tooltip: 'UTC+10:00',
              },
            ],
          }}
          useDetailPopup={false}
          useFormPopup={false}
          isReadOnly={true}
          view={'month'}
          week={{
            showTimezoneCollapseButton: true,
            timezonesCollapsed: false,
            eventView: true,
            taskView: true,
          }}
          ref={calendarRef as React.LegacyRef<ToastUIReactCalendar>}
          gridSelection={false}
        />
      </div>
    </>
  );
};

export default CustomCalendar;
