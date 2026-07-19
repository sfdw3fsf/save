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
  DETAIL_VISTOR_NUMBER_OPTIONS,
} from '@/utils/enums/funeral/reservation-mngt.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { Nil } from '@/utils/types/utils.type';

type GenaralDetailInformation = {
  id: number;
  name: Nil<string>;
  ordinal: Nil<string>;
};

export type ReservationInventoriesRESP = {
  id: number;
  quantity: number;
  amount: number;
  registerDate: null | string;
  isDefault: boolean;
  material: {
    id: number;
    name: string;
    type: Nil<string>;
  };
  reservation: Nil<{ name: string; id: number }>;
};

export type ReservationListRESP = {
  id: number;
  code: string;
  branch: { id: number; name: string };
  note: Nil<string>;
  notificationStatus: ENUM_STATUS;
  leader: Nil<{
    id: number;
    fullName: string;
  }>;
  petInformation: {
    name: Nil<string>;
    petBreed: Nil<{ id: number; name: string }>;
    weight: Nil<number>;
    petType: Nil<{ id: number; name: string }>;
    petImage: Nil<{
      id: number;
      key: string;
    }>;
    dateOfBirth: Nil<string>;
    endDate: Nil<string>;
    registrationNumber: Nil<string>;
    age: Nil<number>;
    status: DETAIL_PET_REGISTATION_OPTIONS;
    gender: DETAIL_PET_GENDER_OPTIONS;
  };
  guardianInformation: Nil<{
    id: 3;
    name: string;
    phoneNumber: string;
    address: string;
    addressDetail: string;
    email: string;
    request: string;
    referralSource: DETAIL_GUARDIAN_VISIT_OPTIONS;
    reservationSource: DETAIL_GUARDIAN_BOOKING_OPTIONS;
  }>;
  funeralInformation: {
    deliveryDate: string | null;
    funeralDate: string;
    funeralTime: Nil<string>;
    progressNote: Nil<string>;
    processStatus: DETAIL_FUNERAL_STATUS_OPTIONS;
    progress: Nil<GenaralDetailInformation>;
    subProgresses: Nil<GenaralDetailInformation>;
    waitingRoom: Nil<GenaralDetailInformation>;
    funeralService: Nil<{ name: string; type: string; id: number; color: string }>;
    manager: Nil<{ fullName: string; id: number }>;
    id: number;
    isMarked: boolean;
    vehicleTime: Nil<string>;
    reservationStatus: Nil<DETAIL_FUNERAL_RESERVATION_OPTIONS>;
    numberOfVisitors: Nil<DETAIL_VISTOR_NUMBER_OPTIONS>;
    guardianAttendanceType: Nil<DETAIL_SERVICE_ATTENDANCE_OPTIONS>;
    numberOfAttendances: Nil<DETAIL_SERVICE_NUMBER_ATTENDANCE_OPTIONS>;
    observationType: Nil<DETAIL_SERVICE_ATTENDANCE_OPTIONS>;
    note: Nil<string>;
    address: string;
    addressDetail: string;
  };
  lucetteInformation: Nil<{
    jewelry: string;
    status: ENUM_STATUS;
    process: LUCETTE_CATEGORY;
    processDate: number;
    processTime: string;
    lucetteOrders: {
      quantity: number;
      lucetteMaterial: {
        id: number;
        name: string;
      };
    }[];
  }>;
  columbariumInformation: Nil<{
    id: number;
    registrationDate: string;
    expirationDate: string;
    emergencyContact: string;
    remainingDays: number;
    reservation: Nil<{ name: string; id: number }>;
    columbariumPlace: {
      id: number;
      number: number;
      columbariumRoom: {
        id: number;
        name: string;
      };
    };
  }>;
  obituaryInformation: Nil<{
    link: string;
    coupons: {
      id: number;
      code: string;
      amount: number;
      purchaser: string;
      purchaserContact: string;
    }[];
    files: string[];
  }>;
  partnerInformation: Nil<{
    note: string;
    partner: Nil<{
      id: number;
      name: string;
    }>;
  }>;
  paymentInformation: Nil<{
    base: number;
    goods: number;
    additional: number;
    discounted: number;
    total: number;
    cash: number;
    bank: number;
    card: number;
    coupon: number;
    note: string;
    method: DETAIL_PAYMENT_METHODS_OPTIONS;
    transportation: number;
    overweight: number;
    obituary: number;
    temporaryPlacement: number;
    carbonPlate: number;
    other: number;
    reasonDiscounted: null;
  }>;
  stockOrder: Nil<{
    id: number;
    columbarium: number;
    home: number;
    emotional: number;
    sophisticated: number;
    frame: number;
    material?: DETAIL_SERVICE_NEW_AUDITION_COLOR_OPTIONS;
    reservation: Nil<{ name: string; id: number }>;
  }>;
  reservationPhoto: Nil<{
    files: {
      id: number;
      key: string;
      fileName: string;
      url: string;
    }[];
  }>;
  inventories: ReservationInventoriesRESP[];
  hospitalInformation: Nil<{
    amount: number;
    hospitalEntitlement: Nil<{ name: string; id: number }>;
    hospital: Nil<{ name: string; id: number; createdAt: Nil<number> }>;
  }>;
  settlement: Nil<{
    id: number;
    status: ENUM_STATUS;
    funeralDate: number;
    funeralId: number;
    funeralCode: Nil<string>;
    basicAmount: number;
    productAmount: number;
    additionalCost: {
      transport: number;
      overweight: number;
      columbarium: number;
      temporaryPlacement: number;
      carbonPlate: number;
      otherAdditionalCost: number;
    };
    reasonOfDiscount: Nil<string>;
    discountAmount: number;
    usageCoupon: number;
    remainingAmount: number;
    totalAmount: number;
    settlementMethod: {
      card: number;
      cash: number;
      accountAmount: number;
      prePaid: number;
    };
    invoiceMemo: null;
    memo: null;
    branchId: number;
  }>;
  reservationSettlements: {
    id: number;
    settlementAmount: number;
    settlementDate: number;
    reservationMethod: DETAIL_PAYMENT_METHODS_OPTIONS | null;
    memo: string;
  }[];
};

export type ReservationPartnerRESP = {
  note: string;
  partner: {
    name: string;
    note: string;
  };
};
