import { SALES_CALENDAR_EVENT_ID } from '@/utils/enums/home/calendar.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';

export type Region = {
  id: number | null;
  krName: string;
  total: number;
  totalFuneral?: number;
};

export type HospitalRegionRESP = Region & {
  districts: Region[];
};

export type HospitalRESP = {
  id: number;
  name: string;
  region: string;
  isMarked: boolean;
  createdAt: string;
  isNewHospital: boolean;
  totalFuneral: number;
  totalSales: number;
  entitlementMethod?: {
    id: number;
    name: string;
  };
  hospitalBranches?: {
    id: number;
    name: string;
  }[];
};

export type HospitalDetailRESP = {
  id: number;
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
  entitlementMethod?: {
    id: number;
    name: string;
  };
  hospitalBranches?: {
    id: number;
    name: string;
  }[];
};

export type HospitalDetailSalesRESP = {
  id: number;
  hospitalName: string;
  provinceName: string;
  districtName: string;
  visitStatus: string;
  visitedAt: string | null;
  picName: string | null;
  hospitalPicName: string | null;
  hospitalManagerName: string | null;
  note: string;
};

//Delivery
export type HospitalDeliveryRESP = {
  id: number;
  manager: {
    managerId: number;
    managerName: string;
  } | null;
  hospitalId: number | null;
  hospitalName: string;
  hospitalManager: string;
  numOfFunerals: number;
  requestedAt: string | null;
  deliveredAt: string | null;
  requestedBrochure: number;
  requestedBigTransportCase: number;
  requestedSmallTransportCase: number;
  deliveredBrochure: number;
  deliveredBigTransportCase: number;
  deliveredSmallTransportCase: number;
  deliveryStatus: ENUM_STATUS | null;
  note: string | null;
};

export type HospitalDeliverySummaryRESP = {
  totalFuneral: number;
  totalRequestedBrochure: number;
  totalRequestedBigTransportCase: number;
  totalRequestedSmallTransportCase: number;
  totalDeliveredBrochure: number;
  totalDeliveredBigTransportCase: number;
  totalDeliveredSmallTransportCase: number;
};

export type HospitalDeliverySummaryListRESP = {
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

// Sales
export type HospitalSaleCalendarRESP = {
  hospitalName: string;
  saleType: SALES_CALENDAR_EVENT_ID;
  markedAt: string;

  managerName?: string;
  region?: string;
  requestedBrochure?: number;
  requestedBigTransportCase?: number;
  requestedSmallTransportCase?: number;
  deliveredBrochure?: number;
  deliveredBigTransportCase?: number;
  deliveredSmallTransportCase?: number;
};

export type HospitalSaleListRESP = {
  id: number;
  hospitalName: string | null;
  provinceName: string | null;
  districtName: string | null;
  visitStatus: string;
  visitedAt: string | null;
  hospitalManagerName: string | null;
  hospitalPicName: string | null;
  note: string | null;
  hospitalId: number | null;
  manager: { managerId?: number; managerName?: string };
};

//Settlement
export type HospitalSettlementRESP = {
  hospitalSettlementId: number;
  branchName: string;
  reservationId: number;
  hospitalId: number;
  hospitalName: string;
  settlementDate: string | null;
  funeralDate: string;
  funeralCode: string;
  hospitalEntitlement: {
    id: number;
    name: string;
  };
  amount: number;
  latestEmailDate: string;
  memo: string | null;
  canTakeAction: boolean;
};

export type HospitalSettlementMethodSummaryRESP = {
  entitlementId: number;
  entitlementName: string;
  numberOfSettlement: number;
  totalSettlementAmount: number;
};

export type HospitalSettlementSummaryRESP = {
  hospitals: Record<
    string,
    {
      hospitalId: number;
      data: Array<{
        entitlementId?: number;
        entitlementName?: string;
        numberOfSettlement: number;
        totalSettlementAmount: number;
      }>;
    }
  >;
  totalAmount: number;
};

export type HospitalDetailSettlementRESP = {
  hospitalSettlementId: number;
  branchName: string;
  reservationId: number;
  hospitalId: number;
  hospitalName: string;
  settlementDate: string;
  funeralDate: string;
  funeralCode: string;
  hospitalEntitlement: {
    id: number;
    name: string;
  } | null;
  amount: number;
  latestEmailDate: string | null;
  memo: string | null;
  canTakeAction: boolean;
};

export type HospitalDetailSettlementTotalRESP = {
  entitlementId: number;
  entitlementName: string;
  totalSettlement: number;
  totalAmount: number;
  hospitalId: number;
};
