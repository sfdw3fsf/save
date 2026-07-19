// faq.response.ts
export type FaqData = {
  id: number;
  question: string;
  answer: string;
  category: string;
};

export type Pagination = {
  pageNo: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
};

export type FaqRESP = {
  data: FaqData[];
  pagination: Pagination;
};
export type FaqsCategory = {
  id: number;
  name: string;
};
