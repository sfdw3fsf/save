import { api } from '@/config/http';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { SettingPartnerListREQ, SettingPartnerUpdateREQ } from './request/setting-partner.request';
import { SettingPartnerRESP } from './response/setting-partner.response';

export const createPartnerApi = async () => {
  return await api.post(`partners`);
};

export const settingPartnerKeys = initKeys('setting-partner');

export const getPartnerListApi = async (params: SettingPartnerListREQ) => {
  return api.get<BaseListResponse<SettingPartnerRESP[]>>('partners', { params });
};

export const deleteSettingPartnerApi = async (ids: string) => {
  return await api.delete(`partners`, { params: { ids } });
};

export const getDetailSettingPartnerApi = async (id: number) => {
  if (id < 1) return;
  return api.get<BaseListResponse<SettingPartnerRESP>>(`partners/${id}`);
};

export const updateSettingPartnerApi = async (id: number, body: SettingPartnerUpdateREQ) => {
  return await api.patch<BaseListResponse<SettingPartnerRESP>>(`partners/${id}`, body);
};
