import { RESERVATION_TIME_SHIFT, RESERVATION_TIME_STATUS } from '@/utils/enums/manager/manager.enum';

export type ReservationTime = {
  id: number;
  usedInReservationTime: boolean;
  time: string;
  shift: RESERVATION_TIME_SHIFT;
  status: RESERVATION_TIME_STATUS;
};

export type ManagerReservationTimeDTO = {
  AM: ReservationTime[];
  PM: ReservationTime[];
};
