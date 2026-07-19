import { PAYMENT_METHOD } from '@/utils/enums/common.enum';
import { PLACE_STATUS } from '@/utils/enums/ossuary/ossuary-room.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { Nil } from '@/utils/types/utils.type';

export type OssuaryPlaceDetailRESP = {
  id: number;
  registrationDate: string;
  expirationDate: string;
  childName: Nil<string>;
  guardianName: Nil<string>;
  contact: Nil<string>;
  address: Nil<string>;
  addressDetail: Nil<string>;
  emergencyContact: Nil<string>;
  note: Nil<string>;
  receiptNumber: Nil<string>;
  remainingDays: Nil<number>;
  contractNotificationStatus: ENUM_STATUS;
  expirationNotificationStatus: ENUM_STATUS;
  extensionDepositNotificationStatus: ENUM_STATUS;
  extensionConfirmationNotificationStatus: ENUM_STATUS;
  reservation: Nil<{
    id: number;
    code: number;
  }>;
  columbariumPlace: Nil<{
    id: number;
    number: number;
    columbariumRoom: {
      id: number;
      name: string;
    };
  }>;
};

export type OssuaryEntireListRESP = {
  id: number;
  number: Nil<number>;
  status: PLACE_STATUS;
  remainingDays: Nil<number>;
  columbariumRoom: Nil<{ id: number; name: string }>;
  columbariumInformations: OssuaryPlaceDetailRESP[];
};

export type OssuaryListRESP = {
  placeId: number;
  placeNumber: Nil<number>;
  status: PLACE_STATUS;
  roomName: Nil<string>;
  roomId: Nil<number>;
  registrationDate: Nil<string>;
  expirationDate: Nil<string>;
  remainingDays: Nil<number>;
  receiptNumber: Nil<string>;
  childName: Nil<string>;
  guardianName: Nil<string>;
  contact: Nil<string>;
  emergencyContact: Nil<string>;
  informationId: Nil<number>;
  address: Nil<string>;
  funeralCode: Nil<string>;
  reservationId: Nil<number>;
  contractNotificationStatus: ENUM_STATUS;
  expirationNotificationStatus: ENUM_STATUS;
  extensionDepositNotificationStatus: ENUM_STATUS;
  extensionConfirmationNotificationStatus: ENUM_STATUS;
  note: Nil<string>;
};

export type OssuaryEntireSummariesRESP = {
  currentOccupied: number;
  admissionsDuringPeriod: number;
  expirationDuringPeriod: number;
  currentOvernightStays: number;
  total: number;
};

export type ColumbariumPlacesDTO = {
  id: number;
  number: string;
  status: PLACE_STATUS;
  columbariumInformations: {
    expirationDate: string;
    remainingDays: number;
    reservation: Nil<{
      id: number;
      code: number;
    }>;
    columbariumPlace: Nil<{
      id: number;
      number: number;
    }>;
  }[];
  remainingDays: number;
  columbariumRoom: Nil<{
    id: number;
    name: string;
  }>;
};

export type OssuaryRoomDetailRESP = {
  id: number;
  name: string;
  columbariumPlaces: ColumbariumPlacesDTO[];
};

export type OssuaryPlaceDetailStatusRESP = {
  status: PLACE_STATUS;
};

export type OssuaryDetailUsageHistoryRESP = {
  id: number;
  expirationDate: string;
  registrationDate: string;
  note: string | null;
  remainingDays: number;
  columbariumPlace: {
    id: number;
    number: number;
    columbariumRoom: {
      id: number;
      name: string;
    };
  };
};

export type OssuaryDetailSettlementHistoryRESP = {
  id: number;
  expirationDate: string;
  registrationDate: string;
  note: string | null;
  amount: number;
  settlementMethod: PAYMENT_METHOD | null;
  settlementStatus: ENUM_STATUS;
};
