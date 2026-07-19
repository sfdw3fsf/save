import { nameIdValueToOptions } from '@/store/common/common.service';
import { SettingPartnerListREQ } from '@/store/setting/partner/request/setting-partner.request';
import { getDetailSettingPartnerApi, getPartnerListApi, settingPartnerKeys } from '@/store/setting/partner/setting-partner.api';
import { settingPartnerRespToDto } from '@/store/setting/partner/setting-partner.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

type useSettingPartnerProps = {
  filter?: SettingPartnerListREQ;
  partnerId?: number;
};

export function useSettingPartner({ filter = {}, partnerId = 0 }: useSettingPartnerProps) {
  const { data: settingPartnerData } = useQuery({
    queryKey: settingPartnerKeys.list(filter),
    queryFn: () => getPartnerListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(settingPartnerRespToDto), total: response.data.data.length };
    },
    placeholderData: keepPreviousData,
  });

  const { data: settingDetailPartnerData } = useQuery({
    queryKey: settingPartnerKeys.detail(partnerId),
    queryFn: () => getDetailSettingPartnerApi(partnerId),
    select: (response) => {
      return response?.data.data && settingPartnerRespToDto(response?.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const partnerOptions = useMemo(() => {
    if (!settingPartnerData) return [];
    return nameIdValueToOptions(settingPartnerData.data);
  }, [settingPartnerData]);

  return { settingPartnerData, partnerOptions, settingDetailPartnerData };
}
