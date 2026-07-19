import { FilterREQ } from '@/utils/types/filter.type';

export type SettingVarietiesListFilter = {
  name?: string;
} & FilterREQ;

export type SettingVarietiesListREQ = SettingVarietiesListFilter;

export type SettingVarietiesUpdateREQ = {
  name?: string;
  ordinal?: number;
};
