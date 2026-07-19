import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type SettingPartnerListFilter = FilterREQ;

export type SettingPartnerListREQ = SettingPartnerListFilter & Partial<PagingREQ>;

export type SettingPartnerUpdateREQ = {
  name?: string;
};
