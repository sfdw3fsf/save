import { PaginationRESP } from './pagination.type';

export type BaseResponse<T> = {
  data: T;
  message?: string;
};

export type PaginationResponse<T> = {
  data: T;
  pagination: PaginationRESP;
};
