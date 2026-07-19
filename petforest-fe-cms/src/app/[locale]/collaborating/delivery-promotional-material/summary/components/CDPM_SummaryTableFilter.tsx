import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { HospitalDeliveryListFilter } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { getCurrentMonthRange } from '@/utils/helpers/date-time.helper';
import { numberOrUndefined, orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type CDPM_SummaryTableFilterProps = {
  onFilterChange: DebouncedFunc<(filter: HospitalDeliveryListFilter) => void>;
  onDownloadExcel: () => void;
};

const CDPM_SummaryTableFilter = ({ onFilterChange, onDownloadExcel }: CDPM_SummaryTableFilterProps) => {
  const searchParams = useSearchParams();

  const [startDate, today] = getCurrentMonthRange();
  const startDateFromPrevious = numberOrUndefined(searchParams.get('startDate'));
  const endDateFromPrevious = numberOrUndefined(searchParams.get('endDate'));

  const defaultValue = [startDateFromPrevious || startDate, endDateFromPrevious || today];

  return (
    <div className='flex flex-1 w-full flex-wrap gap-3 min-h-[38px] lg:flex-nowrap'>
      <CustomRangePicker
        defaultValue={defaultValue as any}
        onChange={(values) => {
          let newValues: [string | number, string | number] = ['', ''];
          if (values[0] && values[1]) {
            newValues = [...(values as [number, number])];
          }
          onFilterChange({ filter: JSON.stringify({ ['requestedAt.gte']: newValues[0], ['requestedAt.lte']: newValues[1] }) });
        }}
        classNameLabel='flex-1 hidden md:w-full md:block min-w-[200px]'
      />

      <div className='flex-1 flex grow  gap-3'>
        <div className='flex-1 min-w-[150px] grow '>
          <SearchInput
            defaultValue={orUndefined(searchParams.get('search'))}
            onChange={(e) => {
              onFilterChange({ search: e.target.value });
            }}
          />
        </div>
        <DownloadButton className='!h-[38px] flex-shrink-0 !hidden md:!inline-flex' onClick={onDownloadExcel} />
      </div>
    </div>
  );
};

export default CDPM_SummaryTableFilter;
