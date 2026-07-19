import { TScheduleEvent } from '@/components/core/schedule-calendar/ScheduleCalendar';
import { fDate } from '@/utils/helpers/datetime.helper';

export const combineDates = (meetings: TScheduleEvent[]) => {
  if (meetings?.length === 0) return '';
  return meetings
    .map((meeting) => {
      return `${fDate(meeting?.start, 'D[th]')} (${fDate(meeting?.start, 'HH:mm')} - ${fDate(meeting?.end, 'HH:mm')})`;
    })
    .join(' - ');
};
