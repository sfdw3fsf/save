import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type SettingCorrespondentListFilter = FilterREQ;

export type SettingCorrespondentListREQ = SettingCorrespondentListFilter & PagingREQ;

export type SettingCorrespondentUpdateREQ = {
  name?: string;
  managerName?: string;
  contact?: string;
  depositAccount?: string;
  businessRegistration?: string;
  note?: string;
};
