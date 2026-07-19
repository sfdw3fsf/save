import { SALES_CALENDAR_STATUS } from '@/utils/enums/collaborating/sales-mngt.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type HospitalListFilter = {
  regions?: string;
} & FilterREQ;

export type HospitalListREQ = HospitalListFilter & PagingREQ;

export type HospitalDetailSettlementListFilter = FilterREQ;
export type HospitalDetailSettlementListREQ = FilterREQ & HospitalDetailSettlementListFilter;

export type HospitalUpdateREQ = {
  hospitalName?: string;
  region?: string;
  branchIds?: number[];
  director?: string;
  settlementMethodId?: number;
  hospitalSize?: string;
  parkingStatus?: string;
  address?: string;
  detailAddress?: string;
  businessRegistrationNumber?: string;
  phoneNumber?: string;
  email?: string;
  note?: string;
  bankHolder?: string;
  depositor?: string;
  accountNumber?: string;
  entitlementMethod?: number;
  provinceName?: string;
  districtName?: string;
};

//Delivery
export type HospitalDeliveryListFilter = FilterREQ;

export type HospitalDeliveryListREQ = HospitalDeliveryListFilter & PagingREQ;

export type HospitalDeliverySummaryListREQ = HospitalDeliveryListFilter;

export type HospitalDeliveryUpdateREQ = {
  numOfFunerals?: number;
  requestedAt?: number;
  deliveredAt?: number;
  requestedBrochure?: number;
  requestedBigTransportCase?: number;
  requestedSmallTransportCase?: number;
  deliveredBrochure?: number;
  deliveredBigTransportCase?: number;
  deliveredSmallTransportCase?: number;
  managerId?: number;
  hospitalId?: number | null;
  note?: string;
  deliveryStatus?: ENUM_STATUS;
};

// Sales

export type HospitalCalendarSaleREQ = {
  startDate: number;
  endDate: number;
  saleTypes?: SALES_CALENDAR_STATUS[];
  branchId?: number;
};

export type HospitalSaleListFilter = { regions?: string } & FilterREQ;

export type HospitalSaleListREQ = HospitalSaleListFilter & PagingREQ;

export type HospitalSaleListUpdateREQ = {
  visitStatus?: string;
  visitedAt?: string;
  hospitalManagerName?: string;
  hospitalPicName?: string;
  note?: string;
  hospitalId?: number;
  managerId?: number;
};

export type HospitalSaleListCreateREQ = {
  note: string;
  hospitalPicName: string;
};

//Settlement
export type HospitalSettlementListFilter = { 'reservation.branchId'?: number } & FilterREQ;
export type HospitalSettlementListREQ = FilterREQ;

export type HospitalSettlementSummaryListREQ = FilterREQ;

export type HospitalDetailSettlementCreateREQ = {
  amount?: number;
  memo?: string;
  hospitalId: number;
  hospitalEntitlementId?: number;
};

export type HospitalDetailSettlementUpdateREQ = {
  amount?: number;
  memo?: string;
  hospitalId?: number;
  hospitalEntitlementId?: number;
  settlementDate?: number;
};

export type HospitalSettlementSendEmailREQ = {
  startDate: number;
  endDate: number;
};

export type HospitalSettlementBulkREQ = {
  ids: number[];
  settlementDate: number;
};
