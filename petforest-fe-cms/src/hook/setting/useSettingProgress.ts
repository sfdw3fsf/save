import { SettingProgressListREQ } from '@/store/setting/progress/request/setting-progress.request';
import {
  getSettingProgressApi,
  getSubSettingProgressApi,
  settingProgressKeys,
} from '@/store/setting/progress/setting-progress.api';
import { settingProgressDtoToOptions, settingProgressRespToDto } from '@/store/setting/progress/setting-progress.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

type useSettingProgressProps = {
  filter?: SettingProgressListREQ;
  progressId?: number;
};

export function useSettingProgress({ filter = {} as any, progressId = 0 }: useSettingProgressProps) {
  const { data: settingProgressData } = useQuery({
    queryKey: settingProgressKeys.list(filter),
    queryFn: () => getSettingProgressApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(settingProgressRespToDto), total: response.data.data.length };
    },
    placeholderData: keepPreviousData,
  });

  const { data: settingSubProgressDetailData } = useQuery({
    queryKey: settingProgressKeys.detail(progressId),
    queryFn: () => getSubSettingProgressApi(progressId),
    select: (response) => {
      return {
        data: response?.data.data.map(settingProgressRespToDto) ?? [],
        total: response?.data.data.length || 0,
      };
    },
    placeholderData: keepPreviousData,
  });

  const progressOptions = useMemo(() => {
    if (!settingProgressData) return [];
    return settingProgressDtoToOptions(settingProgressData.data);
  }, [settingProgressData]);

  const subProgressOptions = useMemo(() => {
    if (!settingSubProgressDetailData) return [];
    return settingProgressDtoToOptions(settingSubProgressDetailData.data);
  }, [settingSubProgressDetailData]);

  return { settingProgressData, settingSubProgressDetailData, progressOptions, subProgressOptions };
}
