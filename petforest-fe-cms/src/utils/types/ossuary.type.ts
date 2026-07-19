import { Dayjs } from 'dayjs';
import { PLACE_STATUS } from '../enums/ossuary/ossuary-room.enum';
import { Address } from './common.type';

export type UsageInformationFormType = {
  funeralNumber: string | null;
  category: string;
  room: string;
  startDate: Dayjs;
  expireDate: Dayjs;
  usageCode: string;
  petName: string;
  guardian: string;
  contact: string;
  subContact: string;
  address: Address;
  note: string;
};

export type CurrentPlaceValue = {
  room: string;
  place?: string;
  placeId?: number;
};
export type PlacesRoomInformation = {
  number: number;
  remainingDays: number;
  columbariumInformations: string[];
  status: PLACE_STATUS;
  id: number;
};
