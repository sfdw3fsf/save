import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type StockOrderListFilter = FilterREQ;

export type StockOrderListREQ = StockOrderListFilter & PagingREQ;

export type StockOrderListUpdateREQ = {
  guardianName?: string;
  childName?: string;
  guardianContact?: string;
  columbariumPlace?: string;
  columbarium?: number;
  home?: number;
  homeColumbarium?: number;
  emotional?: number;
  sophisticated?: number;
  monite?: number;
  frame?: number;
  note?: string;
  material?: string;
  funeralDate?: string;
  applicationDate?: string;
  contactFrequencyFirst?: string;
  contactFrequencySecond?: string;
  contactFrequencyThird?: string;
  stockOrderStatus?: string;
};

export type StockOrderListCreateREQ = {
  guardianName: string;
  childName: string;
  guardianContact: string;
  columbariumPlace: string;
};
