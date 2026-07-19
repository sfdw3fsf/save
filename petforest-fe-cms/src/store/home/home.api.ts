import { api } from '@/config/http';
import { BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { AnnouncementScheduleREQ, AnnouncementUpdateREQ } from './request/home.request';
import { AnnouncementRESP, AnnouncementScheduleRESP } from './response/home.response';

export const updateHomeAnnouncementApi = async (id?: number, body?: AnnouncementUpdateREQ) => {
  return await api.patch(`announcements/${id}`, body);
};

export const homeAnnouncementKeys = initKeys('home-announcement');
export const getHomeAnnouncement = async (params: { date: number }) => {
  return api.get<BaseResponse<AnnouncementRESP>>('announcements', { params });
};

export const homeAnnouncementScheduleKeys = initKeys('home-announcement-schedule');
export const getHomeAnnouncementScheduleApi = async (params: AnnouncementScheduleREQ) => {
  return api.get<BaseResponse<AnnouncementScheduleRESP>>('announcements/schedules', { params });
};
