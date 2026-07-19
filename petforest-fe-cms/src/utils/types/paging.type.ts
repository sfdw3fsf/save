import { PaginationRESP } from './response.type';

export type PagingREQ = { pageNumber: number; pageSize: number };

export type PagingState = {
  pageNumber: number;
  pageSize: number;
  total?: number;
};

export const initialPagingState: PagingState = {
  pageNumber: 1,
  pageSize: 15,
};

export const initialPagingResponse: PaginationRESP<{ sample: unknown }>['paging'] = {
  page: 1,
  limit: 20,
  cursor: null,
  total: 1,
  nextCursor: null,
};
