import { api } from '@/config/http';
import { DELETE_TYPE } from '@/utils/enums/common.enums';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import {
  ReservationCreateREQ,
  ReservationListREQ,
  ReservationUpdateColumbariumREQ,
  ReservationUpdateDetailREQ,
  ReservationUpdateFunaralInformationREQ,
  ReservationUpdateGuardianInformationREQ,
  ReservationUpdateHospitalInformationREQ,
  ReservationUpdateInventoriesREQ,
  ReservationUpdateLucetteInformationREQ,
  ReservationUpdatePartnerInformationREQ,
  ReservationUpdatePaymentInformationREQ,
  ReservationUpdatePetInformationREQ,
  ReservationUpdatePhotoREQ,
  ReservationUpdatePhotosREQ,
  ReservationUpdateREQ,
  ReservationUpdateSettlementDetailREQ,
  ReservationUpdateStockOrdersREQ,
} from './request/reservation.request';
import { ReservationListRESP, ReservationPartnerRESP } from './response/reservation.response';

export const createReservationApi = async (body?: ReservationCreateREQ) => {
  return await api.post(`reservations`, body);
};

export const funeralReservationKeys = initKeys('funeral-reservation');
export const funeralReservationHomeKeys = initKeys('funeral-reservation-home');
export const funeralDeletedReservationKeys = initKeys('funeral-deleted-reservation');
export const funeralNonAccompanyReservationKeys = initKeys('funeral-non-accompany-reservation');
export const funeralReservationPartnerKeys = initKeys('funeral-reservation-partner');
export const funeralReservationInventoriesKeys = initKeys('funeral-reservation-inventories');

export const getReservationListApi = async (params?: ReservationListREQ) => {
  return api.get<BaseListResponse<ReservationListRESP[]>>('reservations', { params });
};

export const getReservationDetailApi = async (id: number) => {
  if (id < 1 || !id) return;
  return api.get<BaseListResponse<ReservationListRESP>>(`reservations/${id}`);
};

export const getReservationPartnerApi = async (id: number) => {
  if (id < 1 || !id) return;
  return api.get<BaseListResponse<ReservationPartnerRESP>>(`reservations/${id}/partner-information`);
};

export const deleteReservationListApi = async (ids: string, deleteType: DELETE_TYPE) => {
  return await api.delete(`reservations`, { params: { ids, deleteType } });
};

export const updateReservationPetInformationApi = async (id: number, body: ReservationUpdatePetInformationREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/pet-information`, body);
};

export const updateReservationFuneralInformationApi = async (id: number, body: ReservationUpdateFunaralInformationREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/funeral-information`, body);
};

export const updateReservationApi = async (id: number, body: ReservationUpdateREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}`, body);
};

export const updateReservationGuardianInformationApi = async (id: number, body: ReservationUpdateGuardianInformationREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/guardian-information`, body);
};

export const updateReservationPartnerInformationApi = async (id: number, body: ReservationUpdatePartnerInformationREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/partner-information`, body);
};

export const updateReservationPaymentInformationApi = async (id: number, body: ReservationUpdatePaymentInformationREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/payment-information`, body);
};

export const updateReservationLucetteInformationApi = async (id: number, body: ReservationUpdateLucetteInformationREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/lucette-information`, body);
};

export const updateReservationHospitalInformationApi = async (id: number, body: ReservationUpdateHospitalInformationREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/hospital-information`, body);
};

export const updateReservationStockOrdersApi = async (id: number, body: ReservationUpdateStockOrdersREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/stock-orders`, body);
};

export const updateReservationPhotoApi = async (id: number, body: ReservationUpdatePhotoREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/photos`, body);
};

export const updateReservationColumbariumApi = async (id: number, body: ReservationUpdateColumbariumREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/columbarium-information`, body);
};

export const updateReservationInventoriesApi = async (id: number, body: ReservationUpdateInventoriesREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/inventories`, body);
};

export const updateReservationPhotosApi = async (id: number, body: ReservationUpdatePhotosREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/photos`, body);
};

export const updateReservationSettlementDetailApi = async (id: number, body: ReservationUpdateSettlementDetailREQ) => {
  if (id < 1 || !id) return;
  return await api.put(`reservations/${id}/settlements`, body);
};

export const restoreReservationApi = async (ids: string) => {
  return await api.patch(`reservations/restore`, undefined, { params: { ids } });
};

export const updateReservationDetailApi = async (id: number, body: ReservationUpdateDetailREQ) => {
  if (id < 1 || !id) return;
  return await api.patch(`reservations/${id}/reservations`, body);
};

export const createReservationMessageApi = async (reservationIds: number[]) => {
  return await api.post('reservations/messages', { reservationIds });
};

export const getReservationPdfApi = async (id: number) => {
  return api.get<Blob>(`reservations/${id}/pdf`, { responseType: 'blob' });
};

export const sendReservationSurveyApi = async (body: { reservationIds: number[] }) => {
  return await api.post(`reservations/message-surveys`, body);
};
