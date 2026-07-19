import { BaseResponse } from '@/types/response.type';
import { api } from '..';
import { PrivacyREQ } from './request/privacy.request';
import { PrivacyRESP } from './response/privacy.response';

export const privaciesAPI = (params: PrivacyREQ): Promise<BaseResponse<PrivacyRESP[]>> => api.get('/privacies', { params });
