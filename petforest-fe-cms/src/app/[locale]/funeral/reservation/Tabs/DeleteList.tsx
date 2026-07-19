'use client';

import { usePaging } from '@/hook/usePaging';
import { ReservationListFilter } from '@/store/funeral/reservation/request/reservation.request';
import { funeralDeletedReservationKeys, getReservationListApi } from '@/store/funeral/reservation/reservation.api';
import { reservationListParamsToFilter, reservationRespToDto } from '@/store/funeral/reservation/reservation.service';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import ReservationContent from './ReservationContent';

export const DeleteList = () => {
  const searchParams = useSearchParams();

  const { initialFilter, initialPaging } = useMemo(() => {
    const initialFilter = reservationListParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialFilter, initialPaging };
  }, []);

  const { filter, handleFilterChange, handlePageChange, handleTableChange } = usePaging<ReservationListFilter>({
    initialFilter,
    initialPaging,
    debounceTime: 500,
  });

  const { data: reservationDeleteListData } = useQuery({
    queryKey: funeralDeletedReservationKeys.list(filter),
    queryFn: () => getReservationListApi({ ...filter, isDeleted: true }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(reservationRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  return (
    <ReservationContent
      data={reservationDeleteListData?.data || []}
      isDeletedList={true}
      filter={filter}
      handlePageChange={handlePageChange}
      handleTableChange={handleTableChange}
      total={reservationDeleteListData?.total || 0}
      handleFilterChange={handleFilterChange}
    />
  );
};
