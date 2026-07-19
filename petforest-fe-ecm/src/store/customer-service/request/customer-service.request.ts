import { PagingREQ } from '@/utils/types/paging.type';

export type AnnouncementListREQ = {
  filter: string;
} & PagingREQ;

export type FaqREQ = {
  filter: string;
};
