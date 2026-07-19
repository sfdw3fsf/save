import { api } from '@/config/http';
import { PAYMENT_METHOD } from '@/utils/enums/common.enum';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import {
  SettlementAdjustmentAdditionalCostListREQ,
  SettlementAdjustmentListREQ,
  SettlementAdjustmentListUpdateREQ,
  SettlementCouponListREQ,
  SettlementCouponListUpdateREQ,
} from './request/settlement.request';
import {
  SettlementAdjustmentAdditionalCostListRESP,
  SettlementAdjustmentListRESP,
  SettlementAdjustmentSummaryRESP,
  SettlementCouponListRESP,
} from './response/settlement.response';

export const settlementAdjustmentKeys = initKeys('settlement-adjustment');

export const getSettlementAdjustmentListApi = async (
  params: SettlementAdjustmentListREQ,
): Promise<BaseResponse<BaseListResponse<SettlementAdjustmentListRESP[]>>> => {
  return api.get(`settlements`, { params });
};

export const updateSettlementAdjustmentListApi = async (id: number, body: SettlementAdjustmentListUpdateREQ): Promise<void> => {
  return api.patch(`settlements/${id}`, body);
};

export const deleteSettlementAdjustmentListApi = async (ids: string): Promise<void> => {
  return api.delete(`settlements`, { params: { ids } });
};

export const createSettlementAdjustmentListApi = async (): Promise<void> => {
  return api.post(`settlements`);
};

export const settlementAdjustmentSummaryKeys = initKeys('settlement-adjustment-summary');

export const getSettlementAdjustmentSummaryApi = async (params: {
  startDate: string;
  endDate: string;
  reservationMethods?: PAYMENT_METHOD[];
}): Promise<{
  data: BaseResponse<SettlementAdjustmentSummaryRESP>;
}> => {
  return api.get(`settlements/summary`, { params });
};

// Additional cost

export const settlementAdjustmentAdditionalCostKeys = initKeys('settlement-adjustment-additional-cost');

export const getSettlementAdjustmentAdditionalCostListApi = async (
  params: SettlementAdjustmentAdditionalCostListREQ,
): Promise<BaseResponse<BaseListResponse<SettlementAdjustmentAdditionalCostListRESP[]>>> => {
  return api.get(`settlements/additional-costs`, { params });
};

export const getSettlementAdditionalCostSummaryApi = async (params: {
  startDate: string;
  endDate: string;
}): Promise<{ total: number }> => {
  const response = await api.get(`settlements/additional-costs/summary`, { params });
  return response.data;
};

// Coupon

export const settlementCouponKeys = initKeys('settlement-coupon');

export const getSettlementCouponListApi = async (
  params: SettlementCouponListREQ,
): Promise<BaseResponse<{ data: SettlementCouponListRESP[]; count: number; totalAmount: number }>> => {
  return api.get(`coupons`, { params });
};

export const updateSettlementCouponListApi = async (id: number, body: SettlementCouponListUpdateREQ): Promise<void> => {
  return api.patch(`coupons/${id}`, body);
};
