'use client';

import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import TotalCard from '@/components/common/TotalCard';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { usePaging } from '@/hook/usePaging';
import { SettlementAdjustmentAdditionalCostListDTO } from '@/store/settlement/dto/settlement.dto';
import {
  SettlementAdjustmentAdditionalCostListFilter,
  SettlementAdjustmentAdditionalCostListREQ,
} from '@/store/settlement/request/settlement.request';
import {
  getSettlementAdditionalCostSummaryApi,
  getSettlementAdjustmentAdditionalCostListApi,
  settlementAdjustmentAdditionalCostKeys,
  settlementAdjustmentSummaryKeys,
} from '@/store/settlement/settlement.api';
import {
  settlementAdjustmentAdditionalCostListRespToDto,
  settlementAdjustmentListParamsToFilter,
} from '@/store/settlement/settlement.service';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import SA_AdditionalCostFilter from '../components/SA_AdditionalCostFilter';
import SA_AdditionalTableSummary from '../components/SA_AdditionalCostSummary';
import { SA_AdditionalCostColumns } from '../dataColumns/SA_AdditionalCostColumns';

const page = () => {
  const { t: tSettlement } = useTranslation('settlement');
  const { mutateDownloadExcel } = useDownloadExcel<SettlementAdjustmentAdditionalCostListREQ>({
    fileName: EXCEL_FILE_NAME.SETTLEMENT_MANAGEMENT_ADDITIONAL_COST,
    url: EXCEL_URL.SETTLEMENT_MANAGEMENT_ADDITIONAL_COST,
  });

  const searchParams = useSearchParams();

  const { initialFilter } = useMemo(() => {
    const initialFilter = settlementAdjustmentListParamsToFilter(searchParams);
    return { initialFilter };
  }, []);

  const { filter, handleFilterChange, handleTableChange } = usePaging<SettlementAdjustmentAdditionalCostListFilter>({
    initialFilter,
    debounceTime: 500,
  });

  const { data: listData } = useQuery({
    queryKey: settlementAdjustmentAdditionalCostKeys.list(filter),
    queryFn: () => getSettlementAdjustmentAdditionalCostListApi(filter),
    select: ({ data }) => {
      return { ...data, data: data.data.map(settlementAdjustmentAdditionalCostListRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  console.log('filter', { ...filter });
  const filterObject = JSON.parse(filter.filter || '{}'); // Ensure filter is parsed
  const startDate = filterObject['reservation.funeralInformation.funeralDate.gte'];
  const endDate = filterObject['reservation.funeralInformation.funeralDate.lte'];

  const { data: summaryData } = useQuery({
    queryKey: [settlementAdjustmentSummaryKeys.all, 'additional_cost', startDate, endDate],
    queryFn: () =>
      getSettlementAdditionalCostSummaryApi({
        startDate: startDate?.toString(),
        endDate: endDate?.toString(),
      }),
    select: (response) => response.total,
    placeholderData: keepPreviousData,
  });

  console.log('summaryData', summaryData);

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter });
  };

  return (
    <div className='pt-6 px-6'>
      <PageTitle title={tSettlement('additional_cost')} />

      <div className='inline-flex flex-wrap gap-3 mt-6'>
        <TotalCard title={tSettlement('card.total_amount')} value={summaryData ?? 'N/A'} />
      </div>

      <div className='mt-6'>
        <TableFilter total={listData?.total}>
          <SA_AdditionalCostFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
        </TableFilter>
      </div>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<SettlementAdjustmentAdditionalCostListDTO>
          columns={SA_AdditionalCostColumns()}
          leftHeight={0}
          data={listData?.data || []}
          showIndex
          showPaging={false}
          onChangeTable={handleTableChange}
          serialProps={{ fixed: 'left' }}
          summary={(data) => <SA_AdditionalTableSummary data={data} />}
        />
      </div>
    </div>
  );
};

export default page;
