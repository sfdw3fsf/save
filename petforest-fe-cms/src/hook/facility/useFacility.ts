import {
  facilityHistoryKeys,
  getDetailFacilityHistoryApi,
  getFacilityCompanyNamesApi,
  updateFacilityMaintenanceHistoriesApi,
} from '@/store/facility/facility.api';
import { facilityCompanyNamesDtoToOptions, facilityHistoryDetailRespToDto } from '@/store/facility/facility.service';
import { FacilityHistoryCreateUpdateREQ } from '@/store/facility/request/facility.request';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { keepPreviousData, QueryKey, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useMemo } from 'react';

type useFacilityProps = {
  facilityHistoryId?: number;
  queryKey?: QueryKey;
};

export function useFacility({ facilityHistoryId, queryKey }: useFacilityProps = {}) {
  const queryClient = useQueryClient();

  const { data: facilityCompanyNamesData } = useQuery({
    queryKey: facilityHistoryKeys.lists(),
    queryFn: () => getFacilityCompanyNamesApi(),
    select: (response) => {
      return { ...response.data };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateFacilityHistory } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: FacilityHistoryCreateUpdateREQ }) =>
      updateFacilityMaintenanceHistoriesApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: queryKey });
    },
    onError: handleError,
  });

  const { data: facilityDetailHistoryData } = useQuery({
    queryKey: facilityHistoryKeys.detail(facilityHistoryId || 0),
    queryFn: () => getDetailFacilityHistoryApi(facilityHistoryId || 0),
    select: (response) => {
      return response?.data.data && facilityHistoryDetailRespToDto(response?.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const companyOptions = useMemo(() => {
    if (!facilityCompanyNamesData) return [];
    return facilityCompanyNamesDtoToOptions(facilityCompanyNamesData?.data);
  }, [facilityCompanyNamesData]);

  return { facilityCompanyNamesData, companyOptions, facilityDetailHistoryData, mutateUpdateFacilityHistory };
}
