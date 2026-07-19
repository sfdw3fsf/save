import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type SettlementAdjustmentListFilter = FilterREQ;

export type SettlementAdjustmentListREQ = SettlementAdjustmentListFilter & PagingREQ;

export type SettlementAdjustmentListUpdateREQ = {
  status?: string;
  funeralDate?: string;
  productAmount?: number;
  basicAmount?: number;
  transport?: number;
  overweight?: number;
  columbarium?: number;
  temporaryPlacement?: number;
  carbonPlate?: number;
  otherAdditionalCost?: number;
  reasonOfDiscount?: string;
  discountAmount?: number;
  card?: number;
  cash?: number;
  accountAmount?: number;
  prePaid?: number;
  invoiceMemo?: string;
  memo?: string;
};

// Additional cost

export type SettlementAdjustmentAdditionalCostListFilter = FilterREQ;

export type SettlementAdjustmentAdditionalCostListREQ = SettlementAdjustmentAdditionalCostListFilter & PagingREQ;

// Coupon

export type SettlementCouponListFilter = FilterREQ;

export type SettlementCouponListREQ = SettlementCouponListFilter & PagingREQ;

export type SettlementCouponListUpdateREQ = {
  status?: string;
  note?: string;
};
