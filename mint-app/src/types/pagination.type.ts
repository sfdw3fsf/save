export type PaginationRESP = {
  pageSize: number;
  pageNo: number;
  totalPages: number;
  totalElements: number;
  last: boolean;
};

export type PaginationREQ = {
  search?: string;
  category?: string;
  page?: string;
  size?: string;
  source?: string;
  direction?: 'ASC' | 'DESC';
};
