import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { SettlementCouponListFilter } from '@/store/settlement/request/settlement.request';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type SC_TableFilterProps = {
  defaultDateRange?: [number, number];
  onFilterChange: DebouncedFunc<(filter: SettlementCouponListFilter) => void>;
  onDownloadExcel: () => void;
};

const SC_TableFilter = ({ defaultDateRange, onFilterChange, onDownloadExcel }: SC_TableFilterProps) => {
  const searchParams = useSearchParams();

  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap lg:flex-nowrap'>
      <CustomRangePicker
        classNameLabel='flex-1 min-w-[150px] lg:flex-none'
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
            }),
          });
        }}
      />
      <div className='flex gap-3 flex-1'>
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
    </div>
  );
};

export default SC_TableFilter;
