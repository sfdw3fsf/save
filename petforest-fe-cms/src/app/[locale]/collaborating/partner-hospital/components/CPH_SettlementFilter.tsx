import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { HospitalListFilter } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { DebouncedFunc } from 'lodash';

type CPH_SettlementFilterProps = {
  onFilterChange: DebouncedFunc<(filter: HospitalListFilter) => void>;
  onDownloadExcel: () => void;
};
const CPH_SettlementFilter = ({ onFilterChange, onDownloadExcel }: CPH_SettlementFilterProps) => {
  return (
    <div className='flex flex-1 w-full flex-wrap gap-3 min-h-[38px] lg:flex-nowrap'>
      <CustomRangePicker
        onChange={(values) => {
          let newValues: [string | number, string | number] = ['', ''];
          if (values[0] && values[1]) {
            newValues = [...(values as [number, number])];
          }
          onFilterChange({
            filter: JSON.stringify({
              ['reservation.funeralInformation.funeralDate.gte']: newValues[0],
              ['reservation.funeralInformation.funeralDate.lte']: newValues[1],
            }),
          });
        }}
        classNameLabel='flex-1 md:w-full min-w-[200px]'
      />

      <div className='flex-1 flex grow  gap-3'>
        <div className='flex-1 min-w-[150px] grow'>
          <SearchInput
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

export default CPH_SettlementFilter;
