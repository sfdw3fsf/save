import { ANNOUNCEMENT, ANNOUNCEMENT_INACTIVE } from '@/utils/enums/home/home.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';

type AnnouncementReservation = {
  id: number;
  detail: string;
  status: ENUM_STATUS;
  type: ANNOUNCEMENT;
  registeredDate: string;
  reservationId: number;
};

export type AnnouncementDTO = {
  id: number;
  content: string;
  reservations: [];
  others: [];
};

export type AnnouncementSchedule = {
  id: string;
  calendarId: ANNOUNCEMENT | ANNOUNCEMENT_INACTIVE;
  title: string;
  state: string;
  category: 'allday';
  start: string;
  end: string;
  isReadOnly: boolean;
  location: string;
};

export type AnnouncementScheduleDTO = {
  announcement: string;
  schedules: AnnouncementSchedule[];
};
