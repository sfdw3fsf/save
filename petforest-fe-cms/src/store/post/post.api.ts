import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import {
  PopupCreateREQ,
  PopupListREQ,
  PopupUpdateREQ,
  PostListREQ,
  PostUpdateREQ,
  ReviewCreateREQ,
  ReviewUpdateREQ,
} from './request/post.request';
import { PopupRESP, PostDetailRESP, PostRESP, ReviewRESP } from './response/post.response';

//POST
export const createPostApi = async () => {
  return await api.post(`posts`);
};

export const postKeys = initKeys('post');
export const getPostListApi = async (params: PostListREQ) => {
  return api.get<BaseListResponse<PostRESP[]>>('posts', { params });
};

export const updatePostApi = async (id: number, body: PostUpdateREQ) => {
  return await api.patch(`posts/${id}`, body);
};

export const deletePostApi = async (ids: string) => {
  return await api.delete(`posts`, { params: { ids } });
};

export const getPostDetailApi = async (id: number) => {
  if (id < 1) return;
  return api.get<BaseResponse<PostDetailRESP>>(`posts/${id}`);
};

export const updatePostDetailApi = async (id: number, body: PostUpdateREQ) => {
  return api.patch<BaseResponse<PostDetailRESP>>(`posts/${id}`, body);
};

//REVIEW
export const createReviewApi = async (body: ReviewCreateREQ) => {
  return await api.post(`reviews`, body);
};

export const reviewKeys = initKeys('review');
export const getReviewListApi = async () => {
  return api.get<BaseListResponse<ReviewRESP[]>>('reviews');
};

export const updateReviewApi = async (id: number, body: ReviewUpdateREQ) => {
  return await api.patch<BaseResponse<ReviewRESP>>(`reviews/${id}`, body);
};

export const deleteReviewApi = async (id: number) => {
  return await api.delete(`reviews/${id}`);
};

//POPUP
export const createPopupApi = async (body: PopupCreateREQ) => {
  return await api.post(`popups`, body);
};

export const popupKeys = initKeys('popup');
export const getPopupListApi = async (params: PopupListREQ) => {
  return api.get<BaseListResponse<PopupRESP[]>>('popups', { params });
};

export const updatePopupApi = async (id: number, body: PopupUpdateREQ) => {
  return await api.patch(`popups/${id}`, body);
};

export const deletePopupApi = async (ids: string) => {
  return await api.delete(`popups`, { params: { ids } });
};

export const updateImagePopupApi = async (id: number, imageId: number) => {
  return await api.patch(`popups/${id}/upload-image/${imageId}`);
};
