import { BaseResponse } from '@/types/response.type';
import { api } from '..';
import { EditFeedbacksREQ, FeedbacksREQ } from './request/feedbacks.request';
import { FeedbacksRESP } from './response/feedbacks.response';

const BASE_URL = '/feedbacks';

export const createFeedbacksAPI = (data: FeedbacksREQ): Promise<BaseResponse<FeedbacksRESP>> => api.post(BASE_URL, data);

export const editFeedbacksAPI = (data: EditFeedbacksREQ): Promise<BaseResponse<FeedbacksRESP>> => api.patch(BASE_URL, data);

export const deleteFeedbacksAPI = (id: number): Promise<BaseResponse<void>> => api.delete(`${BASE_URL}/${id}`);
