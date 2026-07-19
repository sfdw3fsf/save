'use client';
import AddButton from '@/components/common/AddButton';
import PageTitle from '@/components/common/page/PageTitle';
import { useReservation } from '@/hook/funeral/useReservation';
import { usePaging } from '@/hook/usePaging';
import { ReservationListFilter } from '@/store/funeral/reservation/request/reservation.request';
import { funeralNonAccompanyReservationKeys, getReservationListApi } from '@/store/funeral/reservation/reservation.api';
import { reservationNonAccompanyListParamsToFilter, reservationRespToDto } from '@/store/funeral/reservation/reservation.service';
import { DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import ReservationContent from '../Tabs/ReservationContent';

const ReservationList = () => {
  const searchParams = useSearchParams();
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { onCreateReservation } = useReservation();

  const { initialFilter, initialPaging } = useMemo(() => {
    const initialFilter = reservationNonAccompanyListParamsToFilter(searchParams);
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

  const { data: reservationListData } = useQuery({
    queryKey: funeralNonAccompanyReservationKeys.list(filter),
    queryFn: () =>
      getReservationListApi({
        ...filter,
        filter: JSON.stringify({
          ...JSON.parse(filter.filter ?? ''),
          'funeralInformation.guardianAttendanceType.in': ['NOT_ACCOMPANY'],
        }),
      }),
    select: (response) => ({
      ...response.data,
      data: response.data.data.map(reservationRespToDto),
    }),
    placeholderData: keepPreviousData,
  });

  return (
    <div className='p-6'>
      <PageTitle
        title={tReservation('non_accompany')}
        actions={
          <AddButton
            onClick={() =>
              onCreateReservation({
                guardianAttendanceType: DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS.NOT_ACCOMPANY,
                date: JSON.parse(filter?.filter ?? '')['funeralInformation.funeralDate.lte'] ?? undefined,
              })
            }
          />
        }
      />

      <div className='mt-4' id='fixedTable'>
        <ReservationContent
          isHiddenExtraButton={true}
          filter={filter}
          handlePageChange={handlePageChange}
          handleTableChange={handleTableChange}
          total={reservationListData?.total || 0}
          data={reservationListData?.data || []}
          handleFilterChange={handleFilterChange}
        />
      </div>
    </div>
  );
};

export default ReservationList;
