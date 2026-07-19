import { LUCETTE_CATEGORY } from '@/utils/enums/funeral/lucette-mngt.enums';
import {
  DETAIL_FUNERAL_RESERVATION_OPTIONS,
  DETAIL_FUNERAL_STATUS_OPTIONS,
  DETAIL_GUARDIAN_BOOKING_OPTIONS,
  DETAIL_GUARDIAN_VISIT_OPTIONS,
  DETAIL_PAYMENT_METHODS_OPTIONS,
  DETAIL_PET_GENDER_OPTIONS,
  DETAIL_PET_REGISTATION_OPTIONS,
  DETAIL_SERVICE_ATTENDANCE_OPTIONS,
  DETAIL_SERVICE_NEW_AUDITION_COLOR_OPTIONS,
  DETAIL_SERVICE_NUMBER_ATTENDANCE_OPTIONS,
  DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS,
  DETAIL_VISTOR_NUMBER_OPTIONS,
} from '@/utils/enums/funeral/reservation-mngt.enums';
import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';
import { Nil } from '@/utils/types/utils.type';

export type ReservationListFilter = FilterREQ;

export type ReservationListREQ = { isDeleted?: boolean } & ReservationListFilter & PagingREQ;

export type ReservationCreateREQ = {
  guardianAttendanceType?: DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS;
  date?: number;
};

export type ReservationUpdatePetInformationREQ = {
  petName?: string;
  registrationNumber?: string;
  petTypeId?: number;
  petBreedId?: Nil<number>;
  weight?: number;
  dateOfBirth?: number;
  endDate?: number;
  petImageId?: number;
  age?: number;
  gender?: DETAIL_PET_GENDER_OPTIONS;
  status?: DETAIL_PET_REGISTATION_OPTIONS;
};

export type ReservationUpdateFunaralInformationREQ = {
  isMarked?: boolean;
  funeralTime?: string;
  vehicleTime?: string;
  funeralDate?: number;
  deliveryDate?: number;
  progressNote?: string;
  reservationStatus?: DETAIL_FUNERAL_RESERVATION_OPTIONS;
  processStatus?: DETAIL_FUNERAL_STATUS_OPTIONS;
  numberOfVisitors?: DETAIL_VISTOR_NUMBER_OPTIONS;
  guardianAttendanceType?: DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS;
  numberOfAttendances?: Nil<DETAIL_SERVICE_NUMBER_ATTENDANCE_OPTIONS>;
  observationType?: DETAIL_SERVICE_ATTENDANCE_OPTIONS;
  funeralNote?: string;
  address?: string;
  addressDetail?: string;
  progressId?: number;
  subProgressId?: Nil<number>;
  waitingRoomId?: number;
  managerId?: number;
};

export type ReservationUpdateREQ = {
  reservationNote?: string;
  leaderId?: number;
};

export type ReservationUpdateGuardianInformationREQ = {
  guardianName?: string;
  phoneNumber?: string;
  email?: string;
  address?: string;
  addressDetail?: string;
  request?: string;
  referralSource?: DETAIL_GUARDIAN_VISIT_OPTIONS;
  reservationSource?: DETAIL_GUARDIAN_BOOKING_OPTIONS;
  provinceName?: string;
  districtName?: string;
};

export type ReservationUpdatePartnerInformationREQ = {
  partnerNote?: string;
  partnerId?: number;
};

export type ReservationUpdatePaymentInformationREQ = {
  base: number;
  goods: number;
  additional: number;
  discounted: number;
  cash: number;
  bank: number;
  card: number;
  coupon: number;
  transportation: number;
  overweight: number;
  obituary: number;
  temporaryPlacement: number;
  carbonPlate: number;
  other: number;
  paymentNote?: string;
  method?: DETAIL_PAYMENT_METHODS_OPTIONS;
  reasonDiscounted?: string;
  remaining?: number;
  invoiceNote?: string;
};

export type ReservationUpdateLucetteInformationREQ = {
  processDate?: number;
  processTime?: string;
  materials?: { materialId: number; quantity: number }[];
  isDelete?: boolean;
  process?: LUCETTE_CATEGORY;
};

export type ReservationUpdateHospitalInformationREQ = {
  amount?: number;
  hospitalEntitlementId?: number;
  hospitalId?: number;
};

export type ReservationUpdateStockOrdersREQ = {
  columbarium: number;
  home: number;
  monite: number;
  emotional: number;
  sophisticated: number;
  frame: number;
  material?: DETAIL_SERVICE_NEW_AUDITION_COLOR_OPTIONS;
  isDelete: boolean;
};

export type ReservationUpdatePhotoREQ = {
  photoIds?: number[];
};

export type ReservationUpdateColumbariumREQ = {
  emergencyContact?: string;
  columbariumNote?: string;
  columbariumPlaceId?: number;
  registrationDate?: number;
  expirationDate?: number;
  isDelete?: boolean;
};

export type ReservationUpdateInventoriesREQ = {
  funeralServiceId?: number;
  materials?: { materialId: number; quantity: number; amount: number; isDefault: boolean }[];
};

export type ReservationUpdatePhotosREQ = {
  photoIds?: number[];
};

export type ReservationCreateInventoriesByFuneralServiceREQ = {
  funeralServiceId: number;
};

export type ReservationUpdateSettlementDetailREQ = {
  reservationSettlements?: {
    id?: number;
    settlementAmount?: number;
    reservationMethod?: DETAIL_PAYMENT_METHODS_OPTIONS | null;
    settlementDate?: number;
    memo?: string;
  }[];
};

export type ReservationUpdateSettlementREQ = {
  basicAmount: number;
  productAmount: number;
  transport: number;
  overweight: number;
  columbarium: number;
  temporaryPlacement: number;
  carbonPlate: number;
  otherAdditionalCost: number;
  reasonOfDiscount: string;
  discountAmount: number;
};

export type ReservationUpdateDetailREQ = {
  reservation?: ReservationUpdateREQ;
  columbariumInformation?: ReservationUpdateColumbariumREQ;
  hospitalInformation?: ReservationUpdateHospitalInformationREQ;
  partnerInformation?: ReservationUpdatePartnerInformationREQ;
  petInformation?: ReservationUpdatePetInformationREQ;
  guardianInformation?: ReservationUpdateGuardianInformationREQ;
  lucetteInformation?: ReservationUpdateLucetteInformationREQ;
  reservationInventory?: ReservationUpdateInventoriesREQ;
  stockOrder?: ReservationUpdateStockOrdersREQ;
  reservationSettlement?: ReservationUpdateSettlementDetailREQ;
  funeralInformation?: ReservationUpdateFunaralInformationREQ;
  reservationPhoto?: ReservationUpdatePhotosREQ;
  settlement?: ReservationUpdateSettlementREQ;
};
