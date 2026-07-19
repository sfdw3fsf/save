import CustomRangePicker from '@/components/common/CustomRangePicker';
import SearchInput from '@/components/common/SearchInput';
import { NotiHistoryListFilter } from '@/store/funeral/noti-history/request/noti-history.request';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type NotiHistoryTableDetailFilterProps = {
  defaultDateRange?: [number, number];
  onFilterChange: DebouncedFunc<(filter: NotiHistoryListFilter) => void>;
};

const NotiHistoryTableDetailFilter = ({ defaultDateRange, onFilterChange }: NotiHistoryTableDetailFilterProps) => {
  const searchParams = useSearchParams();

  return (
    <div className='flex flex-1 w-full flex-wrap gap-3 min-h-[38px] lg:flex-nowrap'>
      <CustomRangePicker
        classNameLabel=' flex-1 min-w-[280px]'
        defaultValue={defaultDateRange}
        onChange={(values) => {
          let newValues: [string | number, string | number] = ['', ''];
          if (values[0] && values[1]) {
            newValues = [...(values as [number, number])];
          }
          onFilterChange({
            filter: JSON.stringify({
              ['createdAt.gte']: newValues[0],
              ['createdAt.lte']: newValues[1],
            }),
          });
        }}
      />

      <div className='flex-1 grow min-w-[280px]'>
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

export default NotiHistoryTableDetailFilter;
