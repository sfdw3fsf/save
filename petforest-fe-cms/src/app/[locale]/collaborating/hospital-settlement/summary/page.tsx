'use client';
import DeleteButton from '@/components/common/DeleteButton';
import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import TotalCard from '@/components/common/TotalCard';
import { useHospitalSettlementMethod } from '@/hook/setting/useHospitalSettlementMethod';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { usePaging } from '@/hook/usePaging';
import useResize from '@/hook/useResize';
import {
  getHospitalSettlementSummaryListApi,
  hospitalSettlementSummaryKeys,
} from '@/store/collaborating-hospital/collaborating-hospital.api';
import {
  hospitalSettlementParamsToFilter,
  hospitalSettlementSummaryRespToDto,
} from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalSettlementSummaryDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { HospitalSettlementSummaryListREQ } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { getCurrentMonthRange } from '@/utils/helpers/date-time.helper';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import CHS_Summary from './components/CHS_Summary';
import CHS_SummaryTableFilter from './components/CHS_SummaryTableFilter';
import { CHS_SummaryColumns } from './data-columns/CHS_Columns';

const DeliveryPromotionalMaterialList = () => {
  const { t: tSettlement } = useTranslation('collaborating_settlement');
  const { isResponsive } = useResize();
  const searchParams = useSearchParams();
  const { settlementMethodOptions } = useHospitalSettlementMethod();
  const { mutateDownloadExcel } = useDownloadExcel<HospitalSettlementSummaryListREQ>({
    fileName: EXCEL_FILE_NAME.HOSPITAL_SETTLEMENT_SUMMARY,
    url: EXCEL_URL.HOSPITAL_SETTLEMENT_SUMMARY,
  });
  const [startDate, endDate] = getCurrentMonthRange();

  const { initialFilter } = useMemo(() => {
    const initialFilter = hospitalSettlementParamsToFilter(searchParams);

    return { initialFilter };
  }, []);

  const { filter, handleFilterChange } = usePaging<HospitalSettlementSummaryListREQ>({
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
    return [startDate, endDate];
  }, [filter]);

  const filterObject = JSON.parse(filter.filter || '{}');
  const startDateFilter = filterObject['reservation.funeralInformation.funeralDate.gte'];
  const endDateFilter = filterObject['reservation.funeralInformation.funeralDate.lte'];

  const { data: hospitalSettlementSummaryListData } = useQuery({
    queryKey: [hospitalSettlementSummaryKeys.list(filter), startDateFilter, endDateFilter],
    queryFn: () =>
      getHospitalSettlementSummaryListApi({
        ...filter,
        startDate: startDateFilter?.toString(),
        endDate: endDateFilter?.toString(),
      }),
    select: (response) => {
      return { total: response.data.data.totalAmount, data: hospitalSettlementSummaryRespToDto(response.data.data) };
    },
    placeholderData: keepPreviousData,
  });

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter });
  };

  return (
    <div className='pt-6'>
      <div className='px-6'>
        <PageTitle title={tSettlement('summary.title')} />
        <div className='mt-6'>
          <TotalCard title={tSettlement('summary.total_card.title')} value={hospitalSettlementSummaryListData?.total || 0} />
        </div>

        <div className='mt-6'>
          <TableFilter total={hospitalSettlementSummaryListData?.data.length}>
            <CHS_SummaryTableFilter
              defaultDateRange={filterDateRange}
              onFilterChange={handleFilterChange}
              onDownloadExcel={onDownloadExcel}
            />
          </TableFilter>
        </div>
      </div>

      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<HospitalSettlementSummaryDTO>
          key={JSON.stringify({ settlementMethodOptions })}
          leftHeight={0}
          columns={CHS_SummaryColumns({ settlementMethodOptions })}
          data={hospitalSettlementSummaryListData?.data || []}
          showPaging={isResponsive ? true : false}
          bordered
          showIndex
          extra={isResponsive ? <DeleteButton className='!inline-flex' /> : undefined}
          summary={(data) => {
            return <CHS_Summary data={data} />;
          }}
        />
      </div>
    </div>
  );
};

export default DeliveryPromotionalMaterialList;
