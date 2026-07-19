import { BaseResponse } from '@/types/response.type';
import { api } from '..';
import { LoginREQ } from './request/login.request';
import { LoginRESP } from './response/login.response';

export const loginAPI = (data: LoginREQ): Promise<BaseResponse<LoginRESP>> => api.post('/accounts/login', data);
