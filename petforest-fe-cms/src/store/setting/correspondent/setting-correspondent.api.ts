import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { SettingCorrespondentListREQ, SettingCorrespondentUpdateREQ } from './request/setting-correspondent.request';
import { SettingCorrespondentRESP } from './response/setting-correspondent.response';

export const createSettingCorrespondentApi = async () => {
  return await api.post(`vendors`);
};

export const settingCorrespondentKeys = initKeys('setting-vendors');

export const getSettingCorrespondentApi = async (params?: SettingCorrespondentListREQ) => {
  return api.get<BaseListResponse<SettingCorrespondentRESP[]>>('vendors', { params });
};

export const getDetailSettingCorrespondentApi = async (id: number) => {
  if (id < 1) return;
  return api.get<BaseListResponse<SettingCorrespondentRESP>>(`vendors/${id}`);
};

export const deleteSettingCorrespondentListApi = async (ids: string) => {
  return await api.delete(`vendors`, { params: { ids } });
};

export const updateCorrespondentApi = async (id: number, body: SettingCorrespondentUpdateREQ) => {
  return await api.patch<BaseResponse<SettingCorrespondentRESP>>(`vendors/${id}`, body);
};
