import { ReservationCreateREQ } from '@/store/funeral/reservation/request/reservation.request';
import {
  createReservationApi,
  funeralNonAccompanyReservationKeys,
  funeralReservationKeys,
  getReservationListApi,
} from '@/store/funeral/reservation/reservation.api';
import { reservationRespToDto } from '@/store/funeral/reservation/reservation.service';
import { reservationToOptions } from '@/store/manager/reservation-time/manager-reservation-time.service';
import { SORT_ORDER } from '@/utils/enums/sort-order.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useMemo } from 'react';

export function useReservation() {
  const queryClient = useQueryClient();
  const { mutate: mutateCreateReservation } = useMutation({
    mutationFn: (body?: ReservationCreateREQ) => createReservationApi(body),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: funeralReservationKeys.lists() });
      queryClient.invalidateQueries({ queryKey: funeralNonAccompanyReservationKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { data: reservationListData } = useQuery({
    queryKey: funeralReservationKeys.lists(),
    queryFn: () => getReservationListApi({} as any),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(reservationRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: reservationAccompanyListData } = useQuery({
    queryKey: funeralReservationKeys.list({ accompany: 'accompany' }),
    queryFn: () =>
      getReservationListApi({
        filter: JSON.stringify({ 'funeralInformation.guardianAttendanceType.in': ['ACCOMPANY'] }),
      } as any),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(reservationRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: reservationUnaccompanyListData } = useQuery({
    queryKey: funeralReservationKeys.list({ accompany: 'unaccompany' }),
    queryFn: () =>
      getReservationListApi({
        filter: JSON.stringify({ 'funeralInformation.guardianAttendanceType.in': ['NOT_ACCOMPANY'] }),
        sortBy: 'funeralDate',
        sortOrder: SORT_ORDER.DESC,
      } as any),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(reservationRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const reservationOptions = useMemo(() => {
    if (!reservationListData) return [];
    return reservationToOptions(reservationListData.data);
  }, [reservationListData]);

  const reservationAccompanyOptions = useMemo(() => {
    if (!reservationAccompanyListData) return [];
    return reservationToOptions(reservationAccompanyListData.data);
  }, [reservationAccompanyListData]);

  const reservationUnaccompanyOptions = useMemo(() => {
    if (!reservationUnaccompanyListData) return [];
    return reservationToOptions(reservationUnaccompanyListData.data);
  }, [reservationUnaccompanyListData]);

  const onCreateReservation = (body?: ReservationCreateREQ) => {
    mutateCreateReservation(body);
  };

  return {
    reservationListData,
    reservationOptions,
    reservationAccompanyListData,
    reservationAccompanyOptions,
    reservationUnaccompanyListData,
    reservationUnaccompanyOptions,
    onCreateReservation,
  };
}
