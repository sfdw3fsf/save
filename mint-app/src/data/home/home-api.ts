import { BaseResponse } from '@/types/response.type';
import { api } from '..';
import { ExpertRESP } from '../project/response/project.response';
import { ExpertIndustryRESP } from './response/expert-industry.response';
import { ExpertMedicalRESP } from './response/expert-medical.response';
import { HomeOverviewRESP } from './response/home-overview.response';
import { BoardRESP, HomePartnersRESP, TestimonialsRESP } from './response/home-partner.response';
import { HomeBanner } from './response/home-banner.response';
import { HomeCapabilitiesRESP } from './response/home-capabilities.response';

export const getNumberExperts = (): Promise<BaseResponse<ExpertIndustryRESP[]>> => api.get('/stats/number-experts');

export const getHomeOverview = (): Promise<BaseResponse<HomeOverviewRESP>> => api.get('/stats/home-overview');

export const getMedicalFieldOverview = (): Promise<BaseResponse<ExpertMedicalRESP[]>> => api.get('/stats/medical-field');

export interface ExpertMedicalFieldParams {
  medicalFieldCategoryIds: number[];
  size: number;
  page: number;
}
export type TestimonialsParams = {
  size?: number;
};
export type PartnerParams = {
  size?: number;
};
export type BoardsParams = {
  size?: number;
};
export const getExpertsByMedicalField = (params: ExpertMedicalFieldParams): Promise<BaseResponse<ExpertRESP[]>> =>
  api.get('/stats/experts/medical-field', { params });
export const getPartnersField = (params: PartnerParams): Promise<BaseResponse<HomePartnersRESP>> =>
  api.get('/stats/partners', { params });
export const getTestimonialsField = (params: TestimonialsParams): Promise<BaseResponse<TestimonialsRESP>> =>
  api.get('/stats/testimonials', { params });
export const getBoardsField = (params: BoardsParams): Promise<BaseResponse<BoardRESP>> => api.get('/stats/boards', { params });

export const getHomeBanners = (params: BoardsParams): Promise<BaseResponse<HomeBanner[]>> =>
  api.get('/stats/banners', { params });

export const getHomeCapabilities = (params: BoardsParams): Promise<BaseResponse<HomeCapabilitiesRESP[]>> =>
  api.get('/stats/capabilities', { params });
