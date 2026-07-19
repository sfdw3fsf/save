import { PAYMENT_METHOD } from '@/utils/enums/common.enum';
import { PLACE_STATUS } from '@/utils/enums/ossuary/ossuary-room.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { Dayjs } from 'dayjs';

export type OssuaryListDTO = {
  key: number;
  id: number;
  number: number;
  room: string;
  status: ENUM_STATUS;
  infoIds: number[];
  placeId?: number;
  registrationDate: PLACE_STATUS;
  expirationDate: string;
  remainingDays: number;
  receiptNumber: string;
  childName: string;
  guardianName: string;
  contact: string;
  emergencyContact: string;
  address: string;
  reservationNumber: number;
  reservationId: number;
  contractNotificationStatus: ENUM_STATUS;
  expirationNotificationStatus: ENUM_STATUS;
  extensionDepositNotificationStatus: ENUM_STATUS;
  extensionConfirmationNotificationStatus: ENUM_STATUS;
  note: string;
};

export type OssuaryPlaceListDTO = {
  key: number;
  id: number;
  registrationDate: string;
  expirationDate: string;
  remainingDays: number;
  receiptNumber: string;
  childName: string;
  guardianName: string;
  contact: string;
  emergencyContact: string;
  address: string;
  reservationNumber: string;
  reservationId: number;
  note: string;
};

export type UsageInformationFormDTO = {
  reservationNumber: string | null;
  placeId: number;
  room: string;
  registrationDate: Dayjs | null;
  expirationDate: Dayjs | null;
  usageCode: string;
  childName: string;
  guardianName: string;
  contact: string;
  emergencyContact: string;
  address: string;
  addressDetail: string;
  note: string;
  receiptNumber: string;
};

export type OssuaryDetailUsageHistoryDTO = {
  key: number;
  id: number;
  place: string;
  room: string;
  registrationDate: string;
  expirationDate: string;
  remainingDays: string;
  memo: string;
};

export type OssuaryDetailSettlementHistoryDTO = {
  key: number;
  id: number;
  expirationDate: string;
  registrationDate: string;
  note: string | null;
  amount: number;
  settlementMethod: PAYMENT_METHOD | null;
  settlementStatus: ENUM_STATUS;
};
