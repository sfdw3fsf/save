import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { ENUM_STATUS, ENUM_STATUS_INACTIVE } from '@/utils/enums/status.enum';
import { orUndefined, parseStringToNumber } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import { isNil } from 'lodash';
import { numericFormatter } from 'react-number-format';
import { LucetteDetailDTO, LucetteListDTO, LucetteScheduleListDTO, LucetteSummaryDTO } from './dto/lucette.dto';
import { LucetteListFilter, LucetteListREQ, LucetteScheduleInput, LucetteScheduleREQ } from './request/lucette.request';
import { LucetteDetailRESP, LucetteListRESP, LucetteScheduleListRESP, LucetteSummaryRESP } from './response/lucette.response';

export const lucetteListParamsToFilter = (
  searchParams: URLSearchParams,
  defaultFilter?: LucetteListFilter,
): LucetteListFilter => {
  const { start, end } = {
    start: parseStringToNumber(searchParams.get('start')),
    end: parseStringToNumber(searchParams.get('end')),
  };

  let filter;
  if (start && end) {
    filter = JSON.stringify({
      'reservation.funeralInformation.funeralDate.gte': start,
      'reservation.funeralInformation.funeralDate.lte': end,
    });
  }

  return {
    filter: orUndefined(filter || defaultFilter?.filter),
    search: orUndefined(searchParams.get('search') || defaultFilter?.search),
    sortOrder: orUndefined(searchParams.get('sortOrder') || defaultFilter?.sortOrder),
    sortBy: orUndefined(searchParams.get('sortBy') || defaultFilter?.sortBy),
  };
};

export const lucetteListRespToDto = (resp: LucetteListRESP): LucetteListDTO => {
  const { reservation, ...rest } = resp;
  const dto: LucetteListDTO = {
    ...rest,
    key: resp.id,
    status: resp.status as ENUM_STATUS,
    lucetteOrder: (resp.lucetteOrders ?? []).reduce(
      (acc, cur) => {
        acc[cur.lucetteMaterialId] = cur.quantity;
        return acc;
      },
      {} as Record<number, number>,
    ),
    boneQuantity: orUndefined(resp.boneQuantity),
    note: orUndefined(resp.note),
    notificationStatus: resp.notificationStatus as ENUM_STATUS,
    processDate: resp.processDate ? dayjs(+resp.processDate) : null,
    processTime: resp.processTime ? dayjs(resp.processTime, DATE_FORMAT.TIME.PICKER_HOUR_MIN) : null,
    process: orUndefined(resp.process),
    managerId: resp.manager?.id,
    managerName: resp.manager?.fullName,
    reservationCode: resp.reservation.code,
    funeralDate: reservation.funeralInformation.funeralDate
      ? dayjs(+reservation.funeralInformation.funeralDate).format(DATE_FORMAT.DATE.HYPHEN)
      : undefined,
    petName: orUndefined(reservation.petInformation.name),
    petWeight: orUndefined(reservation.petInformation.weight),
    petBreed: reservation.petInformation.petBreed?.name,
    petBreedId: reservation.petInformation.petBreed?.id,
    guardianName: orUndefined(reservation.guardianInformation.name),
    guardianContact: orUndefined(reservation.guardianInformation.phoneNumber),
    guardianAddress: orUndefined(reservation.guardianInformation.address),
    guardianAddressDetail: orUndefined(reservation.guardianInformation.addressDetail),
    managerPosition: !!resp.manager?.position,
  };
  return dto;
};

export const lucetteListReqMapping = (req?: LucetteListREQ) => {
  if (!req) return;
  const sortBy = (() => {
    switch (req.sortBy) {
      case 'manager.fullName':
        return 'managerName';
      case 'reservation.petInformation.petBreed.name':
        return 'petBreed';
      default:
        return req.sortBy;
    }
  })();
  return { ...req, sortBy };
};

export const lucetteDetailRespToDto = (resp: LucetteDetailRESP): LucetteDetailDTO => {
  const dto: LucetteDetailDTO = {
    ...resp,
    deliveryStatus: resp.deliveryStatus as ENUM_STATUS,
    tycheDeliveryDate: resp.tycheDeliveryDate ? dayjs(+resp.tycheDeliveryDate) : null,
    petForestIndiaDate: resp.petForestIndiaDate ? dayjs(+resp.petForestIndiaDate) : null,
  };
  return dto;
};

export const lucetteSummaryRespToDto = (resp: LucetteSummaryRESP): LucetteSummaryDTO => {
  const { total, ...detail } = resp;
  const dto: LucetteSummaryDTO = {
    total,
    detail: {
      sameDay: detail.waitingForDay,
      schedulePending: detail.waitingForSchedule,
      processPending: detail.waitingForProcess,
      standalone: detail.waitingForExclusive,
    },
  };
  return dto;
};

export const lucetteScheduleInputToReq = (input: LucetteScheduleInput): LucetteScheduleREQ => {
  const req: LucetteScheduleREQ = {
    status: `[${input.status?.toString()}]`,
    startDate: input.startDate.valueOf(),
    endDate: input.endDate.valueOf(),
  };
  return req;
};

export const lucetteScheduleListRespToDto = (
  respList: LucetteScheduleListRESP[],
  month: dayjs.Dayjs[],
): LucetteScheduleListDTO[] => {
  return respList.flatMap((resp) => {
    const { records } = resp;

    const dtoList: LucetteScheduleListDTO[] = records.map((record) => {
      const petWeight = record.reservation.petInformation?.weight;
      let formattedWeight;
      if (!isNil(petWeight)) {
        formattedWeight = `${numericFormatter(petWeight.toString(), { thousandSeparator: true })}kg`;
      }

      const funeralDate = record.reservation?.funeralInformation?.funeralDate;
      let formattedFuneralDate;
      if (!isNil(funeralDate)) {
        formattedFuneralDate = `<${dayjs(Number(funeralDate)).format(DATE_FORMAT.DATE.DATE_HYPHEN)}>`;
      }

      const type =
        record.status && record.processDate
          ? dayjs(+record.processDate).isAfter(month[0]) && dayjs(Number(+record.processDate)).isBefore(month[1])
            ? record.status
            : ENUM_STATUS_INACTIVE[record.status]
          : '';

      const date = record.processDate ? dayjs(+record.processDate).format(DATE_FORMAT.DATE_TIME_SECOND.ISO8601) : '';

      const dto: LucetteScheduleListDTO = {
        id: record.id.toString(),
        calendarId: type,
        category: 'allday',
        title: [record.processTime, record.reservation.petInformation?.name, formattedWeight, formattedFuneralDate]
          .filter(Boolean)
          .join(' '),
        start: date,
        end: date,
        isReadOnly: true,
      };

      return dto;
    });

    return dtoList;
  });
};
