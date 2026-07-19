import CustomButton from '@/components/common/CustomButton';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { usePaging } from '@/hook/usePaging';
import { OssuaryDetailUsageHistoryDTO, UsageInformationFormDTO } from '@/store/ossuary/dto/ossuary.dto';
import {
  getOssuaryContractHistoryPdfApi,
  getOssuaryDetailUsageHistoryApi,
  ossuaryDetailUsageHistoryKeys,
  updateOssuaryDetailUsageHistoryApi,
} from '@/store/ossuary/ossuary.api';
import { ossuaryDetailUsageHistoryRespToDto } from '@/store/ossuary/ossuary.service';
import { OssuaryDetailUsageHistoryFilter } from '@/store/ossuary/request/ossuary.request';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { downloadExcel } from '@/utils/utils/download-excel.util';
import { DownloadOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { OssuaryUsageHistoryColumns } from '../data-columns/OssuaryUsageHistoryColumns';

type OssuaryUsageHistoryProps = {
  id: number;
};

const OssuaryUsageHistory = ({ id }: OssuaryUsageHistoryProps) => {
  const { t: tOssuary } = useTranslation('ossuary');
  const queryClient = useQueryClient();
  const { control, getValues, watch, setValue } = useFormContext<UsageInformationFormDTO>();
  const petName = getValues('childName');

  const { initialPaging } = useMemo(() => {
    const initialPaging: PagingState = {
      pageSize: initialPagingState.pageSize,
      pageNumber: initialPagingState.pageNumber,
    };
    return { initialPaging };
  }, []);

  const { filter, handlePageChange, handleTableChange } = usePaging<OssuaryDetailUsageHistoryFilter>({
    initialPaging,
    debounceTime: 500,
    isPushURL: false,
  });

  const { data: ossuaryUsageHistoryData } = useQuery({
    queryKey: ossuaryDetailUsageHistoryKeys.list(filter),
    queryFn: () => getOssuaryDetailUsageHistoryApi(id, filter),
    select: (response) => {
      return response?.data && { ...response.data, data: response.data.data.map(ossuaryDetailUsageHistoryRespToDto) };
    },
    placeholderData: keepPreviousData,
  });
  
  const { mutate: mutateUpdateOssuaryDetailUsageHistory } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: { note: string } }) => updateOssuaryDetailUsageHistoryApi(id, body),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ossuaryDetailUsageHistoryKeys.lists() });
      handleSuccess('update');
    },
    onError: handleError,
  });

  const { mutate: mutateDownloadPdf } = useMutation({
    mutationFn: (usageId: number) => getOssuaryContractHistoryPdfApi(usageId),
    onSuccess: (resp) => {
      downloadExcel(resp, `${petName}_${ossuaryUsageHistoryData?.data[0].room}_${ossuaryUsageHistoryData?.data[0].place}_봉안당증명서`, 'pdf');
    },
    onError: handleError,
  });

  const onDownloadPdf = (usageId: number) => {
    mutateDownloadPdf(usageId);
  };

  const onUpdateOssuaryDetailUsageHistory = (id: number, values: { note: string }) => {
    mutateUpdateOssuaryDetailUsageHistory({ id, body: values });
  };

  return (
    <div className='mt-4 '>
      <CustomButton
        type={'default'}
        icon={<DownloadOutlined />}
        onClick={() => {
          if (!ossuaryUsageHistoryData?.data[0].id) return;
          onDownloadPdf(ossuaryUsageHistoryData?.data[0].id);
        }}>
        {tOssuary('button.download_bongandang')}
      </CustomButton>

      <div className='mt-4'>
        <TableFilter total={ossuaryUsageHistoryData?.total} />
      </div>

      <div className='mt-4'>
        <CustomTable<OssuaryDetailUsageHistoryDTO>
          columns={OssuaryUsageHistoryColumns({ tOssuary, onUpdateOssuaryDetailUsageHistory, onDownloadPdf })}
          data={ossuaryUsageHistoryData?.data || []}
          showIndex
          paging={{
            total: ossuaryUsageHistoryData?.total,
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

export default OssuaryUsageHistory;
