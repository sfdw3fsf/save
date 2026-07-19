import { BaseResponse, PaginationResponse } from '@/types/response.type';
import { api } from '..';
import { InquiryREQ } from './request/inquiry.request';
import { Inquiry, InquiryCategory, InquiryRESP } from './response/inquiry.response';

export const getInquiry = async (params: InquiryREQ): Promise<PaginationResponse<Inquiry[]>> => {
  const response: InquiryRESP = await api.get('/inquiries', {
    params,
  });
  return response;
};

export const createInquiry = (data: InquiryREQ): Promise<BaseResponse<InquiryRESP>> => api.post('/inquiries', data);

export const getInquiryCategory = async (): Promise<BaseResponse<InquiryCategory[]>> => api.get('/inquiries/categories');

export const getInquiryById = (data: Pick<Inquiry, 'id'>): Promise<BaseResponse<Inquiry>> => api.get(`/inquiries/${data.id}`);
