import { api } from '@/config/http';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { BaseResponse } from '@/utils/utils/types/response.type';
import { ReservationCreateREQ, ReservationInquiryREQ, ReservationTimeListREQ } from './request/reservation.request';
import {
  ReservationDetailRESP,
  ReservationPetTypeRESP,
  ReservationRESP,
  ReservationTimeRESP,
} from './response/reservation.response';

export const reservationTimeKeys = initKeys('reservation-time');
export const reservationPetTypeKeys = initKeys('reservation-pet-type');
export const reservationKey = initKeys('reservation');

export const getReservationTimeApi = async (params: ReservationTimeListREQ) => {
  if (!params.date) return;
  return api.get<BaseListResponse<{ reservationTimes: ReservationTimeRESP[] }>>('reservation-times', { params });
};

export const createReservationApi = async (body: ReservationCreateREQ) => {
  return await api.post<BaseListResponse<ReservationRESP>>(`reservations`, body);
};

export const getReservationDetailApi = async (id: number) => {
  if (id < 1 || !id) return;
  return api.get<BaseResponse<ReservationDetailRESP>>(`reservations/${id}`);
};

export const getReservationPetTypeApi = async () => {
  return api.get<BaseListResponse<ReservationPetTypeRESP[]>>('pet-types');
};

export const deleteReservationDetailApi = async (id: number) => {
  if (id < 1 || !id) return;
  return api.delete(`reservations/${id}`);
};

export const getReservationInquiryApi = async (params: ReservationInquiryREQ) => {
  return await api.get(`reservations/inquiries`, { params });
};
