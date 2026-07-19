import { orString, orUndefined } from '@/utils/parser/io.parser';
import { SettingProgressDTO } from './dto/setting-progress.dto';
import { SettingProgressListFilter } from './request/setting-progress.request';
import { SettingProgressRESP, SettingSubProgressRESP } from './response/setting-progress.response';

export const settingProgressRespToDto = (r: SettingProgressRESP | SettingSubProgressRESP): SettingProgressDTO => {
  return {
    key: r.id,
    id: r.id,
    name: orString(r.name),
    ...('subProgresses' in r && { subProgresses: r.subProgresses?.map((subProgress) => subProgress.name).join(' / ') }),
  };
};

export function settingProgressListParamToFilter(searchParams: URLSearchParams): SettingProgressListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as SettingProgressListFilter;
}

export const settingProgressDtoToOptions = (d: SettingProgressDTO[]) => {
  return d.map((item) => ({
    text: item.name,
    label: item.name,
    value: item.id,
  }));
};
