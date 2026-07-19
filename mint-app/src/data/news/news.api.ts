import { BaseResponse, PaginationResponse } from '@/types/response.type';
import { api } from '..';
import { NewsCategoryDTO, NewsDTO } from './dto/news.dto';
import { NewsREQ } from './request/news.request';
import { NewsRESP } from './response/news.response';

export const listNewsAPI = (data: Partial<NewsREQ>): Promise<PaginationResponse<NewsRESP[]>> =>
  api.get('/news', { params: data });

export const getNewsAPI = (data: Pick<NewsDTO, 'id'>): Promise<BaseResponse<NewsDTO>> => api.get(`/news/${data.id}`);

export const getNewsCategoriesAPI = (): Promise<BaseResponse<NewsCategoryDTO[]>> => api.get('/news-categories');
