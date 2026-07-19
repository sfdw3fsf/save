import { api } from '@/config/http';
import { SelectType } from '@/utils/types/post/select.type';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import {
  HospitalCalendarSaleREQ,
  HospitalDeliveryListREQ,
  HospitalDeliverySummaryListREQ,
  HospitalDeliveryUpdateREQ,
  HospitalDetailSettlementCreateREQ,
  HospitalDetailSettlementListREQ,
  HospitalDetailSettlementUpdateREQ,
  HospitalListREQ,
  HospitalSaleListCreateREQ,
  HospitalSaleListREQ,
  HospitalSaleListUpdateREQ,
  HospitalSettlementBulkREQ,
  HospitalSettlementListREQ,
  HospitalSettlementSendEmailREQ,
  HospitalSettlementSummaryListREQ,
  HospitalUpdateREQ,
} from './request/collaborating-hospital.request';
import {
  HospitalDeliveryRESP,
  HospitalDeliverySummaryListRESP,
  HospitalDeliverySummaryRESP,
  HospitalDetailRESP,
  HospitalDetailSalesRESP,
  HospitalDetailSettlementRESP,
  HospitalDetailSettlementTotalRESP,
  HospitalRegionRESP,
  HospitalRESP,
  HospitalSaleCalendarRESP,
  HospitalSaleListRESP,
  HospitalSettlementMethodSummaryRESP,
  HospitalSettlementRESP,
  HospitalSettlementSummaryRESP,
} from './response/collaborating-hospital.response';

export const regionKeys = initKeys('region');
export const getHospitalRegionApi = async (params: { startDate?: number; endDate?: number }) => {
  return api.get<BaseResponse<HospitalRegionRESP[]>>('regions/hospital', { params });
};

export const regionSalesKeys = initKeys('region-sales');
export const getHospitalRegionSalesApi = async (startDate?: number, endDate?: number) => {
  return api.get<BaseResponse<HospitalRegionRESP[]>>('regions/hospital/sales', { params: { startDate, endDate } });
};

export const createHospitalApi = async () => {
  return await api.post(`hospitals`);
};

export const hospitalKeys = initKeys('hospital');
export const getHospitalListApi = async (params: HospitalListREQ) => {
  return api.get<BaseListResponse<HospitalRESP[]>>('hospitals', { params });
};

export const hospitalSelectKeys = initKeys('hospital-select');
export const getHospitalSelectListApi = async (params?: { startDate?: number; endDate?: number }) => {
  return api.get<BaseResponse<SelectType[]>>('hospitals/select-list', { params });
};

export const updateHospitalApi = async (id: number, body: HospitalUpdateREQ) => {
  return await api.patch(`hospitals/${id}`, body);
};

export const updateMarkedHospitalApi = async (id: number) => {
  return await api.patch(`hospitals/${id}/mark`);
};

export const deleteHospitalApi = async (ids: string) => {
  return await api.delete(`hospitals`, { params: { ids } });
};

export const getHospitalDetailApi = async (id: number) => {
  return api.get<BaseResponse<HospitalDetailRESP>>(`hospitals/${id}`);
};

export const hospitalDetailSalesKeys = initKeys('hospital-detail-sales');
export const getHospitalDetailSalesApi = async (id: number, params: HospitalListREQ) => {
  return api.get<BaseListResponse<HospitalDetailSalesRESP[]>>(`hospital-sales/hospital/${id}`, { params });
};

//Delivery
export const createHospitalDeliveryApi = async (hospitalId?: number) => {
  return await api.post(`hospital-promo-materials`, { hospitalId });
};

export const hospitalDeliveryKeys = initKeys('hospital-delivery');
export const getHospitalDeliveryListApi = async (params: HospitalDeliveryListREQ) => {
  return api.get<BaseListResponse<HospitalDeliveryRESP[]>>('hospital-promo-materials', { params });
};

export const updateHospitalDeliveryApi = async (id: number, body: HospitalDeliveryUpdateREQ) => {
  return await api.patch(`hospital-promo-materials/${id}`, body);
};

export const deleteHospitalDeliveryApi = async (ids: string) => {
  return await api.delete(`hospital-promo-materials`, { params: { ids } });
};

export const hospitalDeliverySummaryKeys = initKeys('hospital-delivery-summary');
export const getHospitalDeliverySummaryApi = async (params: { startDate?: string; endDate?: string }) => {
  return api.get<BaseResponse<HospitalDeliverySummaryRESP>>('hospital-promo-materials/summary', { params });
};

export const hospitalDeliverySummaryListKeys = initKeys('hospital-delivery-summary-list');
export const getHospitalDeliverySummaryListApi = async (params: HospitalDeliverySummaryListREQ) => {
  return api.get<BaseListResponse<HospitalDeliverySummaryListRESP[]>>('hospital-promo-materials/summary-table', { params });
};

export const getHospitalDeliveryListByHospitalApi = async (id: number, params: HospitalDeliveryListREQ) => {
  return api.get<BaseListResponse<HospitalDeliveryRESP[]>>(`hospital-promo-materials/hospital/${id}`, { params });
};

// Sales
export const hospitalSaleCalendarKeys = initKeys('hospital-sale-calendar');
export const getHospitalSaleCalendarApi = async (params: HospitalCalendarSaleREQ) => {
  return api.get<BaseResponse<HospitalSaleCalendarRESP[]>>('hospital/sale-calendar', { params });
};

export const hospitalSaleKeys = initKeys('hospital-sale');

export const getHospitalSaleListApi = async (
  params: HospitalSaleListREQ,
): Promise<BaseResponse<BaseListResponse<HospitalSaleListRESP[]>>> => {
  return api.get(`hospital-sales`, { params });
};

export const updateHospitalSaleListApi = async (id: number, body: HospitalSaleListUpdateREQ): Promise<void> => {
  return api.patch(`hospital-sales/${id}`, body);
};

export const deleteHospitalSaleListApi = async (ids: string): Promise<void> => {
  return api.delete(`hospital-sales`, { params: { ids } });
};

export const createHospitalSaleListApi = async (
  body: HospitalSaleListCreateREQ = {
    note: '',
    hospitalPicName: '',
  },
  id?: number,
): Promise<void> => {
  return api.post(`hospital-sales`, { ...body, hospitalId: id });
};

//Settlement
export const hospitalSettlementKeys = initKeys('hospital-settlement');
export const getHospitalSettlementListApi = async (params: HospitalSettlementListREQ) => {
  return api.get<BaseListResponse<HospitalSettlementRESP[]>>('hospital-settlements', { params });
};

export const hospitalSettlementMethodSummaryKeys = initKeys('hospital-settlement-method-summary');
export const getHospitalSettlementMethodSummaryApi = async (params?: { startDate?: string; endDate?: string }) => {
  return api.get<BaseResponse<HospitalSettlementMethodSummaryRESP[]>>('hospital-settlements/entitlements', { params });
};

export const hospitalSettlementSummaryKeys = initKeys('hospital-settlement-summary');
export const getHospitalSettlementSummaryListApi = async (
  params: HospitalSettlementSummaryListREQ & { startDate?: string; endDate?: string },
) => {
  return api.get<BaseResponse<HospitalSettlementSummaryRESP>>('hospital-settlements/summaries', { params });
};

export const hospitalDetailSettlementKeys = initKeys('hospital-detail-settlement');
export const getHospitalDetailSettlementApi = async (id: number, params: HospitalDetailSettlementListREQ) => {
  return api.get<BaseListResponse<HospitalDetailSettlementRESP[]>>(`hospital-settlements/hospital/${id}`, { params });
};

export const createHospitalSettlementApi = async (body: HospitalDetailSettlementCreateREQ) => {
  return await api.post(`hospital-settlements`, body);
};

export const updateHospitalSettlementApi = async (id: number, body: HospitalDetailSettlementUpdateREQ) => {
  return await api.patch(`hospital-settlements/${id}`, body);
};

export const deleteHospitalSettlementApi = async (ids: string): Promise<void> => {
  return api.delete(`hospital-settlements`, { params: { ids } });
};

export const hospitalDetailSettlementTotalKeys = initKeys('hospital-detail-settlement-total');
export const getHospitalDetailSettlemenTotaltApi = async (id: number) => {
  return api.get<BaseResponse<HospitalDetailSettlementTotalRESP[]>>(`hospital-settlements/hospital/${id}/summaries`);
};

export const sendEmailHospitalSettlementApi = async (id: number, body: HospitalSettlementSendEmailREQ) => {
  return await api.post(`hospital-settlements/${id}/send-email`, body);
};

export const hospitalSettlementPdf = initKeys('hospital-detail-settlement');
export const gethospitalSettlementPdfApi = async (id: number, params: HospitalSettlementSendEmailREQ) => {
  return api.get<Blob>(`hospital-settlements/${id}/pdf`, { params, responseType: 'blob' });
};

export const updateBulkSettlementDateApi = async (body: HospitalSettlementBulkREQ) => {
  return await api.patch(`hospital-settlements`, body);
};
