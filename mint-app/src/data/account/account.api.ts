import { BaseResponse } from '@/types/response.type';
import { api } from '..';
import { CheckUsernameDuplicateREQ } from './request/check-username-duplicate.request';
import { ConfirmEmailOtpREQ } from './request/confirm-email-otp.request';
import { LogoutREQ } from './request/logout.request';
import { RegisterExpertREQ } from './request/register-expert.request';
import { RegisterMemberREQ } from './request/register-member.request';
import { RegisterREQ } from './request/register.request';
import { ResetPasswordREQ } from './request/reset-password.request';
import { SocialLoginREQ } from './request/social-login.request';
import { VerifyEmailREQ } from './request/verify-email.request';
import { AdvisoryFieldCategoryRESP } from './response/advisory-field-category.response';
import { BusinessFieldCategoryRESP } from './response/business-field-category.response';
import { MedicalFieldCategoryRESP } from './response/medical-field-category.response';
import { RegisterRESP } from './response/register.response';
import { SocialLoginRESP } from './response/social-login.response';

export const resetPasswordAPI = (data: ResetPasswordREQ): Promise<BaseResponse<void>> =>
  api.patch('/accounts/reset-password', data);

export const logoutAPI = (data: LogoutREQ): Promise<BaseResponse<void>> => api.post('/accounts/logout', data);

export const socialLoginAPI = (data: SocialLoginREQ): Promise<BaseResponse<SocialLoginRESP>> =>
  api.post('/accounts/social-login', data);

export const businessFieldCategoriesAPI = (): Promise<BaseResponse<BusinessFieldCategoryRESP[]>> =>
  api.get('/accounts/business-field-categories');

export const advisoryFieldCategoriesAPI = (): Promise<BaseResponse<AdvisoryFieldCategoryRESP[]>> =>
  api.get('/accounts/advisory-field-categories');

export const medicalFieldCategoriesAPI = (): Promise<BaseResponse<MedicalFieldCategoryRESP[]>> =>
  api.get('/accounts/medical-field-categories');

export const verifyEmailAPI = (data: VerifyEmailREQ): Promise<BaseResponse<void>> => api.post('/accounts/verify-email', data);

export const confirmEmailOtpAPI = (data: ConfirmEmailOtpREQ): Promise<BaseResponse<void>> =>
  api.post('/accounts/confirm-email-otp', data);

export const checkUsernameDuplicateAPI = (params: CheckUsernameDuplicateREQ): Promise<BaseResponse<void>> =>
  api.post('/accounts/check-username-duplicate', null, { params });

export const registerAPI = (data: RegisterREQ): Promise<BaseResponse<RegisterRESP>> => api.post('/accounts/register', data);

export const registerMemberAPI = (data: RegisterMemberREQ): Promise<BaseResponse<void>> =>
  api.post('/accounts/register-member', data);

export const registerExpertAPI = (data: RegisterExpertREQ): Promise<BaseResponse<void>> =>
  api.post('/accounts/register-expert', data);
