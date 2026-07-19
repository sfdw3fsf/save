import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { ReservationListFilter } from '@/store/funeral/reservation/request/reservation.request';
import { getTodayRange } from '@/utils/helpers/date-time.helper';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type ReservationTableFilterProps = {
  onFilterChange: DebouncedFunc<(filter: ReservationListFilter) => void>;
  onDownloadExcel?: () => void;
  isDeleted?: boolean;
};

const ReservationTableFilter = ({ onFilterChange, onDownloadExcel, isDeleted }: ReservationTableFilterProps) => {
  const searchParams = useSearchParams();
  const [startOfDay, endOfDay] = getTodayRange();

  return (
    <div className='flex flex-1 w-full flex-wrap gap-3 min-h-[38px] lg:flex-nowrap'>
      <div className='flex flex-1 gap-3'>
        <CustomRangePicker
          defaultValue={[startOfDay, endOfDay]}
          allowClear={false}
          onChange={(values) => {
            let newValues: [string | number, string | number] = ['', ''];
            if (values[0] && values[1]) {
              newValues = [...(values as [number, number])];
            }
            onFilterChange({
              filter: JSON.stringify({
                ['funeralInformation.funeralDate.gte']: newValues[0],
                ['funeralInformation.funeralDate.lte']: newValues[1],
              }),
            });
          }}
          classNameLabel=' flex-1 min-w-[200px]'
        />
      </div>
      <div className='flex gap-3 flex-1'>
        <div className='flex-1 grow min-w-[150px]'>
          <SearchInput
            defaultValue={searchParams.get('search') || ''}
            onChange={(e) => {
              onFilterChange({ search: e.target.value });
            }}
          />
        </div>
        {!isDeleted && <DownloadButton className='!h-[38px] flex-shrink-0' onClick={onDownloadExcel} />}
      </div>
    </div>
  );
};

export default ReservationTableFilter;
