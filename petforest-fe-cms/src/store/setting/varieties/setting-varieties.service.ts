import { orString, orUndefined } from '@/utils/parser/io.parser';
import { SettingVarietiesDTO } from './dto/setting-varieties.dto';
import { SettingVarietiesListFilter } from './request/setting-varieties.request';
import { SettingVarietiesBreedNameRESP, SettingVarietiesRESP } from './response/setting-varieties.response';

export const settingVarietiesRespToDto = (r: SettingVarietiesRESP): SettingVarietiesDTO => {
  return {
    key: r.id,
    id: r.id,
    name: orString(r.name),
    breed: r.petBreeds.map((item) => item.name).join(' / '),
  };
};

export const settingVarietiesBreedRespToDto = (r: SettingVarietiesRESP): SettingVarietiesDTO => {
  return {
    key: r.id,
    id: r.id,
    name: orString(r.name),
  };
};

export function settingVarietiesListParamToFilter(searchParams: URLSearchParams): SettingVarietiesListFilter {
  return {
    name: orUndefined(searchParams.get('name')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as SettingVarietiesListFilter;
}

export const settingVarietiesBreedNameRespToOptions = (r: SettingVarietiesBreedNameRESP[]) => {
  return r.map((item) => ({
    text: item.name,
    label: item.name,
    value: item.name,
  }));
};
