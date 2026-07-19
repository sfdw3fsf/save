export type FaqREQ = {
  search?: string;
  category?: string;
  page?: number;
  size?: number;
  sort?: string;
  direction?: 'ASC' | 'DESC';
};
