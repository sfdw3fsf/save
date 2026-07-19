import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { FacilityListFilter } from '@/store/facility/request/facility.request';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type FacilityTableFilterProps = {
  filter: FacilityListFilter;
  onFilterChange: DebouncedFunc<(filter: FacilityListFilter) => void>;
  onDownloadExcel: () => void;
};

const FacilityTableFilter = ({ filter, onFilterChange, onDownloadExcel }: FacilityTableFilterProps) => {
  const searchParams = useSearchParams();

  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap lg:flex-nowrap'>
      <div className='flex-1 grow min-w-[150px]'>
        <SearchInput
          defaultValue={orUndefined(searchParams.get('search'))}
          onChange={(e) => {
            onFilterChange({ search: e.target.value });
          }}
        />
      </div>
      <DownloadButton className='!h-[38px] flex-shrink-0' onClick={onDownloadExcel} />
    </div>
  );
};

export default FacilityTableFilter;
