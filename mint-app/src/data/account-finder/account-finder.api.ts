import { BaseResponse } from '@/types/response.type';
import { api } from '..';
import { FindIdConfirmOtpREQ, FindIdRequestOtpREQ } from './request/find-id.request';
import { FindPwdConfirmOtpREQ, FindPwdRequestOtpREQ } from './request/find-password.request';
import { FindIdConfirmOtpRESP } from './response/find-id.response';

export const findIdRequestOtpAPI = (data: FindIdRequestOtpREQ): Promise<BaseResponse<void>> =>
  api.post('/finders/id/request-otp', data);

export const findIdConfirmOtpAPI = (data: FindIdConfirmOtpREQ): Promise<BaseResponse<FindIdConfirmOtpRESP>> =>
  api.post('/finders/id/confirm-otp', data);

export const findPwdRequestOtpAPI = (data: FindPwdRequestOtpREQ): Promise<BaseResponse<void>> =>
  api.post('/finders/pwd/request-otp', data);

export const findPwdConfirmOtpAPI = (data: FindPwdConfirmOtpREQ): Promise<BaseResponse<string>> =>
  api.post('/finders/pwd/confirm-otp', data);
