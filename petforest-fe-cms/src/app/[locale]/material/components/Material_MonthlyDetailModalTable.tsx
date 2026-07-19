import CustomTable from '@/components/common/table/CustomTable';
import { MaterialInventoryMonthlyDTO } from '@/store/material/dto/material.dto';
import { getMaterialInventoryMonthlyApi, materialInventoryMonthlyKeys } from '@/store/material/material.api';
import { materialInventoryMonthlyRespToDto } from '@/store/material/material.service';
import { initialPagingState, PagingREQ } from '@/utils/types/paging.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { MaterialMonthlyDetailColumns } from '../data-columns/MaterialMonthlyDetailColumns';

type MaterialMonthlyDetailModalTableProps = {
  id: number;
};

const MaterialMonthlyDetailModalTable = ({ id }: MaterialMonthlyDetailModalTableProps) => {
  const [paging, setPaging] = useState<PagingREQ>({
    pageNumber: initialPagingState.pageNumber,
    pageSize: initialPagingState.pageSize,
  });

  const { data: materialInventoryData } = useQuery({
    queryKey: materialInventoryMonthlyKeys.list({ paging, id }),
    queryFn: () => getMaterialInventoryMonthlyApi(id, paging),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(materialInventoryMonthlyRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const handlePageChange = ({ pageNumber, pageSize }: PagingREQ) => {
    setPaging({ pageNumber, pageSize });
  };

  return (
    <div className='md:pb-6'>
      <CustomTable<MaterialInventoryMonthlyDTO>
        columns={MaterialMonthlyDetailColumns()}
        data={materialInventoryData?.data || []}
        showSelect={false}
        sticky
        showIndex={false}
        showPaging
        onPageNumberChange={handlePageChange}
      />
    </div>
  );
};

export default MaterialMonthlyDetailModalTable;
