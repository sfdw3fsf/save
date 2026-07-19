// faq.api.ts
import { BaseResponse, PaginationResponse } from '@/types/response.type';
import { api } from '..';
import { FaqREQ } from './request/faq.request';
import { FaqData, FaqRESP, FaqsCategory } from './response/faq.response';

export const getFaqs = async (params: FaqREQ): Promise<PaginationResponse<FaqData[]>> => {
  const response: FaqRESP = await api.get('/faqs', {
    params: {
      search: params.search,
      category: params.category || undefined,
      page: params.page,
      size: params.size,
      sort: params.sort,
      direction: params.direction,
    },
  });

  return response;
};
export const getFaqsCategoriesAPI = (): Promise<BaseResponse<FaqsCategory[]>> => api.get('/faqs/categories');
