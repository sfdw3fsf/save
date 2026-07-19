import {
  getReservationTimeSettingApi,
  reservationTimeSettingKeys,
} from '@/store/manager/reservation-time/manager-reservation-time.api';
import { reservationTimeRespToOptions } from '@/store/manager/reservation-time/manager-reservation-time.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

export function useReservationTime() {
  const { data: reservationTimeSettingListData } = useQuery({
    queryKey: reservationTimeSettingKeys.lists(),
    queryFn: () => getReservationTimeSettingApi(),
    select: (response) => {
      return response.data.data.filter((item) => item.usedInReservationTime).sort((a, b) => a.time.localeCompare(b.time));
    },
    placeholderData: keepPreviousData,
  });

  const reservationTimeOptions = useMemo(() => {
    if (!reservationTimeSettingListData) return [];
    return reservationTimeRespToOptions(reservationTimeSettingListData);
  }, [reservationTimeSettingListData]);

  return { reservationTimeSettingListData, reservationTimeOptions };
}
