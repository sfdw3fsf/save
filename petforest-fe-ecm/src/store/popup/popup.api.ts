import { api } from '@/config/http';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { PopupRESP } from './response/popup.response';

export const popupKeys = initKeys('popup');
export const getPopupApi = async (params: { date: number }) => {
  return api.get<BaseListResponse<PopupRESP[]>>('popups', { params });
};
