import { ANNOUNCEMENT } from '@/utils/enums/home/home.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';

type AnnouncementReservation = {
  id?: number;
  reservationId?: number;
  detail: string;
  type: ANNOUNCEMENT;
  status: ENUM_STATUS;
};

type AnnouncementOthers = {
  id?: number;
  detail: string;
  startDate: number;
  endDate: number;
  status: ENUM_STATUS;
};

export type AnnouncementUpdateREQ = {
  content: string;
  date: number;
  reservations?: AnnouncementReservation[];
  others?: AnnouncementOthers[];
};

export type AnnouncementScheduleREQ = {
  startDate: number;
  endDate: number;
};
