import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { SettingProgressListREQ, SettingProgressUpdateREQ } from './request/setting-progress.request';
import { SettingProgressRESP, SettingSubProgressRESP } from './response/setting-progress.response';

export const createSettingProgressApi = async () => {
  return await api.post(`progresses`);
};

export const settingProgressKeys = initKeys('setting-progresses');

export const getSettingProgressApi = async (params: SettingProgressListREQ) => {
  return api.get<BaseListResponse<SettingProgressRESP[]>>('progresses', { params });
};

export const getSubSettingProgressApi = async (progressId: number) => {
  if (progressId < 1) return;
  return api.get<BaseResponse<SettingSubProgressRESP[]>>(`progresses/${progressId}/sub-progresses`);
};

export const createSubSettingProgressApi = async (progressId: number) => {
  if (progressId < 1) return;
  return await api.post<BaseResponse<SettingSubProgressRESP>>(`progresses/${progressId}/sub-progresses`);
};

export const updateSubSettingProgressApi = async (progressId: number, subProgressId: number, body: SettingProgressUpdateREQ) => {
  if (progressId < 1) return;
  return await api.patch(`progresses/${progressId}/sub-progresses/${subProgressId}`, body);
};

export const deleteSubSettingProgressApi = async (progressId: number, ids: string) => {
  if (progressId < 1) return;
  return await api.delete(`progresses/${progressId}/sub-progresses`, { params: { ids } });
};

export const deleteSettingProgressApi = async (ids: string) => {
  return await api.delete(`progresses`, { params: { ids } });
};

export const updateSettingProgressApi = async (id: number, body: SettingProgressUpdateREQ) => {
  return await api.patch<BaseListResponse<SettingProgressRESP>>(`progresses/${id}`, body);
};
