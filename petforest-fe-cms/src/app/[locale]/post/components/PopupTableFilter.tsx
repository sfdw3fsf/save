import CustomRangePicker from '@/components/common/CustomRangePicker';
import SearchInput from '@/components/common/SearchInput';
import { PopupListFilter } from '@/store/post/request/post.request';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type PopupTableFilterProps = {
  filter: PopupListFilter;
  onFilterChange: DebouncedFunc<(filter: PopupListFilter) => void>;
};

const PopupTableFilter = ({ filter, onFilterChange }: PopupTableFilterProps) => {
  const searchParams = useSearchParams();

  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap  lg:flex-nowrap'>
      <CustomRangePicker
        onChange={(values) => {
          let newValues: [string | number, string | number] = ['', ''];
          if (values[0] && values[1]) {
            newValues = [...(values as [number, number])];
          }
          onFilterChange({ filter: JSON.stringify({ ['startAt.gte']: newValues[0], ['endAt.lte']: newValues[1] }) });
        }}
        className=' flex-1 min-w-[150px] lg:flex-none'
      />
      <div className='flex-1 order-3 min-w-[150px] xs:order-none lg:min-w-0'>
        <SearchInput
          defaultValue={orUndefined(searchParams.get('search'))}
          onChange={(e) => {
            onFilterChange({ search: e.target.value });
          }}
        />
      </div>
    </div>
  );
};

export default PopupTableFilter;
