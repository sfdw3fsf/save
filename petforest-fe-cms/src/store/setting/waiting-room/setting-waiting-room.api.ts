import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { SettingWaitingRoomListREQ, SettingWaitingRoomUpdateREQ } from './request/setting-waiting-room.request';
import { SettingWaitingRoomRESP } from './response/setting-waiting-room.response';

export const createWaitingRoomApi = async () => {
  return await api.post(`waiting-rooms`);
};

export const settingWaitingRoomKeys = initKeys('setting-waiting-room');

export const getWaitingRoomListApi = async (params: SettingWaitingRoomListREQ) => {
  return api.get<BaseListResponse<SettingWaitingRoomRESP[]>>('waiting-rooms', { params });
};

export const updateWaitingRoomApi = async (id: number, body: SettingWaitingRoomUpdateREQ) => {
  return await api.patch<BaseResponse<SettingWaitingRoomRESP>>(`waiting-rooms/${id}`, body);
};

export const deleteWaitingRoomListApi = async (ids: string) => {
  return await api.delete(`waiting-rooms`, { params: { ids } });
};
