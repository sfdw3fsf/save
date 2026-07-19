import { RESERVATION_TIME_SHIFT } from '@/utils/enums/reservation.enum';
import { numberOrZero, orString, orUndefined } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import { ReservationDetailDTO, ReservationDTO, ReservationTimeDTO } from './dto/reservation.dto';
import { ReservationCreateREQ, ReservationTimeFilter, ReservationTimeListREQ } from './request/reservation.request';
import { ReservationDetailRESP, ReservationPetTypeRESP, ReservationTimeRESP } from './response/reservation.response';

export const reservationTimeRespToDto = (r: ReservationTimeRESP[]): ReservationTimeDTO => {
  return {
    AM: r.filter((item) => item.shift === RESERVATION_TIME_SHIFT.AM).sort((a, b) => a.time.localeCompare(b.time)),
    PM: r.filter((item) => item.shift === RESERVATION_TIME_SHIFT.PM).sort((a, b) => a.time.localeCompare(b.time)),
  };
};

export const reservationTimeParamsToFilter = (searchParams: URLSearchParams): ReservationTimeFilter => {
  return {
    date: searchParams.get('date') ? Number(searchParams.get('date')) : undefined,
  };
};

export const reservationTimeFilterToRequest = (i: ReservationTimeFilter): ReservationTimeListREQ => {
  return {
    date: Number(i.date) || 0,
    branchId: i.branchId || 0,
    timezone: Intl.DateTimeFormat().resolvedOptions().timeZone,
  };
};

export const reservationDtoToReq = (r: ReservationDTO): ReservationCreateREQ => {
  const funeralService = r.additionalInfo.isNotSellected
    ? {}
    : r.additionalInfo.funeralService.isActive
      ? {
          guardianAttendanceType: orUndefined(r.additionalInfo.funeralService.accompanny),
          address: r.additionalInfo.funeralService.address ?? undefined,
          addressDetail: r.additionalInfo.funeralService.detailedAddress ?? undefined,
        }
      : {};

  const additionalService = r.additionalInfo.isNotSellected
    ? {}
    : {
        note: `${r.additionalInfo.flowerBouquetService ? '꽃다발서비스' : ''} / ${r.additionalInfo.lucetteService ? '루세떼 (추모보석) 장례' : ''}`,
      };

  return {
    petName: r.petInfo.petName,
    petWeight: Number(r.petInfo.petWeight),
    petTypeId: Number(r.petInfo.petClassification),
    funeralDate: Number(dayjs(r.reserveInfo.date).startOf('day').valueOf()) || 0,
    funeralTime: r.reserveInfo.time,
    guardianName: r.guardianInfo.guardianName,
    guardianContact: r.guardianInfo.phoneNumber,
    guardianRequest: r.guardianInfo.message,
    numberOfVisitors: r.guardianInfo.numOfVisitor,
    ...funeralService,
    branchId: r.reserveInfo.branch,
    ...additionalService,
  };
};
export const reservationPetTypeRespToOption = (d: ReservationPetTypeRESP[]) => {
  return d.map((item) => ({
    text: item.name,
    label: item.name,
    value: item.id,
  }));
};

export const reservationRespToDto = (r: ReservationDetailRESP): ReservationDetailDTO => {
  return {
    branchName: orString(r.branch.name),
    date: numberOrZero(r.funeralInformation.funeralDate),
    time: orString(r.funeralInformation.funeralTime),
    petName: orString(r.petInformation.name),
    petWeight: numberOrZero(r.petInformation.weight),
    petType: orString(r.petInformation.petType.name),
    guardianName: orString(r.guardianInformation.name),
    guardianPhone: orString(r.guardianInformation.phoneNumber),
    numberOfVisitor: orString(r.funeralInformation.numberOfVisitors),
  };
};
