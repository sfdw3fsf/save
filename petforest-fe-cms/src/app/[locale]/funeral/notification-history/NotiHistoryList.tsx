import CustomModal from '@/components/common/modals/CustomModal';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { usePaging } from '@/hook/usePaging';
import { NotiHistoryDetailDTO, NotiHistoryListDTO } from '@/store/funeral/noti-history/dto/noti-history.dto';
import { getNotiHistoryDetailApi, getNotiHistoryListApi, notiHistoryKeys } from '@/store/funeral/noti-history/noti-history.api';
import {
  notiHistoryDetailRespToDto,
  notiHistoryListParamsToFilter,
  notiHistoryListRespToDto,
} from '@/store/funeral/noti-history/noti-history.service';
import { NotiHistoryListFilter } from '@/store/funeral/noti-history/request/noti-history.request';
import { orNull } from '@/utils/parser/io.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { NotiHistoryDetailModal } from './components/NotiHistoryDetailModal';
import NotiHistoryTableDetailFilter from './components/NotiHistoryTableDetailFilter';
import { NotiHistoryColumns } from './data-columns/NotiHistoryColumns';

const DEFAULT_DATE_RANGE: [number, number] = [dayjs().startOf('month').valueOf(), dayjs().endOf('day').valueOf()];

const NotiHistoryList = () => {
  const { t: tNotiHistory } = useTranslation('funeral_noti_history');

  const searchParams = useSearchParams();

  const [clickedItem, setClickedItem] = useState<NotiHistoryDetailDTO | null>(null);

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = notiHistoryListParamsToFilter(searchParams, {
      filter: JSON.stringify({
        'createdAt.gte': DEFAULT_DATE_RANGE[0],
        'createdAt.lte': DEFAULT_DATE_RANGE[1],
      }),
    });
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<NotiHistoryListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const filterDateRange = useMemo<[number, number]>(() => {
    let start, end;
    try {
      const obj = JSON.parse(filter?.filter ?? '');
      start = obj['createdAt.gte'];
      end = obj['createdAt.lte'];
    } catch (e) {
      console.error(e);
    }
    if (start && end) return [+start, +end];
    return DEFAULT_DATE_RANGE;
  }, [filter]);

  const { data: listData } = useQuery({
    queryKey: notiHistoryKeys.list(filter),
    queryFn: () => getNotiHistoryListApi(filter),
    select: ({ data }) => {
      return { ...data, data: data.data.map(notiHistoryListRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDetailFetch } = useMutation({
    mutationFn: getNotiHistoryDetailApi,
    onSuccess: (response) => {
      setClickedItem(orNull(response?.data?.data && notiHistoryDetailRespToDto(response.data.data)));
    },
    onError: () => {
      setClickedItem(null);
    },
  });

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={listData?.total}>
          <NotiHistoryTableDetailFilter defaultDateRange={filterDateRange} onFilterChange={handleFilterChange} />
        </TableFilter>
      </div>
      <div className='mt-4' id='fixedTable'>
        <CustomTable<NotiHistoryListDTO>
          columns={NotiHistoryColumns({ onRecordSelect: mutateDetailFetch })}
          data={listData?.data || []}
          showIndex
          showPaging
          paging={{
            total: listData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          serialProps={{ fixed: 'left' }}
        />
      </div>
      <CustomModal isOpen={!!clickedItem} onClose={() => setClickedItem(null)} title={tNotiHistory('noti_talk')}>
        <NotiHistoryDetailModal item={clickedItem} />
      </CustomModal>
    </>
  );
};

export default NotiHistoryList;
