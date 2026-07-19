'use client';
import '@/components/common/calendar/toastui-react-extend-calendar.css';
import { useReservation } from '@/hook/funeral/useReservation';
import { COLORS } from '@/theme';
import { helperGetBorderColor, helperMappingIconEvent } from '@/utils/helpers/calandar.helper';
import { shortDayOfWeekKR } from '@/utils/parser/date-time.parser';
import type { ReactCalendarOptions, ReactEventObject } from '@toast-ui/react-calendar';
import { default as ToastUIReactCalendar } from '@toast-ui/react-calendar';
import { TFunction } from 'i18next';
import { isEmpty } from 'lodash';
import { useTranslation } from 'react-i18next';

type CustomCalendarProps = {
  event: Partial<ReactEventObject>[];
  calendarRef: React.MutableRefObject<ToastUIReactCalendar | null>;
  calendarEventType: ReactCalendarOptions['calendars'];
  tSales?: TFunction<'collaborating_sales', undefined>;
};

const theme: ReactCalendarOptions['theme'] = {
  common: {
    border: '1px solid #ddd',
    backgroundColor: 'white',
    holiday: { color: COLORS.error.semantic },
    saturday: { color: COLORS.blue[700] },
    dayName: { color: COLORS.neutral[80] },
    today: { color: COLORS.green['deep-aqua'] },
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

const CustomExtendCalendar = ({ event, calendarRef, calendarEventType, tSales }: CustomCalendarProps) => {
  const { t: tCommon } = useTranslation('common');

  const { reservationOptions } = useReservation();

  return (
    <div id='extend'>
      <ToastUIReactCalendar
        key={JSON.stringify(reservationOptions)}
        height='800px'
        calendars={calendarEventType}
        month={{ startDayOfWeek: 0, isAlways6Weeks: false }}
        events={event}
        template={{
          milestone(event: any) {
            return `<span style="color: #fff; background-color: ${event.backgroundColor};">${event.title}</span>`;
          },
          allday(event: any) {
            const borderColor = helperGetBorderColor(calendarEventType, event.calendarId);
            return `<span class="vertical-container-icon " style="color: #fff; background-color: ${borderColor}; border-radius: 4px ; margin-right: 4px; padding: 0 4px;"><span class="${helperMappingIconEvent(
              event.calendarId,
            )}"></span>${tSales ? tSales(`calendar.state.${event.state}` as any) : event.state}</span><span class="title-container">${
              event.title
            }<span style="color: ${borderColor};" class=${isEmpty(event.location) ? 'hidden' : 'divider'}></span><span class=${
              isEmpty(event.location) ? 'hidden' : 'funeral-location'
            }>장례번호 : ${event.location}</span></span>`;
          },
          monthDayName(event: any) {
            return `${shortDayOfWeekKR(event.label)}`;
          },
          monthGridHeader: function (data: any) {
            const date = parseInt(data.date.split('-')[2], 10);
            const today = new Date();
            const inputDate = new Date(data.date);
            return today.toDateString() === inputDate.toDateString()
              ? `<span class="toastui-calendar-weekday-grid-date toastui-calendar-weekday-grid-date-decorator toastui-calendar-template-monthGridHeader" style="margin-left: 4px; font-weight:500; background-color: #007D45; color: #fff"">${date}</span>`
              : `<span class="toastui-calendar-weekday-grid-date toastui-calendar-template-monthGridHeader">${date}</span>`;
          },
          monthGridHeaderExceed() {
            return '';
          },
          monthGridFooterExceed(hiddenEvents: string) {
            return `<span>${tCommon('calendar.and')}${hiddenEvents} ${tCommon('calendar.others')}</span>`;
          },
          monthMoreTitleDate(moreTitle: any) {
            const { ymd } = moreTitle;
            const [year, month, day] = ymd.split('-');
            return `<span style="font-weight: 600; font-size: 16px">${month}월 ${day}일</span>`;
          },
          monthMoreClose() {
            return `<span class="icon-close"></span>`;
          },
          popupDetailLocation({ location }: { location: string }) {
            return (
              <button
                style={{
                  color: '#0A6345',
                }}
                onClick={() => {
                  const id = reservationOptions.find((item) => item.label === location)?.value;

                  if (id) {
                    window.open(
                      `/funeral/reservation/?id=${id}`,
                      '_blank',
                      `noopener,noreferrer,width=800,height=${window.screen.availHeight},left=0,top=0`,
                    );
                  }
                }}>
                {location}
              </button>
            );
          },
          popupDetailState({ state }: { state: number }) {
            return '';
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
          ],
        }}
        useDetailPopup={true}
        useFormPopup={false}
        isReadOnly={true}
        view={'month'}
        week={{
          showTimezoneCollapseButton: true,
          timezonesCollapsed: false,
          eventView: true,
          taskView: true,
        }}
        className='extend'
        ref={calendarRef as React.LegacyRef<ToastUIReactCalendar>}
        gridSelection={false}
      />
    </div>
  );
};

export default CustomExtendCalendar;
