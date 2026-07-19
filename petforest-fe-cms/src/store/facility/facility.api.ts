import { api } from '@/config/http';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import {
  FacilityCreateREQ,
  FacilityHistoryCreateUpdateREQ,
  FacilityListREQ,
  FacilityListUpdateREQ,
} from './request/facility.request';
import {
  FacilityCompanyNamesRESP,
  FacilityDetailListRESP,
  FacilityDetailRESP,
  FacilityHistoryMaintanancesListRESP,
  FacilityListRESP,
  FacilityTypesRESP,
} from './response/facility.response';

export const createFacilityApi = async (body: FacilityCreateREQ) => {
  return await api.post(`facilities`, body);
};

export const facilityKeys = initKeys('facility');

export const getFacilityListApi = async (params: FacilityListREQ) => {
  return api.get<BaseListResponse<FacilityListRESP[]>>('facilities', { params });
};

export const facilityHistoryKeys = initKeys('facility-maintenance');

export const createFacilityMaintenanceHistoriesApi = async (body: FacilityHistoryCreateUpdateREQ) => {
  return await api.post('facilities/maintenances', body);
};

export const getFacilityMaintenanceHistoriesApi = async (params: FacilityListREQ) => {
  return api.get<BaseListResponse<FacilityHistoryMaintanancesListRESP[]>>('facilities/maintenances/histories', { params });
};

export const getDetailFacilityApi = async (id: number) => {
  if (id < 1) return;
  return api.get<BaseListResponse<FacilityDetailRESP>>(`facilities/${id}`);
};

export const getFacilityCompanyNamesApi = async () => {
  return api.get<BaseListResponse<FacilityCompanyNamesRESP[]>>('facilities/company-names');
};

export const deleteFacilityListApi = async (ids: string) => {
  return await api.delete(`facilities`, { params: { ids } });
};

export const updateFacilityListApi = async (id: number, body: FacilityListUpdateREQ) => {
  return await api.patch<BaseListResponse<FacilityListRESP>>(`facilities/${id}`, body);
};

export const getFacilityMaintenancesListApi = async (id: number, params: FacilityListREQ) => {
  if (id < 1) return;
  return api.get<BaseListResponse<FacilityDetailListRESP[]>>(`facilities/${id}/maintenances`, { params });
};

export const updateFacilityMaintenanceHistoriesApi = async (id: number, body: FacilityHistoryCreateUpdateREQ) => {
  return await api.patch(`facilities/maintenances/${id}`, body);
};

export const getFacilityTypeApi = async (companyNames: string) => {
  return api.get<BaseListResponse<FacilityTypesRESP[]>>('facilities/types', { params: { companyName: companyNames } });
};

export const getDetailFacilityHistoryApi = async (id: number) => {
  if (id < 1) return;
  return api.get<BaseListResponse<FacilityHistoryMaintanancesListRESP>>(`facilities/maintenances/${id}`);
};

export const deleteFacilityMaintenanceHistoriesApi = async (ids: string) => {
  return await api.delete('facilities/maintenances', { params: { ids } });
};
