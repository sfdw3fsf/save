import CustomTable from '@/components/common/table/CustomTable';
import { usePaging } from '@/hook/usePaging';
import { HospitalDeliveryListFilter } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { MaterialInventoryMonthlyHistoryDTO } from '@/store/material/dto/material.dto';
import { FilterREQ } from '@/utils/types/filter.type';
import { useEffect } from 'react';
import { MFS_HistorySummaryColumns } from '../data-columns/MFS_HistorySummaryColumns';

type MFS_HistorySummaryProps = {
  data: MaterialInventoryMonthlyHistoryDTO[];
  setFilterSummary: (values: FilterREQ) => void;
};
const MFS_HistorySummary = ({ data, setFilterSummary }: MFS_HistorySummaryProps) => {
  const { filter, handleTableChange } = usePaging<HospitalDeliveryListFilter>({
    debounceTime: 500,
  });

  useEffect(() => {
    setFilterSummary(filter);
  }, [filter]);
  return (
    <div className='pb-6 px-3'>
      <CustomTable<MaterialInventoryMonthlyHistoryDTO>
        onChangeTable={handleTableChange}
        columns={MFS_HistorySummaryColumns()}
        data={data}
        showIndex={false}
      />
    </div>
  );
};

export default MFS_HistorySummary;
