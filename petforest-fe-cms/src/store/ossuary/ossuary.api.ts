import { api } from '@/config/http';
import { PLACE_STATUS } from '@/utils/enums/ossuary/ossuary-room.enum';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { isEmpty } from 'lodash';
import { ossuaryEntireListReqMapping } from './ossuary.service';
import {
  OssuaryDetailSettlementHistoryREQ,
  OssuaryDetailSettlementHistoryUpdateREQ,
  OssuaryDetailUsageHistoryREQ,
  OssuaryInformationCreateUpdateREQ,
  OssuaryListFilter,
  OssuaryMessageCreateREQ,
  OssuarySummariesREQ,
  OssuaryUpdateREQ,
} from './request/ossuary.request';
import {
  OssuaryDetailSettlementHistoryRESP,
  OssuaryDetailUsageHistoryRESP,
  OssuaryEntireListRESP,
  OssuaryEntireSummariesRESP,
  OssuaryListRESP,
  OssuaryPlaceDetailRESP,
  OssuaryPlaceDetailStatusRESP,
  OssuaryRoomDetailRESP,
} from './response/ossuary.response';

export const ossuaryKeys = initKeys('ossuary');
export const ossuaryStatusKeys = initKeys('ossuary-status');

export const getOssuaryEntireListApi = async (req?: OssuaryListFilter) => {
  const params = ossuaryEntireListReqMapping(req);
  return api.get<BaseListResponse<OssuaryEntireListRESP[]>>('columbariums', { params });
};

export const getOssuaryListApi = async (req?: OssuaryListFilter) => {
  const params = ossuaryEntireListReqMapping(req);
  return api.get<BaseListResponse<OssuaryListRESP[]>>('columbariums/entire', { params });
};

export const getOssuaryRoomDetailApi = async (name: string, isCheckout?: PLACE_STATUS) => {
  if (isEmpty(name)) return;
  return api.get<BaseListResponse<OssuaryRoomDetailRESP>>(`columbariums/${name}`, { params: { status: isCheckout } });
};

export const getOssuaryRoomNamesApi = async () => {
  return api.get<BaseListResponse<OssuaryRoomDetailRESP[]>>(`columbariums/names`);
};

export const updateOssuaryStatusApi = async (ids: string, status: PLACE_STATUS) => {
  return await api.patch(`/columbariums/places`, null, { params: { ids, status } });
};

export const updateOssuaryInfoApi = async (id: number, body: OssuaryUpdateREQ) => {
  return await api.patch<BaseListResponse<OssuaryEntireListRESP>>(`columbariums/information/${id}`, body);
};

export const updateExtendOssuaryTimeApi = async (ids: string) => {
  return await api.patch(`/columbariums/information/extends`, null, { params: { ids } });
};

export const getOssuaryPlaceDetailListApi = async (name: string, number: string, params?: OssuaryListFilter) => {
  return api.get<BaseListResponse<OssuaryPlaceDetailRESP[]>>(`columbariums/${name}/places/${number}`, { params });
};

export const createOssuaryPlaceInformationApi = async (body: OssuaryInformationCreateUpdateREQ) => {
  return await api.post('columbariums/information', body);
};

export const updateOssuaryPlaceInformationApi = async (id: number, body: OssuaryInformationCreateUpdateREQ) => {
  return await api.patch(`columbariums/information/${id}`, body);
};

export const getOssuaryInfomationDetailApi = async (id: number) => {
  return await api.get<BaseListResponse<OssuaryPlaceDetailRESP>>(`columbariums/information/${id}`);
};

export const getRoomPlaceStatusDetailApi = async (name: string, number: string) => {
  return await api.get<BaseListResponse<OssuaryPlaceDetailStatusRESP>>(`columbariums/${name}/places/${number}/status`);
};

export const getOssuaryEntireSummariesApi = async (params?: OssuarySummariesREQ) => {
  return api.get<BaseListResponse<OssuaryEntireSummariesRESP>>('columbariums/summaries', { params });
};

export const ossuaryDetailUsageHistoryKeys = initKeys('ossuary-detail-usage-history');
export const getOssuaryDetailUsageHistoryApi = async (id: number, params: OssuaryDetailUsageHistoryREQ) => {
  return await api.get<BaseListResponse<OssuaryDetailUsageHistoryRESP[]>>(`columbariums/information/${id}/contract-histories`, {
    params,
  });
};

export const updateOssuaryDetailUsageHistoryApi = async (id: number, body: { note: string }) => {
  return await api.patch(`columbariums/contract-histories/${id}`, body);
};

export const ossuaryDetailSettlementHistoryKeys = initKeys('ossuary-detail-settlement-history');
export const getOssuaryDetailSettlementHistoryApi = async (id: number, params: OssuaryDetailSettlementHistoryREQ) => {
  return await api.get<BaseListResponse<OssuaryDetailSettlementHistoryRESP[]>>(
    `columbariums/information/${id}/settlement-histories`,
    {
      params,
    },
  );
};

export const updateOssuaryDetailSettlementHistoryApi = async (id: number, body: OssuaryDetailSettlementHistoryUpdateREQ) => {
  return await api.patch(`columbariums/settlement-histories/${id}`, body);
};

export const createOssuaryMessageApi = async (body: OssuaryMessageCreateREQ) => {
  return await api.post('columbariums/messages', body);
};

export const getOssuaryInformationPdfApi = async (id: number) => {
  return api.get<Blob>(`columbariums/information/${id}/pdf`, { responseType: 'blob' });
};

export const getOssuaryContractHistoryPdfApi = async (id: number) => {
  return api.get<Blob>(`columbariums/contract-histories/${id}/pdf`, { responseType: 'blob' });
};
