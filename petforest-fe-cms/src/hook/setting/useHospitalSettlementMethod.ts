import {
  getHospitalSettlementMethodListApi,
  settingHospitalSettlementMethodKeys,
} from '@/store/setting/hospital-settlement-method/setting-hospital-settlement-method.api';
import {
  settingHospitalSettlementMethodRespToDto,
  settingSettlementMethodDtoToOptions,
} from '@/store/setting/hospital-settlement-method/setting-hospital-settlement-method.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

export function useHospitalSettlementMethod() {
  /* Actions */
  const { data: hospitalSettlementMethodData } = useQuery({
    queryKey: settingHospitalSettlementMethodKeys.lists(),
    queryFn: () => getHospitalSettlementMethodListApi(),
    select: (response) => {
      return response.data.data.map(settingHospitalSettlementMethodRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  const settlementMethodOptions = useMemo(() => {
    if (!hospitalSettlementMethodData) return [];
    return settingSettlementMethodDtoToOptions(hospitalSettlementMethodData);
  }, [hospitalSettlementMethodData]);

  return { hospitalSettlementMethodData, settlementMethodOptions };
}
