import { api } from '@/config/http';
import { BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { UploadFile } from 'antd';
import axios from 'axios';
import { UploadFileREQ } from './request/file.request';
import { FileRESP, UploadFileRESP } from './response/file.response';

export const uploadFileApi = async (body: UploadFileREQ) => {
  return await api.post<BaseResponse<UploadFileRESP>>(`files`, body, { baseURL: process.env.NEXT_PUBLIC_API_UPLOAD_URL });
};

export const uploadFileToS3Api = async (url: string, file: UploadFile<any>) => {
  return await axios.put(url, file);
};

export const fileKeys = initKeys('file');
export const getFileApi = async (id: number) => {
  return api.get<FileRESP>(`files/${id}`, { baseURL: process.env.NEXT_PUBLIC_API_UPLOAD_URL });
};
