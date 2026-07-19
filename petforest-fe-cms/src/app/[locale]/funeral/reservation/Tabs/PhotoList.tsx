import CustomButton from '@/components/common/CustomButton';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { recipientRequirePopup } from '@/components/popup/RecipientRequirePopup';
import { usePaging } from '@/hook/usePaging';
import { useUser } from '@/hook/user/useUser';
import { useSelectTable } from '@/hook/useSelectTable';
import { ReservationPhotoListDTO } from '@/store/funeral/reservation-photo/dto/reservation-photo.dto';
import { ReservationPhotoListFilter } from '@/store/funeral/reservation-photo/request/reservation-photo.resquest';
import {
  downloadReservationPhotosApi,
  getReservationPhotoListApi,
  reservationPhotoKeys,
} from '@/store/funeral/reservation-photo/reservation-photo.api';
import {
  reservationPhotoListParamsToFilter,
  reservationPhotoListRespToDto,
} from '@/store/funeral/reservation-photo/reservation-photo.service';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { parseDate } from '@/utils/parser/date-time.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { downloadExcel } from '@/utils/utils/download-excel.util';
import { DownloadOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import PhotoTableDetailFilter from '../components/PhotoTableDetailFilter';
import { PhotoColumns } from '../data-columns/PhotoColumns';

const DEFAULT_DATE_RANGE: [number, number] = [
  dayjs().startOf('day').subtract(1, 'month').valueOf(),
  dayjs().endOf('day').valueOf(),
];

const PhotoList = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');

  const searchParams = useSearchParams();

  const { managerOptions } = useUser();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<ReservationPhotoListDTO>();

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = reservationPhotoListParamsToFilter(searchParams, {
      filter: JSON.stringify({
        'reservation.funeralInformation.funeralDate.gte': DEFAULT_DATE_RANGE[0],
        'reservation.funeralInformation.funeralDate.lte': DEFAULT_DATE_RANGE[1],
      }),
    });
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<ReservationPhotoListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const { mutate: mutateDownloadReservationPhoto, isPending: isDownloading } = useMutation({
    mutationFn: ({ ids }: { ids: string; fileName: string }) => downloadReservationPhotosApi({ ids }),
    onSuccess: (resp, { fileName }) => {
      downloadExcel(resp, fileName, 'zip');
    },
    onError: handleError,
  });

  const filterDateRange = useMemo<[number, number]>(() => {
    let start, end;
    try {
      const obj = JSON.parse(filter?.filter ?? '');
      start = obj['reservation.funeralInformation.funeralDate.gte'];
      end = obj['reservation.funeralInformation.funeralDate.lte'];
    } catch (e) {
      console.error(e);
    }
    if (start && end) return [+start, +end];
    return DEFAULT_DATE_RANGE;
  }, [filter]);

  const { data: listData, isFetching: isLoading } = useQuery({
    queryKey: reservationPhotoKeys.list(filter),
    queryFn: () => getReservationPhotoListApi(filter),
    select: ({ data }) => {
      return { ...data, data: data.data.map(reservationPhotoListRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const onDownloadReservationPhotos = () => {
    if (selectedRowKeys.length === 0) {
      recipientRequirePopup();
      return;
    }

    let fileName = '장례사진 모아보기'; // Default name for multiple rows

    if (selectedRowKeys.length === 1) {
      const reservationInfo = listData?.data.find((item) => item.key === selectedRowKeys[0])?.reservation;
      if (reservationInfo) {
        fileName = `${reservationInfo.guardianName}-${reservationInfo.petName}-${parseDate(reservationInfo.funeralDate)}`;
      }
    }

    mutateDownloadReservationPhoto({ ids: parseArrayToStringRequest(selectedRowKeys), fileName });
  };

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={listData?.total}>
          <PhotoTableDetailFilter defaultDateRange={filterDateRange} onFilterChange={handleFilterChange} />
        </TableFilter>
      </div>
      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<ReservationPhotoListDTO>
          key={JSON.stringify({ managerOptions })}
          columns={PhotoColumns({ managerOptions })}
          loading={isLoading}
          data={listData?.data || []}
          showSelect
          showIndex
          extra={
            <div className='flex w-full'>
              <CustomButton
                loading={isDownloading}
                onClick={onDownloadReservationPhotos}
                type={'ghost'}
                icon={<DownloadOutlined />}
                className='!w-[150px] lg:w-auto'>
                {tReservation('photo.button.download_image')}
              </CustomButton>
            </div>
          }
          showPaging
          paging={{
            total: listData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>
    </>
  );
};

export default PhotoList;
