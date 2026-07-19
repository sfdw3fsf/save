import { api } from '@/config/http';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { ReservationTimeREQ, ReservationTimeUpdateStatusREQ } from './request/manager-reservation-time.request';
import { ManagerReservationTimeRESP } from './response/manager-reservation-time.response';

export const reservationTimeSettingKeys = initKeys('manager-reservation-time-setting');
export const getReservationTimeSettingApi = async () => {
  return api.get<BaseListResponse<ManagerReservationTimeRESP[]>>(`reservation-times/settings`);
};

export const updateReservationTimeSettingApi = async (ids: string) => {
  return await api.patch(`reservation-times/settings`, undefined, { params: { ids } });
};

export const reservationTimeKeys = initKeys('manager-reservation-time');
export const getReservationTimeApi = async (params: ReservationTimeREQ) => {
  return api.get<BaseListResponse<{ reservationTimes: ManagerReservationTimeRESP[] }>>(`reservation-times`, { params });
};

export const updateReservationTimeStatusApi = async (id: number, body: ReservationTimeUpdateStatusREQ) => {
  return await api.patch(`reservation-times/${id}`, body);
};

export const updateReservationTimeAllStatusApi = async (body: ReservationTimeUpdateStatusREQ) => {
  return await api.patch(`reservation-times`, body);
};
