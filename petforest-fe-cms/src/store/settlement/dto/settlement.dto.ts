import { COUPON_STATUS } from '@/utils/enums/common.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';

export type SettlementAdjustmentListDTO = {
  key: number;
  id: number;
  status: ENUM_STATUS;
  funeralDate: string | null;
  funeralId: number | null;
  funeralCode: string;
  basicAmount: number | null;
  productAmount: number | null;
  additionalCost: {
    transport: number | null;
    overweight: number | null;
    columbarium: number | null;
    temporaryPlacement: number | null;
    carbonPlate: number | null;
    otherAdditionalCost: number | null;
  };
  reasonOfDiscount: string | null;
  discountAmount: number | null;
  usageCoupon: number | null;
  remainingAmount: number | null;
  totalAmount: number | null;
  settlementMethod: {
    card: number | null;
    cash: number | null;
    accountAmount: number | null;
    prePaid: number | null;
  };
  invoiceMemo: string | null;
  memo: string | null;
  branchId: number | null;
};

export type SettlementAdjustmentSummaryDTO = {
  totalSettlements: number;
  totalAmount: number;
  totalRemainingAmount: number;
};

// Additional cost

export type SettlementAdjustmentAdditionalCostListDTO = {
  key: number;
  id: number;
  reservationId: number;
  status: ENUM_STATUS;
  funeralDate: string | null;
  basicAmount: number | null;
  transport: number | null;
  overweight: number | null;
  columbarium: number | null;
  temporaryPlacement: number | null;
  carbonPlate: number | null;
  otherAdditionalCost: number | null;
};

// Coupon

export type SettlementCouponListDTO = {
  key: number;
  id: number;
  code?: string;
  status: COUPON_STATUS;
  paymentCode?: string;
  amount?: number;
  note?: string;
  purchaser?: string;
  purchaserContact?: string;
  purchaseDate?: string; // epoch
  useDate?: string; // epoch
  reservationCode?: string;
  reservationId?: number;
  funeralDate?: string; // epoch
  funeralTime?: string;
  guardianName?: string;
  guardianPhone?: string;
};
