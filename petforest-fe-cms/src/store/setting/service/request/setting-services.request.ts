import { FilterREQ } from '@/utils/types/filter.type';

export type SettingServicesListFilter = FilterREQ;

export type SettingServicesListREQ = SettingServicesListFilter;

export type SettingServiceUpdateREQ = {
  name?: string;
  type?: string;
  price?: number;
  color?: string;
};

export type SettingServiceUpdateDefaultMaterialREQ = {
  materialId?: number;
  quantity?: string;
};
