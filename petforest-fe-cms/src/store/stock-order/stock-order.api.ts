import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { StockOrderListCreateREQ, StockOrderListREQ, StockOrderListUpdateREQ } from './request/stock-order.request';
import { StockOrderListRESP } from './response/stock-order.response';

export const stockOrderKeys = initKeys('stock-order');

export const getStockOrderListApi = async (
  params: StockOrderListREQ,
): Promise<BaseResponse<BaseListResponse<StockOrderListRESP[]>>> => {
  return api.get(`stock-orders`, { params });
};

export const updateStockOrderListApi = async (id: number, body: StockOrderListUpdateREQ): Promise<void> => {
  return api.patch(`stock-orders/${id}`, body);
};

export const deleteStockOrderListApi = async (ids: string): Promise<void> => {
  return api.delete(`stock-orders`, { params: { ids } });
};

export const createStockOrderListApi = async (
  body: StockOrderListCreateREQ = {
    guardianName: '',
    childName: '',
    guardianContact: '',
    columbariumPlace: '',
  },
): Promise<void> => {
  return api.post(`stock-orders`, body);
};

export const sendMessageStockOrderApi = async (body: { stockOrderIds: number[] }): Promise<void> => {
  return api.post(`stock-orders/messages`, body);
};
