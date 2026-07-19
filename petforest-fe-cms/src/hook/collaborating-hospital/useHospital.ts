import { getHospitalSelectListApi, hospitalSelectKeys } from '@/store/collaborating-hospital/collaborating-hospital.api';
import { selectRespToDto } from '@/utils/utils/select.utils';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

export function useHospital(startDate?: number, endDate?: number) {
  /* Actions */
  const { data: hospitalSelectListData } = useQuery({
    queryKey: [hospitalSelectKeys.lists(), startDate, endDate],
    queryFn: () => getHospitalSelectListApi({ startDate, endDate }),
    select: (response) => {
      return response.data.data.map(selectRespToDto);
    },
  });

  const hospitalOptions = useMemo(() => {
    if (!hospitalSelectListData) return [];
    return hospitalSelectListData;
  }, [hospitalSelectListData]);

  return { hospitalOptions };
}
