import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { PntSettingDetailUpdateREQ } from './request/pnt-setting.request';
import { ReservationPhotoListREQ } from './request/reservation-photo.resquest';
import { reservationPhotoListReqMapping } from './reservation-photo.service';
import { PntSettingDetailCreateRESP, PntSettingDetailRESP, PntSettingListRESP } from './response/pnt-setting.response';
import { ReservationPhotoDetailRESP, ReservationPhotoListRESP } from './response/reservation-photo.response';

export const reservationPhotoKeys = initKeys('funeral-reservation-photo');

export const getReservationPhotoListApi = async (
  req?: ReservationPhotoListREQ,
): Promise<{ data: BaseListResponse<ReservationPhotoListRESP[]> }> => {
  const params = reservationPhotoListReqMapping(req);
  return api.get(`reservation-photos`, { params });
};

export const getReservationPhotoDetailApi = async (id: number): Promise<{ data: BaseResponse<ReservationPhotoDetailRESP> }> => {
  return api.get(`reservation-photos/${id}`);
};

// pnt-setting

export const pntSettingKeys = initKeys('funeral-reservation-photo-pnt-setting');

export const getPntSettingsListApi = async (): Promise<{ data: BaseResponse<PntSettingListRESP[]> }> => {
  return api.get(`reservation-photos/pnt-settings`);
};

export const getPntSettingDetailApi = async (id: number): Promise<{ data: BaseResponse<PntSettingDetailRESP> }> => {
  return api.get(`reservation-photos/pnt-settings/${id}`);
};

export const updatePntSettingDetailApi = async (id: number, body: PntSettingDetailUpdateREQ): Promise<void> => {
  return api.patch(`reservation-photos/pnt-settings/${id}`, body);
};

export const createPntSettingDetailApi = async (): Promise<{ data: BaseResponse<PntSettingDetailCreateRESP> }> => {
  return api.post(`reservation-photos/pnt-settings`);
};

export const deletePntSettingDetailApi = async (id: number): Promise<void> => {
  return api.delete(`reservation-photos/pnt-settings/${id}`);
};

export const downloadReservationPhotosApi = async (params: { ids: string }) => {
  return api.get<Blob>('reservation-photos/images', {
    params,
    responseType: 'blob',
  });
};
