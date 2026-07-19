import { api } from '@/config/http';
import { initKeys } from '@/utils/utils/query-key.utils';
import { BaseResponse } from '@/utils/utils/types/response.type';
import { PntDetailRESP, PntSettingListRESP } from './response/pnt-link.response';

export const pntSettingKeys = initKeys('funeral-reservation-photo-pnt-setting');
export const getPntSettingsListApi = async (id?: string): Promise<{ data: BaseResponse<PntSettingListRESP[]> }> => {
  return api.get(`reservation-photos/pnt-settings?branchId=${id}`);
};

export const getPntDetailApi = async (id: string): Promise<{ data: BaseResponse<PntDetailRESP> }> => {
  return api.get(`reservation-photos/pnt-settings/${id}`);
};
