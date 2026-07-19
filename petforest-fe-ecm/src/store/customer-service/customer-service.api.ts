import { api } from '@/config/http';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { BaseResponse } from '@/utils/utils/types/response.type';
import { AnnouncementListREQ, FaqREQ } from './request/customer-service.request';
import { AnnouncementDetailRESP, AnnouncementRESP, ReviewRESP } from './response/customer-service.response';

export const announcementKeys = initKeys('announcement');
export const faqKeys = initKeys('faq');
export const journalKeys = initKeys('journal');
export const pressReportKeys = initKeys('press-report');
export const getPostListApi = async (params: AnnouncementListREQ | FaqREQ) => {
  return api.get<BaseListResponse<AnnouncementRESP[]>>('posts', { params });
};

export const getPostDetailApi = async (id: number) => {
  return api.get<BaseResponse<AnnouncementDetailRESP>>(`posts/${id}`);
};

export const reviewKeys = initKeys('review');
export const getReviewListApi = async () => {
  return api.get<BaseListResponse<ReviewRESP[]>>('reviews');
};
