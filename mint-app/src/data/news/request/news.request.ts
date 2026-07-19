export type NewsREQ = {
  page: number;
  size: number;
  searchType: 'ALL' | 'TITLE' | 'CONTENT';
  search: string;
  category: string;
};
