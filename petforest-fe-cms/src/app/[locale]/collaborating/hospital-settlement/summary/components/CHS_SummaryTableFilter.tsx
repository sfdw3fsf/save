import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { HospitalSettlementSummaryListREQ } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type CHS_SummaryTableFilterProps = {
  onFilterChange: DebouncedFunc<(filter: HospitalSettlementSummaryListREQ) => void>;
  onDownloadExcel: () => void;
  defaultDateRange?: [number, number];
};

const CHS_SummaryTableFilter = ({ onFilterChange, onDownloadExcel, defaultDateRange }: CHS_SummaryTableFilterProps) => {
  const searchParams = useSearchParams();

  return (
    <div className='flex flex-1 w-full flex-wrap gap-3 min-h-[38px] lg:flex-nowrap'>
      <div className='w-full gap-3 flex flex-col lg:flex-row'>
        <CustomRangePicker
          defaultValue={defaultDateRange}
          onChange={(values) => {
            let newValues: [string | number, string | number] = ['', ''];
            if (values[0] && values[1]) {
              newValues = [...(values as [number, number])];
            }
            onFilterChange({
              filter: JSON.stringify({
                ['reservation.funeralInformation.funeralDate.gte']: newValues[0],
                ['reservation.funeralInformation.funeralDate.lte']: newValues[1],
                ['createdAt.gte']: newValues[0],
                ['createdAt.lte']: newValues[1],
              }),
            });
          }}
          classNameLabel='flex-1 md:w-full min-w-[200px]'
        />
        <div className='flex-1 min-w-[150px] grow'>
          <SearchInput
            defaultValue={orUndefined(searchParams.get('search'))}
            onChange={(e) => {
              onFilterChange({ search: e.target.value });
            }}
          />
        </div>
      </div>
      <DownloadButton className='!h-[38px] flex-shrink-0 !hidden md:!inline-flex' onClick={onDownloadExcel} />
    </div>
  );
};

export default CHS_SummaryTableFilter;
