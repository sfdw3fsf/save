import { orString, orUndefined } from '@/utils/parser/io.parser';
import { SettingCorrespondentDTO } from './dto/setting-correspondent.dto';
import { SettingCorrespondentListFilter } from './request/setting-correspondent.request';
import { SettingCorrespondentRESP } from './response/setting-correspondent.response';

export const settingCorrespondentIntialValueForm = {
  name: '',
  managerName: '',
  contact: '',
  depositAccount: '',
  businessRegistration: '',
  note: '',
};

export const settingCorrespondentRespToDto = (r: SettingCorrespondentRESP): SettingCorrespondentDTO => {
  return {
    key: r.id,
    id: r.id,
    name: orString(r.name),
    managerName: orString(r.managerName),
    contact: orString(r.contact),
    depositAccount: orString(r.depositAccount),
    businessRegistration: orString(r.businessRegistration),
    note: orString(r.note),
  };
};

export function settingCorrespondentListParamToFilter(searchParams: URLSearchParams): SettingCorrespondentListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as SettingCorrespondentListFilter;
}

export const correspondentDtoToOptions = (d: SettingCorrespondentDTO[]) => {
  return d.map((item) => ({
    text: item.name,
    value: item.id,
    label: item.name,
  }));
};
