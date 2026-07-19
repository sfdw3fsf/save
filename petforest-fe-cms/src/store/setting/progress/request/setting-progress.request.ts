import { FilterREQ } from '@/utils/types/filter.type';

export type SettingProgressListFilter = {
  name?: string;
} & FilterREQ;

export type SettingProgressListREQ = SettingProgressListFilter;

export type SettingProgressUpdateREQ = {
  name?: string;
};
