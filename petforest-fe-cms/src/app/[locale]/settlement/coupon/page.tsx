'use client';
import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import TotalCard from '@/components/common/TotalCard';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettlementCouponListDTO } from '@/store/settlement/dto/settlement.dto';
import {
  SettlementCouponListFilter,
  SettlementCouponListREQ,
  SettlementCouponListUpdateREQ,
} from '@/store/settlement/request/settlement.request';
import {
  getSettlementCouponListApi,
  settlementCouponKeys,
  updateSettlementCouponListApi,
} from '@/store/settlement/settlement.api';
import { settlementCouponListParamsToFilter, settlementCouponListRespToDto } from '@/store/settlement/settlement.service';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import SC_TableFilter from './components/SC_TableFilter';
import { SettlementCouponColumns } from './dataColumns/SettlementCouponColumns';

const DEFAULT_DATE_RANGE: [number, number] = [
  dayjs().startOf('day').subtract(1, 'month').valueOf(),
  dayjs().startOf('day').valueOf(),
];

const page = () => {
  const { t: tSettlement } = useTranslation('settlement');

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { mutateDownloadExcel } = useDownloadExcel<SettlementCouponListREQ>({
    fileName: EXCEL_FILE_NAME.COUPON_MANAGEMENT,
    url: EXCEL_URL.COUPON_MANAGEMENT,
  });

  const { selectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettlementCouponListDTO>();

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = settlementCouponListParamsToFilter(searchParams, {
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

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<SettlementCouponListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
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

  const { data: listData } = useQuery({
    queryKey: settlementCouponKeys.list(filter),
    queryFn: () => getSettlementCouponListApi(filter),
    select: ({ data }) => {
      return { ...data, data: data.data.map(settlementCouponListRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateListUpdate } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettlementCouponListUpdateREQ }) => updateSettlementCouponListApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settlementCouponKeys.lists() });
    },
    onError: handleError,
  });

  const onListUpdate = (id: number, body: SettlementCouponListUpdateREQ) => {
    mutateListUpdate({ id, body });
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  return (
    <div>
      <PageTitle title={tSettlement('coupon') as string} />
      <div className='inline-flex flex-wrap gap-3 mt-6'>
        <TotalCard title={tSettlement('card.total_number')} value={listData?.count ?? 0} />
        <TotalCard title={tSettlement('card.total_amount')} value={listData?.totalAmount ?? 0} />
      </div>

      <div className='mt-6'>
        <TableFilter total={listData?.count}>
          <SC_TableFilter
            defaultDateRange={filterDateRange}
            onFilterChange={handleFilterChange}
            onDownloadExcel={onDownloadExcel}
          />
        </TableFilter>
      </div>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<SettlementCouponListDTO>
          columns={SettlementCouponColumns({ onListUpdate })}
          data={listData?.data || []}
          showSelect
          showIndex
          showPaging
          paging={{
            total: listData?.count,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          serialProps={{ fixed: 'left' }}
        />
      </div>
    </div>
  );
};

export default page;
