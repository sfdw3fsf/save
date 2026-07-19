import { PagingREQ } from '@/utils/types/paging.type';

export type PostListREQ = {
  filter: string;
} & PagingREQ;

export type FaqREQ = {
  filter: string;
};
