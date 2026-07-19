import SearchInput from '@/components/common/SearchInput';
import { FilterREQ } from '@/utils/types/filter.type';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type BranchTableFilterProps = {
  onFilterChange: DebouncedFunc<(filter: FilterREQ) => void>;
};

const BranchTableFilter = ({ onFilterChange }: BranchTableFilterProps) => {
  const searchParams = useSearchParams();
  return (
    <div className='flex-1 order-3 min-w-[150px] sm:order-none lg:min-w-0'>
      <SearchInput
        defaultValue={searchParams.get('search')?.toString()}
        onChange={(e) => {
          onFilterChange({ search: e.target.value });
        }}
      />
    </div>
  );
};

export default BranchTableFilter;
