import { nameIdValueToOptions } from '@/store/common/common.service';
import { SettingVarietiesListREQ } from '@/store/setting/varieties/request/setting-varieties.request';
import {
  getSettingVarietiesApi,
  getSettingVarietiesBreedApi,
  settingVarietiesKeys,
} from '@/store/setting/varieties/setting-varieties.api';
import { settingVarietiesBreedRespToDto, settingVarietiesRespToDto } from '@/store/setting/varieties/setting-varieties.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

type useSettingVarietiesProps = {
  filter?: SettingVarietiesListREQ;
  varietyId?: number;
};

export function useSettingVarieties({ filter = {}, varietyId = 0 }: useSettingVarietiesProps) {
  const { data: settingVarietiesData } = useQuery({
    queryKey: settingVarietiesKeys.list(filter),
    queryFn: () => getSettingVarietiesApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(settingVarietiesRespToDto), total: response.data.data.length };
    },
    placeholderData: keepPreviousData,
  });

  const { data: settingVarietyBreedDetailData } = useQuery({
    queryKey: settingVarietiesKeys.detail(varietyId),
    queryFn: () => getSettingVarietiesBreedApi(varietyId),
    select: (response) => {
      return {
        data: response?.data.data.map(settingVarietiesBreedRespToDto) ?? [],
        total: response?.data.data.length || 0,
      };
    },
    placeholderData: keepPreviousData,
  });

  const petTypeOptions = useMemo(() => {
    if (!settingVarietiesData) return [];
    return nameIdValueToOptions(settingVarietiesData.data);
  }, [settingVarietiesData]);

  const petBreedOptions = useMemo(() => {
    if (!settingVarietyBreedDetailData) return [];
    return nameIdValueToOptions(settingVarietyBreedDetailData.data);
  }, [settingVarietyBreedDetailData]);

  return { settingVarietiesData, petTypeOptions, petBreedOptions, settingVarietyBreedDetailData };
}
