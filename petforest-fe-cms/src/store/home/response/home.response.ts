import { ANNOUNCEMENT } from '@/utils/enums/home/home.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';

export type AnnouncementReservation = {
  id: number;
  detail: string;
  status: ENUM_STATUS;
  type: ANNOUNCEMENT;
  registeredDate: string;
  reservation: {
    id: number;
    code: string;
  };
};

export type AnnouncementOthers = {
  id: number;
  startDate: string;
  endDate: string;
  detail: string;
  status: ENUM_STATUS;
  type: ANNOUNCEMENT;
};

export type AnnouncementRESP = {
  id: number;
  content: string;
  reservations: AnnouncementReservation[];
  others: AnnouncementOthers[];
};

type AnnouncementRecord = {
  records: (AnnouncementReservation | AnnouncementOthers)[];
  counts: Record<ANNOUNCEMENT, number>;
};

export type AnnouncementScheduleRESP = {
  announcement: string;
  schedules: Record<string, AnnouncementRecord>;
};
