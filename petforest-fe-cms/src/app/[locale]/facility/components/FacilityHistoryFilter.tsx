import CustomDatePicker from '@/components/common/CustomDatePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { FacilityListFilter } from '@/store/facility/request/facility.request';
import dayjs from 'dayjs';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type FacilityHistoryFilterProps = {
  onFilterChange: DebouncedFunc<(filter: FacilityListFilter) => void>;
  onDownloadExcel: () => void;
};

const FacilityHistoryFilter = ({ onFilterChange, onDownloadExcel }: FacilityHistoryFilterProps) => {
  const searchParams = useSearchParams();
  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap  lg:flex-nowrap'>
      <CustomDatePicker
        defaultValue={searchParams.get('date') ? dayjs(Number(searchParams.get('date'))) : null}
        onChange={(date) => {
          const startOfDay = date?.startOf('day').valueOf() || undefined;
          const endOfDay = date?.endOf('day').valueOf() || undefined;

          onFilterChange({
            date: date?.valueOf() || undefined,
            filter: JSON.stringify({
              ['maintenanceDate.gte']: startOfDay,
              ['maintenanceDate.lte']: endOfDay,
            }),
          });
        }}
        className=' flex-1 min-w-[150px] lg:flex-none'
      />
      <div className='flex-1 order-3 min-w-[150px] xs:order-none lg:min-w-0'>
        <SearchInput
          defaultValue={searchParams.get('search')?.toString()}
          onChange={(e) => {
            onFilterChange({ search: e.target.value });
          }}
        />
      </div>
      <DownloadButton className='!h-[38px]' onClick={onDownloadExcel} />
    </div>
  );
};

export default FacilityHistoryFilter;
