import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { Nil } from '@/utils/types/utils.type';

export type SettingHospitalSettlementMethodRESP = {
  id: number;
  name: Nil<string>;
  status: ACTIVE_STATE;
  ordinal: Nil<string>;
};
