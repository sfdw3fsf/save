import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { MaterialInventoryDTO } from '@/store/material/dto/material.dto';
import { getMaterialInventoryHistoryListApi, materialLucetteInventoryHistoryKeys } from '@/store/material/material.api';
import { materialHistoryListParamsToFilter, materialInventoryRespToDto } from '@/store/material/material.service';
import { MaterialListFilter, MaterialListREQ } from '@/store/material/request/material.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import MaterialTableDetailFilter from '../../components/MaterialTableDetailFilter';
import MLS_HistoryColumns from '../data-columns/MLS_HistoryColumns';

const MLS_History = () => {
  const searchParams = useSearchParams();
  const { mutateDownloadExcel } = useDownloadExcel<MaterialListREQ>({
    fileName: EXCEL_FILE_NAME.MATERIAL_LUCETTE_INVENTORY_DETAILS,
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

  const { data: materialInventoryHistoryListData } = useQuery({
    queryKey: materialLucetteInventoryHistoryKeys.list(filter),
    queryFn: () => getMaterialInventoryHistoryListApi({ ...filter, materialType: MATERIAL_TYPE.LUCETTE }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(materialInventoryRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const onDownloadExcel = () => {
    mutateDownloadExcel({
      filter: { ...filter, materialType: MATERIAL_TYPE.LUCETTE },
      ids: parseArrayToStringRequest(selectedRowKeys, false),
    });
  };

  return (
    <div className='mt-6'>
      <div className='mt-4'>
        <TableFilter total={materialInventoryHistoryListData?.total}>
          <MaterialTableDetailFilter
            onFilterChange={handleFilterChange}
            materialType={MATERIAL_TYPE.LUCETTE}
            onDownloadExcel={onDownloadExcel}
          />
        </TableFilter>
      </div>

      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<MaterialInventoryDTO>
          columns={MLS_HistoryColumns()}
          data={materialInventoryHistoryListData?.data || []}
          showPaging
          showSelect
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

export default MLS_History;
