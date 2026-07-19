import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type ReservationPhotoListFilter = FilterREQ;

export type ReservationPhotoListREQ = ReservationPhotoListFilter & PagingREQ;
