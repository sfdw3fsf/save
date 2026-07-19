import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { orUndefined, parseStringToNumber } from '@/utils/parser/io.parser';
import { OptionItem } from '@/utils/types/option.type';
import dayjs from 'dayjs';
import { ReservationListFilter } from '../reservation/request/reservation.request';
import { PntSettingDetailDTO, PntSettingListDTO } from './dto/pnt-setting.dto';
import { ReservationPhotoDetailDTO, ReservationPhotoListDTO } from './dto/reservation-photo.dto';
import { ReservationPhotoListREQ } from './request/reservation-photo.resquest';
import { PntSettingDetailRESP, PntSettingListRESP } from './response/pnt-setting.response';
import { ReservationPhotoDetailRESP, ReservationPhotoListRESP } from './response/reservation-photo.response';

export const reservationPhotoListParamsToFilter = (
  searchParams: URLSearchParams,
  defaultFilter?: ReservationListFilter,
): ReservationListFilter => {
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

export const reservationPhotoListRespToOption = (resp: ReservationPhotoListRESP): OptionItem<number> => {
  const converter = (arr: any[], separator: string = ' ') => arr.map((i: any) => (i?.toString() ? i : '-')).join(separator);

  const funeralDatetime = converter([
    resp.reservation?.funeralInformation?.funeralDate
      ? dayjs(Number(resp.reservation?.funeralInformation?.funeralDate)).format(DATE_FORMAT.DATE.HYPHEN)
      : '',
    resp.reservation?.funeralInformation?.funeralTime,
  ]);

  const funeralNumber = converter([[resp.reservation?.code]]);

  const info = converter(
    [
      resp.reservation?.petInformation?.petBreed?.name,
      resp.reservation?.petInformation?.name,
      resp.reservation?.guardianInformation?.name,
    ],
    '｜',
  );

  return {
    value: resp.id,
    label: converter([`[${funeralDatetime}]`, `[${funeralNumber}]`, info]),
  };
};

export const reservationPhotoListRespToDto = (resp: ReservationPhotoListRESP): ReservationPhotoListDTO => {
  return {
    id: resp?.id,
    key: resp?.id,
    files: resp?.files?.map((file) => ({
      id: file?.id,
      key: file?.key,
      fileName: orUndefined(file?.fileName),
      usedInMemorialRoom: Boolean(file?.usedInMemorialRoom),
      url: process.env.NEXT_PUBLIC_S3_PREFIX + file?.key,
    })),
    reservation: resp?.reservation
      ? {
          code: orUndefined(resp?.reservation?.code),
          leaderId: resp?.reservation?.leader?.id,
          leaderName: orUndefined(resp?.reservation?.leader?.fullName),
          funeralDate: resp?.reservation?.funeralInformation?.funeralDate
            ? dayjs(+resp?.reservation?.funeralInformation?.funeralDate)
            : null,
          funeralTime: orUndefined(resp?.reservation?.funeralInformation?.funeralTime),
          petName: orUndefined(resp?.reservation?.petInformation?.name),
          petBreed: {
            id: resp?.reservation?.petInformation?.petBreed?.id,
            name: orUndefined(resp?.reservation?.petInformation?.petBreed?.name),
          },
          guardianName: orUndefined(resp?.reservation?.guardianInformation?.name),
          guardianPhone: orUndefined(resp?.reservation?.guardianInformation?.phoneNumber),
        }
      : undefined,
  };
};

export const reservationPhotoDetailRespToDto = (resp: ReservationPhotoDetailRESP): ReservationPhotoDetailDTO => {
  return {
    id: resp?.id,
    files: resp?.files?.map((file) => ({
      id: file?.id,
      key: file?.key,
      fileName: orUndefined(file?.fileName),
      usedInMemorialRoom: Boolean(file?.usedInMemorialRoom),
      url: process.env.NEXT_PUBLIC_S3_PREFIX + file?.key,
    })),
    reservation: resp?.reservation
      ? {
          code: orUndefined(resp?.reservation?.code),
          leaderId: resp?.reservation?.leader?.id,
          leaderName: orUndefined(resp?.reservation?.leader?.fullName),
          funeralDate: resp?.reservation?.funeralInformation?.funeralDate
            ? dayjs(+resp?.reservation?.funeralInformation?.funeralDate)
            : null,
          funeralTime: orUndefined(resp?.reservation?.funeralInformation?.funeralTime),
          petName: orUndefined(resp?.reservation?.petInformation?.name),
          petBreed: {
            id: resp?.reservation?.petInformation?.petBreed?.id,
            name: orUndefined(resp?.reservation?.petInformation?.petBreed?.name),
          },
          guardianName: orUndefined(resp?.reservation?.guardianInformation?.name),
          guardianPhone: orUndefined(resp?.reservation?.guardianInformation?.phoneNumber),
        }
      : undefined,
  };
};

export const reservationPhotoListReqMapping = (req?: ReservationPhotoListREQ) => {
  if (!req) return;
  const sortBy = (() => {
    switch (req.sortBy) {
      case 'reservation.leader.fullName':
        return 'managerName';
      default:
        return req.sortBy;
    }
  })();
  return { ...req, sortBy };
};

// pnt-setting

export const pntSettingListRespToDto = (resp: PntSettingListRESP): PntSettingListDTO => {
  const { reservation, ...rest } = resp;
  let dto: PntSettingListDTO = {
    id: rest.id,
    key: rest.id,
    name: orUndefined(rest.name),
  };
  if (!reservation) return dto;
  const { funeralInformation, petInformation, guardianInformation, reservationPhoto } = reservation;
  dto = {
    ...dto,
    reservation: {
      id: reservation.id,
      key: reservation.id,
      code: orUndefined(reservation.code),
      funeralDate: funeralInformation?.funeralDate ? dayjs(+funeralInformation?.funeralDate) : null,
      funeralTime: orUndefined(funeralInformation?.funeralTime),
      petName: orUndefined(petInformation?.name),
      petBreed: petInformation?.petBreed
        ? { id: petInformation.petBreed.id, name: orUndefined(petInformation.petBreed.name) }
        : undefined,
      petImage: petInformation?.petImage
        ? {
            id: petInformation.petImage.id,
            key: petInformation.petImage.key,
            fileName: orUndefined(petInformation.petImage.fileName),
            url: process.env.NEXT_PUBLIC_S3_PREFIX + petInformation.petImage.key,
          }
        : undefined,
      guardianName: orUndefined(guardianInformation?.name),
      files: reservationPhoto?.files?.map((file) => ({
        id: file?.id,
        key: file?.key,
        fileName: orUndefined(file?.fileName),
        usedInMemorialRoom: Boolean(file?.usedInMemorialRoom),
        url: process.env.NEXT_PUBLIC_S3_PREFIX + file?.key,
      })),
    },
  };
  return dto;
};

export const pntSettingDetailRespToDto = (resp: PntSettingDetailRESP): PntSettingDetailDTO => {
  const { reservation, ...rest } = resp;
  let dto: PntSettingDetailDTO = {
    id: rest.id,
    key: rest.id,
    name: orUndefined(rest.name),
  };
  if (!reservation) return dto;
  const { petInformation, guardianInformation, funeralInformation, reservationPhoto } = reservation;
  dto = {
    ...dto,
    reservation: {
      id: reservation.id,
      key: reservation.id,
      code: orUndefined(reservation.code),
      funeralDate: funeralInformation?.funeralDate ? dayjs(+funeralInformation?.funeralDate) : null,
      funeralTime: funeralInformation?.funeralTime
        ? dayjs(funeralInformation?.funeralTime, DATE_FORMAT.TIME.PICKER_HOUR_MIN)
        : null,
      petName: orUndefined(petInformation?.name),
      petBreed: petInformation?.petBreed
        ? { id: petInformation.petBreed.id, name: orUndefined(petInformation.petBreed.name) }
        : undefined,
      guardianName: orUndefined(guardianInformation?.name),
      files: reservationPhoto?.files?.map((file) => ({
        id: file.id,
        key: file.key,
        fileName: orUndefined(file.fileName),
        usedInMemorialRoom: Boolean(file.usedInMemorialRoom),
        url: process.env.NEXT_PUBLIC_S3_PREFIX + file.key,
      })),
    },
  };
  return dto;
};
