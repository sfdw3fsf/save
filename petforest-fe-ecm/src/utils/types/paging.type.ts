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
