import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type NotiHistoryListFilter = FilterREQ;

export type NotiHistoryListREQ = NotiHistoryListFilter & PagingREQ;
