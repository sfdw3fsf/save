import { BaseResponse, PaginationResponse } from '@/types/response.type';
import { api } from '..';
import { ProjectDetailREQ, ProjectsExpertREQ, ProposalMeetingsREQ, ProposalsREQ } from './request/project.request';
import { ProjectDetailRESP, ProjectsExpertRESP, ProjectStatisticsRESP, ProposalTimeSlotRESP } from './response/project.response';

const BASE_URL = '/experts/projects';
export const getProjectsExpertAPI = (data: ProjectsExpertREQ): Promise<PaginationResponse<ProjectsExpertRESP[]>> =>
  api.get(`${BASE_URL}`, { params: { ...data, direction: 'DESC' } });

export const getProjectsExpertStaticsByStatusAPI = (): Promise<BaseResponse<ProjectStatisticsRESP>> =>
  api.get(`${BASE_URL}/statics-by-status`);

export const getProjectDetailExpertAPI = (data: ProjectDetailREQ): Promise<BaseResponse<ProjectDetailRESP>> =>
  api.get(`${BASE_URL}/${data.projectId}`);

export const getProposalTimeSlotsMeetingsExpertAPI = (projectId: number): Promise<BaseResponse<ProposalTimeSlotRESP>> =>
  api.get(`${BASE_URL}/${projectId}/proposal-time-slots-meetings`);

export const acceptInitialMeetingsExpertAPI = (projectId: number): Promise<BaseResponse<void>> =>
  api.patch(`${BASE_URL}/${projectId}/accept-initial-meetings`);

export const accceptMatchExpertAPI = (projectId: number): Promise<BaseResponse<void>> =>
  api.patch(`${BASE_URL}/${projectId}/accept-match`);

export const proposalsExpertAPI = (projectId: number, data: ProposalsREQ): Promise<BaseResponse<void>> =>
  api.post(`${BASE_URL}/${projectId}/proposals`, data);

export const proposalMeetingsExpertAPI = (projectId: number, data: ProposalMeetingsREQ): Promise<BaseResponse<void>> =>
  api.post(`${BASE_URL}/${projectId}/proposal-meetings`, data);

export const confirmProposalMeetingsExpertAPI = (projectId: number): Promise<BaseResponse<void>> =>
  api.post(`${BASE_URL}/${projectId}/confirm-proposal-meetings`);

export const canConfirmProposalExpertAPI = (projectId: number): Promise<BaseResponse<{ canConfirmProposalMeeting: boolean }>> =>
  api.get(`${BASE_URL}/${projectId}/can-confirm-proposal-meeting`);
