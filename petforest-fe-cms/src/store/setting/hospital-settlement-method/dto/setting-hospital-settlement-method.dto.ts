import { ACTIVE_STATE } from '@/utils/enums/common.enum';

export type SettingHospitalSettlementMethodDTO = {
  key: number;
  id: number;
  name: string;
  status: ACTIVE_STATE;
};
