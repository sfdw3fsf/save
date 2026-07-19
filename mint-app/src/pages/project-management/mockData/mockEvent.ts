import { TScheduleEvent } from '@/components/core/schedule-calendar/ScheduleCalendar';
import dayjs from 'dayjs';

export const mockEvent: TScheduleEvent[] = [
  {
    id: 99,
    start: dayjs(1727892000000).toDate(),
    end: dayjs(1727917200000).toDate(),
  },
  {
    id: 999,
    start: dayjs(1727920800000).toDate(),
    end: dayjs(1727942400000).toDate(),
  },
  {
    id: 9999,
    start: dayjs(1727946000000).toDate(),
    end: dayjs(1727956800000).toDate(),
  },
  {
    id: 99999,
    start: dayjs(1728522000000).toDate(),
    end: dayjs(1728529200000).toDate(),
  },
];
