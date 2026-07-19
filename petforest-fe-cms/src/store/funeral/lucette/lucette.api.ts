import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { lucetteListReqMapping, lucetteScheduleInputToReq } from './lucette.service';
import { LucetteListREQ, LucetteListUpdateREQ, LucetteScheduleInput, LucetteSummaryREQ } from './request/lucette.request';
import { LucetteDetailRESP, LucetteListRESP, LucetteScheduleListRESP, LucetteSummaryRESP } from './response/lucette.response';

export const lucetteKeys = initKeys('funeral-lucette');

export const getLucetteListApi = async (req: LucetteListREQ): Promise<{ data: BaseListResponse<LucetteListRESP[]> }> => {
  const params = lucetteListReqMapping(req);
  return api.get(`lucettes`, { params });
};

export const updateLucetteListApi = async (id: number, body: LucetteListUpdateREQ): Promise<void> => {
  return api.patch(`lucettes/${id}`, body);
};

export const getLucetteDetailApi = async (id: number): Promise<{ data: BaseResponse<LucetteDetailRESP> }> => {
  return api.get(`lucettes/${id}`);
};

export const lucetteSummaryKeys = initKeys('funeral-lucette-summary');

export const getLucetteSummaryApi = async (params: LucetteSummaryREQ): Promise<{ data: BaseResponse<LucetteSummaryRESP> }> => {
  return api.get(`lucettes/summaries`, { params });
};

export const lucetteScheduleKeys = initKeys('funeral-lucette-schedule');

export const getLucetteScheduleListApi = async (
  input: LucetteScheduleInput,
): Promise<{ data: BaseResponse<LucetteScheduleListRESP[]> }> => {
  return api.get(`lucettes/schedules`, { params: lucetteScheduleInputToReq(input) });
};

export const createLucetteMessageApi = async (lucetteIds: number[]) => {
  return await api.post('lucettes/messages', { lucetteIds });
};

export const getLucettePdfApi = async (id: number) => {
  return api.get<Blob>(`lucettes/${id}/pdf`, { responseType: 'blob' });
};
