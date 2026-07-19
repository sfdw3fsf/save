import { ANNOUNCEMENT } from '@/utils/enums/home/home.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { Dayjs } from 'dayjs';

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
  date: [Dayjs, Dayjs];
  status: ENUM_STATUS;
};

export type AnnouncementUpdateInput = {
  id?: number;
  content: string;
  date: Dayjs;
  reservations?: AnnouncementReservation[];
  unaccompany?: AnnouncementReservation[];
  others?: AnnouncementOthers[];
};

export type AnnouncementScheduleInput = {
  startDate: Dayjs;
  endDate: Dayjs;
};
