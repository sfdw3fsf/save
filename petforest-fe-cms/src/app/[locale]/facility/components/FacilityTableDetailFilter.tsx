import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { FacilityListFilter } from '@/store/facility/request/facility.request';
import { DebouncedFunc } from 'lodash';

type FacilityTableDetailFilterProps = {
  onFilterChange: DebouncedFunc<(filter: FacilityListFilter) => void>;
  onDownloadExcel: () => void;
};

const FacilityTableDetailFilter = ({ onFilterChange, onDownloadExcel }: FacilityTableDetailFilterProps) => {
  return (
    <div className='flex gap-3 min-h-[38px] flex-wrap lg:flex-nowrap'>
      <CustomRangePicker
        onChange={(values) => {
          let newValues: [string | number, string | number] = ['', ''];
          if (values[0] && values[1]) {
            newValues = [...(values as [number, number])];
          }
          onFilterChange({
            filter: JSON.stringify({ ['maintenanceDate.gte']: newValues[0], ['maintenanceDate.lte']: newValues[1] }),
          });
        }}
        className='flex-1 min-w-[150px] lg:flex-none'
      />
      <div className='flex-1 order-3 min-w-[150px] sm:order-none lg:min-w-0'>
        <SearchInput
          onChange={(e) => {
            onFilterChange({ search: e.target.value });
          }}
        />
      </div>
      <DownloadButton className='!h-[38px]' onClick={onDownloadExcel} />
    </div>
  );
};

export default FacilityTableDetailFilter;
