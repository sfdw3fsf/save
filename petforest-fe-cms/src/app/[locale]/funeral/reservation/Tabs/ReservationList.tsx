import { usePaging } from '@/hook/usePaging';
import { ReservationListFilter } from '@/store/funeral/reservation/request/reservation.request';
import { funeralReservationKeys, getReservationListApi } from '@/store/funeral/reservation/reservation.api';
import { reservationListParamsToFilter, reservationRespToDto } from '@/store/funeral/reservation/reservation.service';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { focusManager, keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import ReservationContent from './ReservationContent';

type ReservationListProps = {
  setCreateReservationDate: (date: number) => void;
};
const ReservationList = ({ setCreateReservationDate }: ReservationListProps) => {
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

  const { data: reservationListData, refetch } = useQuery({
    queryKey: funeralReservationKeys.list(filter),
    queryFn: () => getReservationListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(reservationRespToDto) };
    },
    placeholderData: keepPreviousData,
    refetchOnWindowFocus: true,
  });
  const onRefresh = () => {
    refetch();
  };

  window.addEventListener('focus', () => {
    focusManager.setFocused(true);
  });

  window.addEventListener('blur', () => {
    focusManager.setFocused(false);
  });

  // useEffect(
  //   () => setCreateReservationDate(JSON.parse(filter?.filter ?? '')['funeralInformation.funeralDate.lte'] ?? undefined),
  //   [filter],
  // );

  return (
    <ReservationContent
      filter={filter}
      handlePageChange={handlePageChange}
      handleTableChange={handleTableChange}
      total={reservationListData?.total || 0}
      data={reservationListData?.data || []}
      handleFilterChange={handleFilterChange}
      onRefresh={onRefresh}
    />
  );
};

export default ReservationList;
