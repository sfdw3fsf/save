import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { MaterialInventoryDTO, MaterialInventoryMonthlyHistoryDTO } from '@/store/material/dto/material.dto';
import {
  getMaterialInventoryHistoryListApi,
  getMaterialInventoryMonthlyHistoryApi,
  materialFuneralInventoryHistoryKeys,
  materialInventoryMonthlyHistoryKeys,
} from '@/store/material/material.api';
import {
  materialHistoryListParamsToFilter,
  materialInventoryMonthlyHistoryRespToDto,
  materialInventoryRespToDto,
} from '@/store/material/material.service';
import { MaterialListFilter, MaterialListREQ } from '@/store/material/request/material.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { FilterREQ } from '@/utils/types/filter.type';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo } from 'react';
import MaterialTableDetailFilter from '../../components/MaterialTableDetailFilter';
import { MFS_HistoryColumns } from '../data-columns/MFS_HistoryColumns';

type MFS_HistoryProps = {
  onDataChange: (values: MaterialInventoryMonthlyHistoryDTO[]) => void;
  filterSummary?: FilterREQ;
};
const MFS_History = ({ onDataChange, filterSummary }: MFS_HistoryProps) => {
  const searchParams = useSearchParams();
  const { mutateDownloadExcel } = useDownloadExcel<MaterialListREQ>({
    fileName: EXCEL_FILE_NAME.MATERIAL_INVENTORY_DETAILS,
    url: EXCEL_URL.MATERIAL_HISTORY,
  });

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = materialHistoryListParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<MaterialListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });
  const { selectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<MaterialInventoryDTO>();

  const filterMonth = useMemo<string>(() => {
    let start;
    try {
      const obj = JSON.parse(filter?.filter ?? '');
      start = obj['registerDate.gte'];
    } catch (e) {
      console.error(e);
    }
    if (start) return start;
  }, [filter]);

  const { data: materialInventoryHistoryListData } = useQuery({
    queryKey: materialFuneralInventoryHistoryKeys.list(filter),
    queryFn: () => getMaterialInventoryHistoryListApi({ ...filter, materialType: MATERIAL_TYPE.FUNERAL }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(materialInventoryRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  console.log('filterMonth', filterMonth);
  const { data: materialInventoryMonthlyHistoryData } = useQuery({
    queryKey: [materialInventoryMonthlyHistoryKeys.list(filter), filterSummary, filterMonth],
    queryFn: () =>
      getMaterialInventoryMonthlyHistoryApi({
        materialType: MATERIAL_TYPE.FUNERAL,
        date: filterMonth.toString(),
        sortBy: filterSummary?.sortBy,
        sortOrder: filterSummary?.sortOrder,
      }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(materialInventoryMonthlyHistoryRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const onDownloadExcel = () => {
    mutateDownloadExcel({
      filter: { ...filter, materialType: MATERIAL_TYPE.FUNERAL },
      ids: parseArrayToStringRequest(selectedRowKeys, false),
    });
  };

  useEffect(() => {
    onDataChange(materialInventoryMonthlyHistoryData?.data || []);
  }, [materialInventoryMonthlyHistoryData]);

  return (
    <div className='mt-6'>
      <div className='mt-4'>
        <TableFilter total={materialInventoryHistoryListData?.total}>
          <MaterialTableDetailFilter
            onFilterChange={handleFilterChange}
            materialType={MATERIAL_TYPE.FUNERAL}
            onDownloadExcel={onDownloadExcel}
          />
        </TableFilter>
      </div>

      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<MaterialInventoryDTO>
          columns={MFS_HistoryColumns()}
          data={materialInventoryHistoryListData?.data || []}
          showPaging
          paging={{
            total: materialInventoryHistoryListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>
    </div>
  );
};

export default MFS_History;
