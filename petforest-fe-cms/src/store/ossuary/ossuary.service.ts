import { parseTimestampToDate, parseTimeStampToDayjs } from '@/utils/parser/date-time.parser';
import { orString, orUndefined } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import { isNil } from 'lodash';
import {
  OssuaryDetailSettlementHistoryDTO,
  OssuaryDetailUsageHistoryDTO,
  OssuaryListDTO,
  OssuaryPlaceListDTO,
  UsageInformationFormDTO,
} from './dto/ossuary.dto';
import { OssuaryInformationCreateUpdateREQ, OssuaryListFilter } from './request/ossuary.request';
import {
  ColumbariumPlacesDTO,
  OssuaryDetailSettlementHistoryRESP,
  OssuaryDetailUsageHistoryRESP,
  OssuaryEntireListRESP,
  OssuaryListRESP,
  OssuaryPlaceDetailRESP,
  OssuaryRoomDetailRESP,
} from './response/ossuary.response';

export const OssuaryInitialValue = {
  reservationNumber: '',
  usageCode: '',
  childName: '',
  guardianName: '',
  contact: '',
  emergencyContact: '',
  address: '',
  addressDetail: '',
  note: '',
  registrationDate: dayjs(),
  expirationDate: dayjs().add(1, 'year').subtract(1, 'day'),
};

export const ossuaryEntireListReqMapping = (req?: OssuaryListFilter) => {
  if (!req) return;
  const sortBy = (() => {
    switch (req.sortBy) {
      case 'room':
        return 'name';
      case 'reservationNumber':
        return 'code';
      default:
        return req.sortBy;
    }
  })();
  return { ...req, sortBy };
};

export const ossuaryEntireRespToDto = (r: OssuaryEntireListRESP): OssuaryListDTO => {
  const columbariumInfo = r.columbariumInformations?.[0] || {};

  return {
    key: r.id,
    id: r.id,
    number: orUndefined(r.number),
    room: orString(r.columbariumRoom?.name),
    status: orUndefined(r.status),
    infoIds: r.columbariumInformations?.map((i) => i.id) || [],

    registrationDate: orUndefined(columbariumInfo.registrationDate),
    expirationDate: orUndefined(columbariumInfo.expirationDate),
    remainingDays: orUndefined(r.remainingDays),
    receiptNumber: orUndefined(columbariumInfo.receiptNumber),
    childName: orString(columbariumInfo.childName),
    guardianName: orString(columbariumInfo.guardianName),
    contact: orString(columbariumInfo.contact),
    emergencyContact: orString(columbariumInfo.emergencyContact),
    address: orString(columbariumInfo.address),
    reservationNumber: orUndefined(columbariumInfo.reservation?.code),
    reservationId: orUndefined(columbariumInfo.reservation?.id),
    contractNotificationStatus: orString(columbariumInfo.contractNotificationStatus),
    expirationNotificationStatus: orString(columbariumInfo.expirationNotificationStatus),
    extensionDepositNotificationStatus: orString(columbariumInfo.extensionDepositNotificationStatus),
    extensionConfirmationNotificationStatus: orString(columbariumInfo.extensionConfirmationNotificationStatus),
    note: orString(columbariumInfo.note),
  };
};

export const ossuaryListRespToDto = (r: OssuaryListRESP): OssuaryListDTO => {
  const dto: OssuaryListDTO = {
    key: r.informationId && r.informationId !== 0 ? r.informationId : r.placeId,
    id: r.informationId && r.informationId !== 0 ? r.informationId : r.placeId,
    number: orUndefined(Number(r.placeNumber)),
    room: orString(r.roomName),
    status: orUndefined(r.status),
    infoIds: [r.informationId].filter((id) => !isNil(id)),
    placeId: r.placeId,
    registrationDate: orUndefined(r.registrationDate),
    expirationDate: orUndefined(r.expirationDate),
    remainingDays: orUndefined(r.remainingDays),
    receiptNumber: orUndefined(r.receiptNumber),
    childName: orString(r.childName),
    guardianName: orString(r.guardianName),
    contact: orString(r.contact),
    emergencyContact: orString(r.emergencyContact),
    address: orString(r.address),
    reservationNumber: orUndefined(r.funeralCode),
    reservationId: orUndefined(r.reservationId),
    contractNotificationStatus: orString(r.contractNotificationStatus),
    expirationNotificationStatus: orString(r.expirationNotificationStatus),
    extensionDepositNotificationStatus: orString(r.extensionDepositNotificationStatus),
    extensionConfirmationNotificationStatus: orString(r.extensionConfirmationNotificationStatus),
    note: orString(r.note),
  };
  return dto;
};

export function ossuaryEntireListParamToFilter(searchParams: URLSearchParams): OssuaryListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as OssuaryListFilter;
}

export const ossuaryPlaceDetailRespToDto = (r: OssuaryPlaceDetailRESP): OssuaryPlaceListDTO => {
  return {
    key: r.id,
    id: r.id,
    registrationDate: parseTimestampToDate(r.registrationDate),
    expirationDate: parseTimestampToDate(r.expirationDate),
    remainingDays: orUndefined(r.remainingDays),
    receiptNumber: orUndefined(r.receiptNumber),
    childName: orString(r.childName),
    guardianName: orString(r.guardianName),
    contact: orString(r.contact),
    emergencyContact: orString(r.emergencyContact),
    address: orString(r.address),
    reservationNumber: orUndefined(r.reservation?.code),
    reservationId: orUndefined(r.reservation?.id),
    note: orString(r.note),
  };
};

export function ossuaryPlaceDetailListParamToFilter(searchParams: URLSearchParams): OssuaryListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    filter: orUndefined(searchParams.get('filter')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as OssuaryListFilter;
}

export const ossuaryPlaceDtoToCreateReq = (r: UsageInformationFormDTO): OssuaryInformationCreateUpdateREQ => {
  return {
    registrationDate: r.registrationDate?.valueOf(),
    expirationDate: r.expirationDate?.valueOf(),
    guardianName: r.guardianName,
    childName: r.childName,
    contact: r.contact,
    address: r.address,
    addressDetail: r.addressDetail,
    emergencyContact: r.emergencyContact,
    note: r.note,
    columbariumPlaceId: r.placeId,
  };
};

export const ossuaryPlaceInformationDetailRespToDto = (r: OssuaryPlaceDetailRESP): UsageInformationFormDTO => {
  return {
    usageCode: orUndefined(r.id),
    registrationDate: parseTimeStampToDayjs(r.registrationDate),
    expirationDate: parseTimeStampToDayjs(r.expirationDate),
    childName: orString(r.childName),
    guardianName: orString(r.guardianName),
    contact: orString(r.contact),
    emergencyContact: orString(r.emergencyContact),
    address: orString(r.address),
    addressDetail: orString(r.addressDetail),
    reservationNumber: orUndefined(r.reservation?.code),
    note: orString(r.note),
    placeId: orUndefined(r.columbariumPlace?.id),
    room: orUndefined(r.columbariumPlace?.columbariumRoom.name),
    receiptNumber: orString(r.receiptNumber),
  };
};

export const ossuaryPlaceCatergoryToOptions = (d: OssuaryRoomDetailRESP) => {
  return d.columbariumPlaces.map((item) => ({
    text: item.number,
    label: item.number,
    value: item.id,
  }));
};

export const ossuaryRoomNameToOptions = (d: OssuaryRoomDetailRESP[]) => {
  return d.map((item) => ({
    text: item.name,
    label: item.name,
    value: item.name,
  }));
};

export const ossuaryDetailUsageHistoryRespToDto = (r: OssuaryDetailUsageHistoryRESP): OssuaryDetailUsageHistoryDTO => {
  return {
    key: r.id,
    id: r.id,
    place: r.columbariumPlace.number.toString(),
    room: r.columbariumPlace.columbariumRoom.name,
    registrationDate: parseTimestampToDate(r.registrationDate),
    expirationDate: parseTimestampToDate(r.expirationDate),
    remainingDays: r.remainingDays.toString(),
    memo: orString(r.note),
  };
};

export const ossuaryDetailSettlementHistoryRespToDto = (
  r: OssuaryDetailSettlementHistoryRESP,
): OssuaryDetailSettlementHistoryDTO => {
  return {
    key: r.id,
    id: r.id,
    expirationDate: parseTimestampToDate(r.expirationDate),
    registrationDate: parseTimestampToDate(r.registrationDate),
    note: orString(r.note),
    amount: r.amount,
    settlementMethod: r.settlementMethod,
    settlementStatus: r.settlementStatus,
  };
};

export const ossuaryRoomDetailDataRespToDto = (r?: OssuaryRoomDetailRESP) => {
  const places = r
    ? Object.values(
        r.columbariumPlaces.reduce((groups, item) => {
          const floor = parseInt(item.number[0], 10);
          if (!groups[floor]) {
            groups[floor] = [];
          }
          groups[floor].push(item);
          return groups;
        }, [] as ColumbariumPlacesDTO[][]),
      )
    : [];

  const sortedPlaces = places
    .filter((group) => group !== undefined)
    .sort((a, b) => parseInt(b[0].number[0], 10) - parseInt(a[0].number[0], 10))
    .map((group) => group.sort((a, b) => parseInt(a.number, 10) - parseInt(b.number, 10)));

  return sortedPlaces;
};
