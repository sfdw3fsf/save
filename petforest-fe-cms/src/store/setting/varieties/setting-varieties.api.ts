import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { SettingVarietiesListREQ, SettingVarietiesUpdateREQ } from './request/setting-varieties.request';
import { SettingVarietiesBreedNameRESP, SettingVarietiesRESP } from './response/setting-varieties.response';

export const createSettingVarietiesApi = async () => {
  return await api.post(`pet-types`);
};

export const settingVarietiesKeys = initKeys('setting-varieties');

export const getSettingVarietiesApi = async (params: SettingVarietiesListREQ) => {
  return api.get<BaseListResponse<SettingVarietiesRESP[]>>('pet-types', { params });
};

export const deleteSettingVarietiesApi = async (ids: string) => {
  return await api.delete(`pet-types`, { params: { ids } });
};

export const updateSettingVarietiesApi = async (id: number, body: SettingVarietiesUpdateREQ) => {
  return await api.patch<BaseListResponse<SettingVarietiesRESP>>(`pet-types/${id}`, body);
};

export const settingVarietiesBreedNameKeys = initKeys('setting-varieties-breed-name');

export const getSettingVarietiesBreedNameApi = async () => {
  return api.get<BaseResponse<SettingVarietiesBreedNameRESP[]>>('pet-types/breeds/names');
};

//Breed
export const createSettingVarietiesBreedApi = async (id: number) => {
  return await api.post(`pet-types/${id}/breeds`);
};

export const getSettingVarietiesBreedApi = async (id: number) => {
  return api.get<BaseListResponse<SettingVarietiesRESP[]>>(`pet-types/${id}/breeds`);
};

export const deleteSettingVarietiesBreedApi = async (id: number, ids: string) => {
  return await api.delete(`pet-types/${id}/breeds`, { params: { ids } });
};

export const updateSettingVarietiesBreedApi = async (id: number, breedId: number, body: SettingVarietiesUpdateREQ) => {
  return await api.patch<BaseListResponse<SettingVarietiesRESP>>(`pet-types/${id}/breeds/${breedId}`, body);
};
