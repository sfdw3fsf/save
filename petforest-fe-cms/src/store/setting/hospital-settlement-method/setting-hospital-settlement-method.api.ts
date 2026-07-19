import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import {
  SettingHospitalSettlementMethodListREQ,
  SettingHospitalSettlementMethodUpdateREQ,
  SettingHospitalSettlementMethodUpdateStatusREQ,
} from './request/setting-hospital-settlement-method.request';

import { SettingHospitalSettlementMethodRESP } from './response/setting-hospital-settlement-method.response';

export const createSettingHospitalSettlementMethodApi = async () => {
  return await api.post(`hospital-entitlements`);
};

export const settingHospitalSettlementMethodKeys = initKeys('setting-hospital-entitlements');

export const getHospitalSettlementMethodListApi = async (params?: SettingHospitalSettlementMethodListREQ) => {
  return api.get<BaseListResponse<SettingHospitalSettlementMethodRESP[]>>('hospital-entitlements', { params });
};

export const deleteHospitalSettlementMethodListApi = async (ids: string) => {
  return await api.delete(`hospital-entitlements`, { params: { ids } });
};
export const updateHospitalSettlementMethodApi = async (id: number, body: SettingHospitalSettlementMethodUpdateREQ) => {
  return await api.patch<BaseResponse<SettingHospitalSettlementMethodRESP>>(`hospital-entitlements/${id}`, body);
};

export const updateStatusHospitalSettlementMethodApi = async (
  id: number,
  body: SettingHospitalSettlementMethodUpdateStatusREQ,
) => {
  return await api.patch<BaseResponse<SettingHospitalSettlementMethodRESP>>(`hospital-entitlements/${id}/status`, body);
};
