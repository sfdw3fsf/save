import { SettingServicesListREQ } from '@/store/setting/service/request/setting-services.request';
import { getSettingServiceApi, settingServiceKeys } from '@/store/setting/service/setting-service.api';
import { settingServicesDtoToOptions, settingServicesRespToDto } from '@/store/setting/service/setting-services.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

type useSettingServiceProps = {
  filter?: SettingServicesListREQ;
};

export function useSettingService({ filter = {} }: useSettingServiceProps) {
  const { data: settingServicesData } = useQuery({
    queryKey: settingServiceKeys.list(filter),
    queryFn: () => getSettingServiceApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(settingServicesRespToDto), total: response.data.data.length };
    },
    placeholderData: keepPreviousData,
  });

  const serviceOptions = useMemo(() => {
    if (!settingServicesData) return [];
    return settingServicesDtoToOptions(settingServicesData.data);
  }, [settingServicesData]);

  return { settingServicesData, serviceOptions };
}
