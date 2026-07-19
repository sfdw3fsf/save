import { api } from '@/config/http';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import {
  SettingServicesListREQ,
  SettingServiceUpdateDefaultMaterialREQ,
  SettingServiceUpdateREQ,
} from './request/setting-services.request';

import { MaterialRESP } from '@/store/material/response/material.response';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { SettingServicesDefaultMaterialRESP, SettingServicesRESP } from './response/setting-services.response';

export const createSettingServiceApi = async () => {
  return await api.post(`funeral-services`);
};

export const settingServiceKeys = initKeys('setting-funeral-services');

export const getSettingServiceApi = async (params: SettingServicesListREQ) => {
  return api.get<BaseListResponse<SettingServicesRESP[]>>('funeral-services', { params });
};

export const deleteServiceListApi = async (ids: string) => {
  return await api.delete(`funeral-services`, { params: { ids } });
};

export const updateSettingServiceApi = async (id: number, body: SettingServiceUpdateREQ) => {
  return await api.patch<BaseListResponse<SettingServicesRESP>>(`funeral-services/${id}`, body);
};

export const createSettingServiceMaterialApi = async (id: number) => {
  return await api.post(`funeral-services/${id}/service-default-materials`);
};

export const getSettingMaterialOptionApi = async () => {
  return api.get<BaseListResponse<MaterialRESP[]>>('materials', {
    params: { materialType: MATERIAL_TYPE.FUNERAL },
  });
};

export const getSettingServiceMaterialApi = async (id: number) => {
  if (!id) return;
  return api.get<BaseListResponse<SettingServicesDefaultMaterialRESP[]>>(`funeral-services/${id}/service-default-materials`);
};

export const deleteServiceMaterialListApi = async (funeralServiceId: number, ids: string) => {
  return await api.delete(`funeral-services/${funeralServiceId}/service-default-materials`, { params: { ids } });
};

export const updateSettingServiceMaterialApi = async (
  id: number,
  serviceDefaultMaterialId: number,
  body: SettingServiceUpdateDefaultMaterialREQ,
) => {
  return await api.patch(`funeral-services/${id}/service-default-materials/${serviceDefaultMaterialId}`, body);
};
