import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { usePaging } from '@/hook/usePaging';
import { OssuaryDetailSettlementHistoryDTO } from '@/store/ossuary/dto/ossuary.dto';
import {
  getOssuaryDetailSettlementHistoryApi,
  ossuaryDetailSettlementHistoryKeys,
  updateOssuaryDetailSettlementHistoryApi,
} from '@/store/ossuary/ossuary.api';
import { ossuaryDetailSettlementHistoryRespToDto } from '@/store/ossuary/ossuary.service';
import {
  OssuaryDetailSettlementHistoryFilter,
  OssuaryDetailSettlementHistoryUpdateREQ,
} from '@/store/ossuary/request/ossuary.request';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { OssuarySettlementHistoryColumns } from '../data-columns/OssuarySettlementHistoryColumns';

type OssuarySettlementHistoryProps = {
  id: number;
};
const OssuarySettlementHistory = ({ id }: OssuarySettlementHistoryProps) => {
  const { t: tCommon } = useTranslation('common');
  const { t: tOssuary } = useTranslation('ossuary');
  const { t: tEnum } = useTranslation('enum');
  const queryClient = useQueryClient();

  const { initialPaging } = useMemo(() => {
    const initialPaging: PagingState = {
      pageSize: initialPagingState.pageSize,
      pageNumber: initialPagingState.pageNumber,
    };
    return { initialPaging };
  }, []);

  const { filter, handlePageChange, handleTableChange } = usePaging<OssuaryDetailSettlementHistoryFilter>({
    initialPaging,
    debounceTime: 500,
    isPushURL: false,
  });

  const { data: ossuarySettlementHistoryData } = useQuery({
    queryKey: ossuaryDetailSettlementHistoryKeys.list(filter),
    queryFn: () => getOssuaryDetailSettlementHistoryApi(id, filter),
    select: (response) => {
      return response?.data && { ...response.data, data: response.data.data.map(ossuaryDetailSettlementHistoryRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateOssuaryDetailSettlementHistory } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: OssuaryDetailSettlementHistoryUpdateREQ }) =>
      updateOssuaryDetailSettlementHistoryApi(id, body),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ossuaryDetailSettlementHistoryKeys.lists() });
      handleSuccess('update');
    },
    onError: handleError,
  });

  const onUpdateOssuaryDetailSettlementHistory = (id: number, values: OssuaryDetailSettlementHistoryUpdateREQ) => {
    mutateUpdateOssuaryDetailSettlementHistory({ id, body: values });
  };

  return (
    <div>
      <div className='mt-4'>
        <TableFilter total={ossuarySettlementHistoryData?.total} />
      </div>

      <div className='mt-4'>
        <CustomTable<OssuaryDetailSettlementHistoryDTO>
          columns={OssuarySettlementHistoryColumns({ tOssuary, tEnum, onUpdateOssuaryDetailSettlementHistory })}
          data={ossuarySettlementHistoryData?.data || []}
          showIndex
          paging={{
            total: ossuarySettlementHistoryData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
        />
      </div>
    </div>
  );
};

export default OssuarySettlementHistory;
