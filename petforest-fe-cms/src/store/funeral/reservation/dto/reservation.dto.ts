import { LUCETTE_CATEGORY } from '@/utils/enums/funeral/lucette-mngt.enums';
import {
  DETAIL_FUNERAL_RESERVATION_OPTIONS,
  DETAIL_FUNERAL_STATUS_OPTIONS,
  DETAIL_PAYMENT_METHODS_OPTIONS,
  DETAIL_PET_GENDER_OPTIONS,
  DETAIL_PET_REGISTATION_OPTIONS,
  DETAIL_SERVICE_ATTENDANCE_OPTIONS,
  DETAIL_SERVICE_NEW_AUDITION_COLOR_OPTIONS,
  DETAIL_SERVICE_NEW_AUDITION_TYPE_OPTIONS,
  DETAIL_SERVICE_NUMBER_ATTENDANCE_OPTIONS,
  DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS,
  DETAIL_VISTOR_NUMBER_OPTIONS,
} from '@/utils/enums/funeral/reservation-mngt.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { Nil } from '@/utils/types/utils.type';
import { UploadFile } from 'antd/lib';
import { Dayjs } from 'dayjs';

export type FuneralMNGTReservationListDTO = {
  key: number;
  id: number;
  code: string;
  color: string;
  funeralDate: number;
  deliveryDate: Dayjs | null;
  funeralTime: string;
  'funeralInformation.processStatus': DETAIL_FUNERAL_STATUS_OPTIONS;
  'funeralInformation.reservationStatus': DETAIL_FUNERAL_RESERVATION_OPTIONS;
  petWeight: number;
  petName: string;
  petType: number;
  progress: string;
  progressNote: string;
  funeralService: string;
  additionalServices: string;
  isRegisterPhoto: boolean;
  paymentMethod: PaymentMethodDTO;
  additionalCost: AdditionalCostDTO;
  notificationStatus: ENUM_STATUS;
  carService: {
    guardianAttendanceType: Nil<DETAIL_SERVICE_ATTENDANCE_OPTIONS>;
    managerName: string;
    vehicleTime: string;
  };
};

export type TransportDTO = {
  accompanimentStatus: DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS;
  numberPeople?: Nil<DETAIL_SERVICE_NUMBER_ATTENDANCE_OPTIONS>;
  manager: number;
  transportTime?: number;
  deliveryServiceAddress: string;
  deliveryServiceDetailAddress: string;
};

export type NewAdditionDTO = {
  isActive: boolean;
  color: DETAIL_SERVICE_NEW_AUDITION_COLOR_OPTIONS;
  typeMemorialTablet: NewAdditionTypeMemorialTabletDTO[];
};

export type LucetteDTO = {
  isActive: boolean;
  category?: LUCETTE_CATEGORY;
  processDate?: number;
  processTime?: string | number;
  status: ENUM_STATUS;
  materials: {
    materialId: number;
    quantity: number;
    name: string;
  }[];
};

export type ColumbariumDTO = {
  isActive: boolean;
  category?: string;
  roomNumber?: Nil<number>;
  date: (number | undefined)[];
  emergencyContact: string;
  roomName?: string;
};

export type SuppliesDTO = {
  inventoryId?: number;
  type: string;
  kind?: Nil<number>;
  quantity: number;
  amount: number;
  price?: number;
  isDefault?: boolean;
};

export type NewAdditionTypeMemorialTabletDTO = {
  type?: DETAIL_SERVICE_NEW_AUDITION_TYPE_OPTIONS;
  quantity: number;
};

export type CouponHistoryDTO = {
  paymentAmount: string;
  couponContactSender: string;
};

export type PetRegistrationDTO = {
  type: DETAIL_PET_REGISTATION_OPTIONS;
  id: string;
};

export type PetInfoDTO = {
  registration: PetRegistrationDTO;
  petName: string;
  type?: number;
  breed?: Nil<number>;
  weight: number;
  gender: DETAIL_PET_GENDER_OPTIONS;
  age?: number;
  dateBirth?: number;
  dateDeath?: number;
};

export type GuardianInfoDTO = {
  booking?: string;
  visit?: string;
  guardianName: string;
  phoneNumber: string;
  addressGeneral: string;
  addressDetail: string;
  email: string;
  request: string;
  provinceName?: string;
  districtName?: string;
};

export type FuneralInfoDTO = {
  reservation: DETAIL_FUNERAL_RESERVATION_OPTIONS;
  status?: DETAIL_FUNERAL_STATUS_OPTIONS;
  funeralDate: number;
  progress: number;
  subProgress?: Nil<number>;
  waitingRoom?: number;
  progressStatusNote: string;
  time: string;
  request: string;
  isActiveTransport: boolean;
  transport: TransportDTO;
  attendance: DETAIL_SERVICE_ATTENDANCE_OPTIONS;
  funeral: number;
  numberOfVisitors: DETAIL_VISTOR_NUMBER_OPTIONS;
};

export type ServiceInfoDTO = {
  supplies: SuppliesDTO[];
  newAdditon: NewAdditionDTO;
  lucette: LucetteDTO;
  columbarium: ColumbariumDTO;
};

export type AnnouncementsCouponInfoDTO = {
  announcement_web_link: string;
  coupon_history: CouponHistoryDTO[];
};

export type HospitalInfoDTO = {
  hospital_name?: number;
  settlement_method?: number;
  amount?: number;
};

export type PartnerCompanyDTO = {
  companyName: number;
  remarks: string;
};

export type AdditionalCostDTO = {
  transportService: number;
  overweight: number;
  columbarium: number;
  temporaryStorage: number;
  carbonPlate: number;
  miscellaneous: number;
};

export type PaymentMethodDTO = {
  baseAmount?: number;
  itemAmount?: number;
  additionalDiscountReason: string;
  additionalDiscountAmount: number;
  coupon: number;
  totalAmount?: number;
};

export type ReservationPhotoDTO = {
  attachments?: UploadFile<any>[];
};

export type SettlementDetailDTO = {
  id?: number;
  date?: number;
  settlementMethod: DETAIL_PAYMENT_METHODS_OPTIONS | null;
  amount: number;
  note: string;
};

export type ReservationDetailDTO = {
  code?: string;
  manager?: number;
  id: number;
  branch: string;
  reservationPhoto: ReservationPhotoDTO;
  petInfo: PetInfoDTO;
  guardianInfo: GuardianInfoDTO;
  funeralInfo: FuneralInfoDTO;
  serviceInfo: ServiceInfoDTO;
  announcementsCouponInfo: AnnouncementsCouponInfoDTO;
  hospital: HospitalInfoDTO;
  partnerCompany: PartnerCompanyDTO;
  settlementId: number;
  settlementDetail: SettlementDetailDTO[];
  additionalCost: AdditionalCostDTO;
  paymentMethod: PaymentMethodDTO;
};
