import { CALENDAR_STATUS_ENUMS } from '@/utils/enums/common.enums';
import { SALES_CALENDAR_EVENT_ID, SALES_CALENDAR_EVENT_INACTIVE } from '@/utils/enums/home/calendar.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { Dayjs } from 'dayjs';

export type RegionDTO = {
  value: number | null;
  label: string;
};

export type HospitalRegionDTO = RegionDTO & {
  children: RegionDTO[];
};

export type HospitalDTO = {
  key: number;
  id: number;
  name: string;
  region: string;
  isMarked: boolean;
  createdAt: string;
  isNewHospital: boolean;
  totalFuneral: string;
  totalSales: string;
  entitlementMethod?: number;
  hospitalBranches: number[];
};

export type HospitalDetailDTO = {
  id?: number;
  name: string;
  address: string;
  detailAddress: string;
  director: string;
  region: string;
  hospitalSize: string;
  parkingStatus: string;
  businessRegistrationNumber: string;
  phoneNumber: string;
  email: string;
  note: string;
  isMarked: boolean;
  bankHolder: string;
  depositor: string;
  accountNumber: string;
  createdAt: string;
  entitlementMethod?: number;
  branchIds: number[];
  provinceName?: string;
  districtName?: string;
};

export type HospitalDetailSalesDTO = {
  key: number;
  id: number;
  hospitalName: string;
  provinceName: string;
  districtName: string;
  visitStatus: string;
  visitedAt: string;
  picName: string | null;
  hospitalPicName: string | null;
  hospitalManagerName: string | null;
  note: string;
};

//Delivery
export type HospitalDeliveryDTO = {
  key: number;
  id: number;
  hospitalId: number | null;
  managerId?: number;
  hospitalName: string;
  hospitalManager: string;
  numOfFunerals: number;
  requestedAt: Dayjs | null;
  deliveredAt: Dayjs | null;
  requestedBrochure: number;
  requestedBigTransportCase: number;
  requestedSmallTransportCase: number;
  deliveredBrochure: number;
  deliveredBigTransportCase: number;
  deliveredSmallTransportCase: number;
  deliveryStatus: ENUM_STATUS | null;
  note: string | null;
};

export type HospitalDeliverySummaryDTO = {
  total: string;
  request: Record<string, string>;
  delivery: Record<string, string>;
};

export type HospitalDeliverySummaryListDTO = {
  key: number;
  hospitalId: number;
  hospitalName: string;
  numOfFunerals: number;
  requestedBrochure: number;
  requestedBigTransportCase: number;
  requestedSmallTransportCase: number;
  deliveredBrochure: number;
  deliveredBigTransportCase: number;
  deliveredSmallTransportCase: number;
};

export type HospitalDeliveryTotal = {
  numOfFunerals: number;
  deliveredBrochure: number;
  deliveredTransportCase: number;
};

// Sales
export type HospitalSaleCalendarDTO = {
  id: string;
  calendarId: SALES_CALENDAR_EVENT_ID | SALES_CALENDAR_EVENT_INACTIVE;
  title: string;
  category: 'allday';
  state: SALES_CALENDAR_EVENT_ID;
  start: string;
  end: string;
  isReadOnly: true;
  status: CALENDAR_STATUS_ENUMS;
};

export type HospitalSaleListDTO = {
  id: number;
  key: number;
  hospitalName: number | null;
  provinceName: string | null;
  districtName: string | null;
  visitStatus: ENUM_STATUS;
  visitedAt: Dayjs | null;
  hospitalManagerName: string | null;
  hospitalPicName: string | null;
  note: string | null;
  hospitalId?: number;
  manager: number | null;
};

export type HospitalSaleTotal = {
  numOfFunerals: number;
  numerOfSalesVisit: number;
};

//Settlement
export type HospitalSettlementDTO = {
  key: number;
  hospitalId: number;
  hospitalName: string;
  settlementDate: string;
  funeralDate: string;
  funeralCode: string;
  reservationId?: number;
  'hospitalEntitlement.id': string;
  amount: string;
  latestEmailDate: string;
  memo: string;
};

type SettlementMethodSummary = {
  title: string;
  value: Array<{
    text: string;
    count: number;
  }>;
};

export type HospitalSettlementMethodSummaryDTO = SettlementMethodSummary[];

export type HospitalSettlementSummaryDTO = {
  key: React.Key;
  hospital: string;
  hospitalId: number;
  summary: Record<number, string>;
} & Record<string, [number, number]>;

export type HospitalDetailSettlementDTO = {
  key: number;
  hospitalSettlementId: number;
  branchName: string;
  reservationId: number;
  hospitalId: number;
  hospitalName: string;
  settlementDate: Dayjs | null;
  funeralDate: string;
  funeralCode: string;
  hospitalEntitlementId?: number;
  hospitalEntitlement: {
    id: number;
    name: string;
  } | null;
  amount: string;
  latestEmailDate: string | null;
  memo: string | null;
  canTakeAction: boolean;
};

export type HospitalDetailSettlementTotalDTO = {
  title: string;
  value: { text: string; count: number }[];
};
