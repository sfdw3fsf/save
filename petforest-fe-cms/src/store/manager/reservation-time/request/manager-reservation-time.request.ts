import { RESERVATION_TIME_STATUS } from '@/utils/enums/manager/manager.enum';
import { Dayjs } from 'dayjs';

export type ReservationTimeFilter = {
  date: Dayjs;
};

export type ReservationTimeREQ = {
  date: number;
};

export type ReservationTimeUpdateStatusREQ = {
  date: number;
  status: RESERVATION_TIME_STATUS;
};
