import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { NotiHistoryListREQ } from './request/noti-history.request';
import { NotiHistoryDetailRESP, NotiHistoryListRESP } from './response/noti-history.response';

export const notiHistoryKeys = initKeys('funeral-noti-history');

export const getNotiHistoryListApi = async (
  params: NotiHistoryListREQ,
): Promise<{ data: BaseListResponse<NotiHistoryListRESP[]> }> => {
  return api.get(`messages`, { params });
};

export const getNotiHistoryDetailApi = async (id: number): Promise<{ data: BaseResponse<NotiHistoryDetailRESP> }> => {
  return api.get(`messages/${id}`);
};
