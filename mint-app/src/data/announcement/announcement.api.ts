import { BaseResponse, PaginationResponse } from '@/types/response.type';
import { ANNOUNCEMENT_SEARCH_TYPE } from '@/utils/enums/data/announcement-type.enum';
import { api } from '..';
import { AnnouncementREQ } from './request/announcement.request';
import { AnnouncementCategory, AnnouncementData, AnnouncementRESP } from './response/announcement.response';

export const getAnnouncement = async (params: AnnouncementREQ): Promise<PaginationResponse<AnnouncementData[]>> => {
  const response: AnnouncementRESP = await api.get('/announcements', {
    params: {
      page: params.page,
      size: params.size,
      searchType: ANNOUNCEMENT_SEARCH_TYPE.ALL,
      search: params.search || null,
      category: params.category || undefined,
    },
  });
  return response;
};

export const getAnnouncementById = (data: Pick<AnnouncementData, 'id'>): Promise<BaseResponse<AnnouncementData>> =>
  api.get(`/announcements/${data.id}`);

export const getAnnouncementCategoriesAPI = (): Promise<BaseResponse<AnnouncementCategory[]>> =>
  api.get('/announcement-categories');
