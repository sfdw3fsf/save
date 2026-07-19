export type BaseResponse<T> = {
  message: string;
  data: T;
};

export type PaginationRESP<T> = T & {
  paging: {
    page: number;
    limit: number;
    cursor: number | null;
    total: number;
    nextCursor: number | null;
  };
};

export type BaseListResponse<T> = {
  data: T;
  total: number;
};
