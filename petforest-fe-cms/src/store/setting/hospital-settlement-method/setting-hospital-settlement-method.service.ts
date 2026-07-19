import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { orString, orUndefined } from '@/utils/parser/io.parser';
import { SettingHospitalSettlementMethodDTO } from './dto/setting-hospital-settlement-method.dto';
import { SettingHospitalSettlementMethodListFilter } from './request/setting-hospital-settlement-method.request';
import { SettingHospitalSettlementMethodRESP } from './response/setting-hospital-settlement-method.response';

export const settingHospitalSettlementMethodRespToDto = (
  r: SettingHospitalSettlementMethodRESP,
): SettingHospitalSettlementMethodDTO => {
  return {
    key: r.id,
    id: r.id,
    name: orString(r.name),
    status: orString(r.status),
  };
};

export function settingHospitalSettlementMethodListParamToFilter(
  searchParams: URLSearchParams,
): SettingHospitalSettlementMethodListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as SettingHospitalSettlementMethodListFilter;
}

export const settingSettlementMethodDtoToOptions = (d: SettingHospitalSettlementMethodDTO[]) => {
  return d
    .filter((item) => item.status === ACTIVE_STATE.ACTIVE)
    .map((item) => ({
      text: item.name,
      value: item.id,
      label: item.name,
    }));
};
