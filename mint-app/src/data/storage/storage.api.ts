import { BaseResponse } from '@/types/response.type';
import { S3File } from '@/types/s3-file.type';
import { api } from '..';
import { GeneratePresignedUrlREQ } from './request/generate-presigned-url.request';

export const generatePresignedUrlAPI = (params: GeneratePresignedUrlREQ): Promise<BaseResponse<S3File>> =>
  api.get('/storages/generate-presigned-url', { params });
