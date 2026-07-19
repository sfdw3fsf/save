import { BaseResponse, PaginationResponse } from '@/types/response.type';
import { api } from '..';
import {
  AvailableExpertsREQ,
  ProjectDetailREQ,
  ProjectsIndivREQ,
  ProposalMeetingsREQ,
  ProposalsREQ,
  RequestProjectREQ,
  SelectExpertREQ,
} from './request/project.request';
import {
  ExpertRESP,
  ProjectDetailRESP,
  ProjectsIndivRESP,
  ProjectStatisticsRESP,
  ProposalTimeSlotRESP,
  RequestProjectRESP,
} from './response/project.response';

const BASE_URL = '/member/projects';

export const getProjectsIndivAPI = (data: ProjectsIndivREQ): Promise<PaginationResponse<ProjectsIndivRESP[]>> =>
  api.get(`${BASE_URL}`, { params: { ...data, direction: 'DESC' } });

export const getProjectsIndivStaticsByStatusAPI = (): Promise<BaseResponse<ProjectStatisticsRESP>> =>
  api.get(`${BASE_URL}/statics-by-status`);

export const requestProjectAPI = (data: RequestProjectREQ): Promise<BaseResponse<RequestProjectRESP>> => api.post(BASE_URL, data);

export const editProjectAPI = (projectId: number, data: RequestProjectREQ): Promise<BaseResponse<void>> =>
  api.put(`${BASE_URL}/${projectId}`, data);

export const getProjectDetailIndivAPI = (data: ProjectDetailREQ): Promise<BaseResponse<ProjectDetailRESP>> =>
  api.get(`${BASE_URL}/${data.projectId}`);

export const getAvailableExpertsAPI = (projectId: number, data: AvailableExpertsREQ): Promise<PaginationResponse<ExpertRESP[]>> =>
  api.get(`${BASE_URL}/${projectId}/available-experts`, { params: data });

export const selectExpertAPI = (projectId: number, data: SelectExpertREQ): Promise<BaseResponse<void>> =>
  api.post(`${BASE_URL}/${projectId}/select-expert`, data);

export const getProposalTimeSlotsMeetingsIndivAPI = (projectId: number): Promise<BaseResponse<ProposalTimeSlotRESP>> =>
  api.get(`${BASE_URL}/${projectId}/proposal-time-slots-meetings`);

export const proposalsIndivAPI = (projectId: number, data: ProposalsREQ): Promise<BaseResponse<void>> =>
  api.post(`${BASE_URL}/${projectId}/proposals`, data);

export const proposalMeetingsIndivAPI = (projectId: number, data: ProposalMeetingsREQ): Promise<BaseResponse<void>> =>
  api.post(`${BASE_URL}/${projectId}/proposal-meetings`, data);

export const confirmProposalMeetingsIndivAPI = (projectId: number): Promise<BaseResponse<void>> =>
  api.post(`${BASE_URL}/${projectId}/confirm-proposal-meetings`);

export const canConfirmProposalIndivAPI = (projectId: number): Promise<BaseResponse<{ canConfirmProposalMeeting: boolean }>> =>
  api.get(`${BASE_URL}/${projectId}/can-confirm-proposal-meeting`);

export const checkProjectCompletedAPI = (projectId: number): Promise<BaseResponse<void>> =>
  api.patch(`${BASE_URL}/${projectId}/check-project-completed`);
