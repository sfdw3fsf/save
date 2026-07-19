import { FuneralMNGTReservationListDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { RESERVATION_TIME_SHIFT, RESERVATION_TIME_STATUS } from '@/utils/enums/manager/manager.enum';
import dayjs, { Dayjs } from 'dayjs';
import { ManagerReservationTimeDTO } from './dto/manager-reservation-time.dto';
import {
  ReservationTimeFilter,
  ReservationTimeREQ,
  ReservationTimeUpdateStatusREQ,
} from './request/manager-reservation-time.request';
import { ManagerReservationTimeRESP } from './response/manager-reservation-time.response';

export const reservationTimeRespToDto = (r: ManagerReservationTimeRESP[]): ManagerReservationTimeDTO => {
  return {
    AM: r.filter((item) => item.shift === RESERVATION_TIME_SHIFT.AM).sort((a, b) => a.time.localeCompare(b.time)),
    PM: r.filter((item) => item.shift === RESERVATION_TIME_SHIFT.PM).sort((a, b) => a.time.localeCompare(b.time)),
  };
};

export const reservationTimeParamsToFilter = (searchParams: URLSearchParams): ReservationTimeFilter => {
  return {
    date: searchParams.get('date') ? dayjs(searchParams.get('date')) : dayjs(),
  };
};

export const reservationTimeFilterToRequest = (i: ReservationTimeFilter): ReservationTimeREQ => {
  return {
    date: i.date.valueOf(),
  };
};

export const reservationTimeAllStatusToRequest = (
  date: Dayjs,
  status: RESERVATION_TIME_STATUS,
): ReservationTimeUpdateStatusREQ => {
  return {
    date: date.valueOf(),
    status,
  };
};

export const getArrayTimeString = (status: RESERVATION_TIME_STATUS, data?: ManagerReservationTimeDTO) => {
  const amTimes = data?.AM.filter((item) => item.status === status).map((item) => item.time);
  const pmTimes = data?.PM.filter((item) => item.status === status).map((item) => item.time);
  const times = amTimes?.concat(pmTimes || []);

  return times?.join(', ');
};

export const reservationTimeRespToOptions = (d: ManagerReservationTimeRESP[]) => {
  return d.map((item) => ({
    text: item.time,
    value: item.time,
    label: item.time,
  }));
};

export const reservationToOptions = (r: FuneralMNGTReservationListDTO[]) => {
  return r.map((item) => ({
    label: item.code,
    value: item.id,
  }));
};
