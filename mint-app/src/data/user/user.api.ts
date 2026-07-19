import { BaseResponse } from '@/types/response.type';
import { api } from '..';
import { UserCalendarMeetingREQ } from './request/user-calendar-meeting.request';
import { UserREQ, UserUpdatePasswordREQ } from './request/user-details-request';
import { UserCalendarMeetingRESP } from './response/user-calendar-meeting.response';

import { ExpertDetailsRESP, UserDetailsRESP } from './response/user-details.response';

export const userDetailsAPI = (): Promise<BaseResponse<UserDetailsRESP>> => api.get('/users/details');

export const userDetailsUpdateAPI = (data: UserREQ): Promise<BaseResponse<void>> => api.put('/users/member', data);

export const userExpertUpdateAPI = (data: any): Promise<BaseResponse<void>> => api.put('/users/expert', data);

export const userPhoneUpdateAPI = (data: string): Promise<BaseResponse<void>> => api.patch('/users/update-phone', data);

export const userCheckPasswordAPI = (data: string): Promise<BaseResponse<void>> => api.post('users/check-password', data);

export const userRequestChangeEmailAPI = (data: any): Promise<BaseResponse<any>> => api.post('/users/change-email', data);

export const userRequestUpdateEmailAPI = (data: any): Promise<BaseResponse<any>> => api.patch('/users/update-email', data);

export const userExpertAPI = (): Promise<BaseResponse<ExpertDetailsRESP>> => api.get('/users/details');

export const userUpdatePasswordAPI = (data: UserUpdatePasswordREQ): Promise<BaseResponse<void>> =>
  api.patch('/users/update-password', data);

export const userCalendarExpertAPI = (params: UserCalendarMeetingREQ): Promise<BaseResponse<UserCalendarMeetingRESP>> =>
  api.get('/users/expert/calendar', { params });

export const userCalendarMemberAPI = (params: UserCalendarMeetingREQ): Promise<BaseResponse<UserCalendarMeetingRESP>> =>
  api.get('/users/member/calendar', { params });
