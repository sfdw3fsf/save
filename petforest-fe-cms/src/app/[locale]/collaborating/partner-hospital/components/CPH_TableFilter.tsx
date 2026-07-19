import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { HospitalListFilter } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { getCurrentMonthRange } from '@/utils/helpers/date-time.helper';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';
import { useEffect } from 'react';

type CPH_TableFilterProps = {
  onFilterChange: DebouncedFunc<(filter: HospitalListFilter) => void>;
  onDownloadExcel: () => void;
};
const CPH_TableFilter = ({ onFilterChange, onDownloadExcel }: CPH_TableFilterProps) => {
  const searchParams = useSearchParams();
  const [startDate, today] = getCurrentMonthRange();

  useEffect(() => {
    onFilterChange({
      filter: JSON.stringify({
        ['createdAt.gte']: startDate,
        ['createdAt.lte']: today,
      }),
    });
  }, []);

  return (
    <div className='flex flex-1 w-full flex-wrap gap-3 min-h-[38px] lg:flex-nowrap'>
      <CustomRangePicker
        defaultValue={[startDate, today]}
        onChange={(values) => {
          let newValues: [string | number, string | number] = ['', ''];
          if (values[0] && values[1]) {
            newValues = [...(values as [number, number])];
          }
          onFilterChange({ filter: JSON.stringify({ ['createdAt.gte']: newValues[0], ['createdAt.lte']: newValues[1] }) });
        }}
        classNameLabel='flex-1 md:w-full min-w-[200px]'
      />

      <div className='flex-1 flex grow  gap-3'>
        <div className='flex-1 min-w-[150px] grow'>
          <SearchInput
            defaultValue={orUndefined(searchParams.get('search'))}
            onChange={(e) => {
              onFilterChange({ search: e.target.value });
            }}
          />
        </div>
        <DownloadButton className='!h-[38px] flex-shrink-0' onClick={onDownloadExcel} />
      </div>
    </div>
  );
};

export default CPH_TableFilter;
