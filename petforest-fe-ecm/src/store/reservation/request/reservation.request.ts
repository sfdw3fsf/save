import { SERVICE_TRANSPORT_ACCOMPANIED_STATUS, VISTOR_NUMBER } from '@/utils/enums/reservation.enum';

export type ReservationTimeListREQ = {
  date: number;
  branchId: number;
  timezone: string;
};

export type ReservationTimeFilter = {
  date?: number;
  branchId?: number;
};

export type ReservationCreateREQ = {
  petName: string;
  petWeight: number;
  petTypeId: number;
  funeralDate: number;
  funeralTime: string;
  guardianName: string;
  guardianContact: string;
  guardianRequest: string;
  guardianAttendanceType?: SERVICE_TRANSPORT_ACCOMPANIED_STATUS;
  numberOfVisitors?: VISTOR_NUMBER;
  address?: string;
  addressDetail?: string;
  branchId: number;
  note?: string;
};

export type ReservationInquiryREQ = {
  guardianName: string;
  phoneNumber: string;
};
