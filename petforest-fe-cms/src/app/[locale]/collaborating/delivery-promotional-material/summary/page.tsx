'use client';
import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import {
  getHospitalDeliverySummaryApi,
  getHospitalDeliverySummaryListApi,
  hospitalDeliverySummaryKeys,
  hospitalDeliverySummaryListKeys,
} from '@/store/collaborating-hospital/collaborating-hospital.api';
import {
  hospitalDeliveryListParamsToFilter,
  hospitalDeliverySummaryListRespToDto,
  hospitalDeliverySummaryRespToDto,
} from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalDeliverySummaryListDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { HospitalDeliveryListFilter } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { helperFormatCountData } from '@/utils/helpers/collaborating.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import CDPM_Summary from './components/CDPM_Summary';
import CDPM_SummaryCountInfoCard from './components/CDPM_SummaryCountInfoCard';
import CDPM_SummaryTableFilter from './components/CDPM_SummaryTableFilter';
import { CPHD_SummaryColumns } from './data-columns/CDPM_SummaryColumns';

const DeliveryPromotionalMaterialList = () => {
  const { t: tDeliveryPromotionalMaterial } = useTranslation('collaborating_delivery_promotional_material');
  const searchParams = useSearchParams();
  const { mutateDownloadExcel } = useDownloadExcel<HospitalDeliveryListFilter>({
    fileName: EXCEL_FILE_NAME.HOSPITAL_PROMOTIONAL_SUMMARY,
    url: EXCEL_URL.HOSPITAL_PROMOTIONAL_SUMMARY,
  });

  const { initialFilter } = useMemo(() => {
    const initialFilter = hospitalDeliveryListParamsToFilter(searchParams);

    return { initialFilter };
  }, []);

  const { filter, handleFilterChange, handleTableChange } = usePaging<HospitalDeliveryListFilter>({
    initialFilter,
    // initialPaging,
    debounceTime: 500,
  });
  const { selectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<HospitalDeliverySummaryListDTO>();
  const { pageSize, pageNumber, ...filteredParams } = filter;
  const { data: hospitalDeliveryListData } = useQuery({
    queryKey: hospitalDeliverySummaryListKeys.list(filteredParams),
    queryFn: () => getHospitalDeliverySummaryListApi(filteredParams),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(hospitalDeliverySummaryListRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const filterObject = JSON.parse(filter.filter || '{}');
  const startDate = filterObject['requestedAt.gte'];
  const endDate = filterObject['requestedAt.lte'];

  const { data: hospitalDeliverySummaryData } = useQuery({
    queryKey: [hospitalDeliverySummaryKeys.lists(), startDate, endDate],
    queryFn: () =>
      getHospitalDeliverySummaryApi({
        startDate: startDate?.toString(),
        endDate: endDate?.toString(),
      }),
    select: (response) => {
      return response.data.data && hospitalDeliverySummaryRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const formattedData =
    hospitalDeliverySummaryData &&
    helperFormatCountData('summary.info_count_card', hospitalDeliverySummaryData, tDeliveryPromotionalMaterial);

  const date = useMemo(() => {
    const parsedFilter = JSON.parse(filter?.filter || '{}');
    const startDate = parsedFilter['requestedAt.gte'];
    const endDate = parsedFilter['requestedAt.lte'];

    if (startDate && endDate) {
      return dayjs(startDate).format(DATE_FORMAT.DATE.DOT_SHORTEN) + '_' + dayjs(endDate).format(DATE_FORMAT.DATE.DOT_SHORTEN);
    }

    return '';
  }, [filter?.filter]);

  const onDownloadExcel = () => {
    const dynamicName = [EXCEL_FILE_NAME.HOSPITAL_PROMOTIONAL_SUMMARY, date].filter((i) => !!i).join('_');
    mutateDownloadExcel({
      filter,
      ids: parseArrayToStringRequest(selectedRowKeys, false),
      dynamicName,
    });
  };
  return (
    <>
      <div className='mt-6 mx-6'>
        <PageTitle title={tDeliveryPromotionalMaterial('summary.title')} />
        <div className='mt-6'>
          <CDPM_SummaryCountInfoCard
            childrenValue={formattedData || []}
            className='w-full md:w-auto'
            numberOfColumns={3}
            numberOfColumnsResponsive={3}
            title={tDeliveryPromotionalMaterial('info_count_card.title')}
            value={hospitalDeliverySummaryData?.total || 0}
          />
        </div>
        <div className='mt-6'>
          <TableFilter total={hospitalDeliveryListData?.total}>
            <CDPM_SummaryTableFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
          </TableFilter>
        </div>
      </div>

      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<HospitalDeliverySummaryListDTO>
          columns={CPHD_SummaryColumns()}
          leftHeight={100}
          data={hospitalDeliveryListData?.data || []}
          showSelect
          showPaging={false}
          showIndex
          summary={(data) => {
            return <CDPM_Summary data={data} />;
          }}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>
    </>
  );
};

export default DeliveryPromotionalMaterialList;
