import { api } from '@/config/http';
import { initKeys } from '@/utils/utils/query-key.utils';
import { BaseResponse } from '@/utils/utils/types/response.type';
import {
  ObituaryCouponCreateREQ,
  ObituaryMessageCreateREQ,
  ObituaryMessageValidateREQ,
  ObituaryMobileRegisterREQ,
  ObituaryUpdateREQ,
} from './request/obituary.request';
import {
  ObituaryCreateCouponRESP,
  ObituaryMessagesRESP,
  ObituaryMobileRegisterRESP,
  ObituaryRESP,
} from './response/obituary.response';

export const obituaryKeys = initKeys('obituary');
export const getObituaryApi = async (id: string) => {
  return api.get<BaseResponse<ObituaryRESP>>(`obituaries/${id}`);
};

export const updateObituaryApi = async (id: string, body: ObituaryUpdateREQ) => {
  return await api.patch(`obituaries/${id}`, body);
};

export const obituaryMessagesKeys = initKeys('messages');
export const getObituaryMessagesApi = async (id: string) => {
  return api.get<BaseResponse<ObituaryMessagesRESP[]>>(`obituaries/${id}/messages`);
};

export const getObituaryMessageDetailApi = async (id: string, messageId: number) => {
  return api.get<BaseResponse<ObituaryMessagesRESP>>(`obituaries/${id}/messages/${messageId}`);
};

export const createObituaryMessageApi = async (id: string, body: ObituaryMessageCreateREQ) => {
  return await api.post(`obituaries/${id}/messages`, body);
};

export const updateObituaryMessageApi = async (id: string, messageId: number, body: ObituaryMessageCreateREQ) => {
  return await api.patch(`obituaries/${id}/messages/${messageId}`, body);
};

export const validateObituaryMessageApi = async (id: string, messageId: number, body: ObituaryMessageValidateREQ) => {
  return await api.post(`obituaries/${id}/messages/validate/${messageId}`, body);
};

export const deleteObituaryMessageApi = async (id: string, messageId: number, params: ObituaryMessageValidateREQ) => {
  return await api.delete(`obituaries/${id}/messages/${messageId}`, { params });
};

export const increaseCountViewApi = async (id: string) => {
  return await api.patch(`obituaries/${id}/view`);
};

//Payment
export const createObituaryCouponApi = async (id: string, body: ObituaryCouponCreateREQ) => {
  return await api.post<BaseResponse<ObituaryCreateCouponRESP>>(`obituaries/${id}/coupons`, body);
};

export const createObituaryMobileRegisterApi = async (body: ObituaryMobileRegisterREQ) => {
  return await api.post<BaseResponse<ObituaryMobileRegisterRESP>>(`obituaries/payments/register`, body);
};
