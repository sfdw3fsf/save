import { BaseResponse } from '@/types/response.type';
import { api } from '..';
import { TermREQ } from './request/term.request';
import { TermRESP } from './response/term.response';

const BASE_URL = '/terms';

export const termsAPI = (params: TermREQ): Promise<BaseResponse<TermRESP[]>> => api.get(BASE_URL, { params });
