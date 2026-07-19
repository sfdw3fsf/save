import { orString, orUndefined } from '@/utils/parser/io.parser';
import { SettingPartnerDTO } from './dto/setting-partner.dto';
import { SettingPartnerListFilter } from './request/setting-partner.request';
import { SettingPartnerRESP } from './response/setting-partner.response';

export const settingPartnerIntialValueForm = {
  name: '',
  note: '',
};

export const settingPartnerRespToDto = (r: SettingPartnerRESP): SettingPartnerDTO => {
  return {
    key: r.id,
    id: r.id,
    name: orString(r.name),
    note: orString(r.note),
  };
};

export function settingPartnerListParamToFilter(searchParams: URLSearchParams): SettingPartnerListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as SettingPartnerListFilter;
}
