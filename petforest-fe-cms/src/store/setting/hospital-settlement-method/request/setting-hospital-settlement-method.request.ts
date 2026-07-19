import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type SettingHospitalSettlementMethodListFilter = FilterREQ;

export type SettingHospitalSettlementMethodListREQ = SettingHospitalSettlementMethodListFilter & PagingREQ;

export type SettingHospitalSettlementMethodUpdateREQ = {
  name?: string;
  status?: ACTIVE_STATE;
};

export type SettingHospitalSettlementMethodUpdateStatusREQ = {
  name?: string;
};
