import CustomButton from '@/components/common/CustomButton';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import CustomText from '@/components/common/Text';
import { usePaging } from '@/hook/usePaging';
import { FuneralMNGTReservationListDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import {
  ReservationListFilter,
  ReservationUpdateFunaralInformationREQ,
} from '@/store/funeral/reservation/request/reservation.request';
import {
  funeralReservationKeys,
  getReservationListApi,
  updateReservationFuneralInformationApi,
} from '@/store/funeral/reservation/reservation.api';
import { reservationRespToDto } from '@/store/funeral/reservation/reservation.service';
import { DETAIL_FUNERAL_STATUS_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { getLastMonthRange } from '@/utils/helpers/date-time.helper';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { DownOutlined, UpOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Collapse, CollapseProps } from 'antd';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { HA_Columns } from '../data-columns/HA_Columns';
import HA_TableFilter from './HA_TableFilter';

export const HA_CollapseTable = () => {
  const { t: tHome } = useTranslation('home');

  const queryClient = useQueryClient();

  const [isExpanded, setIsExpanded] = useState<boolean>(false);
  const [startDate, today] = getLastMonthRange();

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter: ReservationListFilter = {
      filter: JSON.stringify({
        'funeralInformation.processStatus.in': Object.values(DETAIL_FUNERAL_STATUS_OPTIONS).filter(
          (item) => item !== DETAIL_FUNERAL_STATUS_OPTIONS.DEADLINE,
        ),
        'funeralInformation.funeralDate.gte': startDate,
        'funeralInformation.funeralDate.lte': today,
      }),
    };
    const initialPaging: PagingState = {
      pageSize: initialPagingState.pageSize,
      pageNumber: initialPagingState.pageNumber,
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handleFilterChange, handlePageChange, handleTableChange } = usePaging<ReservationListFilter>({
    initialFilter,
    initialPaging,
    debounceTime: 500,
    isPushURL: false,
  });

  const { data: reservationUnaccompanyListData } = useQuery({
    queryKey: funeralReservationKeys.list(filter),
    queryFn: () =>
      getReservationListApi({
        ...filter,
        filter: JSON.stringify({
          ...(filter.filter && JSON.parse(filter.filter)),
          'funeralInformation.guardianAttendanceType.in': ['NOT_ACCOMPANY'],
        }),
      }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(reservationRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateFuneralInformation } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: ReservationUpdateFunaralInformationREQ }) =>
      updateReservationFuneralInformationApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: funeralReservationKeys.list(filter) });
    },
    onError: handleError,
  });

  const onUpdateReservationFuneralInformation = (id: number, value: ReservationUpdateFunaralInformationREQ) => {
    mutateUpdateFuneralInformation({ id, body: value });
  };

  const columns = HA_Columns({ tHome, onUpdateReservationFuneralInformation });

  const renderCollapseCustomHeader = () => (
    <div className='flex gap-4 justify-between md:justify-start items-center md:py-4 '>
      <CustomText variant='headline-1'>{tHome('unaccompanied')}</CustomText>

      <CustomButton
        type='ghost'
        className='!rounded-full'
        onClick={() => {
          setIsExpanded((pre) => !pre);
        }}>
        {isExpanded ? tHome('collapse.collapse') : tHome('collapse.expand')}
        {isExpanded ? <UpOutlined /> : <DownOutlined />}
      </CustomButton>
    </div>
  );

  const items: CollapseProps['items'] = [
    {
      key: 1,
      showArrow: false,
      label: renderCollapseCustomHeader(),
      children: isExpanded && (
        <div className='pb-3'>
          <TableFilter total={reservationUnaccompanyListData?.total}>
            <HA_TableFilter filter={filter} onFilterChange={handleFilterChange} />
          </TableFilter>

          <div className='mt-4'>
            <CustomTable<FuneralMNGTReservationListDTO>
              columns={columns}
              data={reservationUnaccompanyListData?.data || []}
              showPaging
              showSelect={false}
              onPageNumberChange={handlePageChange}
              onChangeTable={handleTableChange}
              paging={{
                total: reservationUnaccompanyListData?.total,
                pageSize: filter.pageSize,
                pageNumber: filter.pageNumber,
              }}
            />
          </div>
        </div>
      ),
    },
  ];

  return <Collapse activeKey={isExpanded ? ['1'] : []} ghost items={items} className='my-2 md:my-6 no-pointer'></Collapse>;
};
