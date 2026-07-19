import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { SettlementAdjustmentAdditionalCostListFilter } from '@/store/settlement/request/settlement.request';
import { orUndefined, parseStringToNumber } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';

type SA_AdditionalCostFilterProps = {
  onFilterChange: DebouncedFunc<(filter: SettlementAdjustmentAdditionalCostListFilter) => void>;
  onDownloadExcel: () => void;
};

const DEFAULT_DATE_RANGE: [number, number] = [dayjs().startOf('day').valueOf(), dayjs().endOf('day').valueOf()];

const SA_AdditionalCostFilter = ({ onFilterChange, onDownloadExcel }: SA_AdditionalCostFilterProps) => {
  const searchParams = useSearchParams();

  const defaultValueRangePicker = useMemo<[number, number] | undefined>(() => {
    const { start, end } = {
      start: parseStringToNumber(searchParams.get('reservation.funeralInformation.funeralDate.gte')),
      end: parseStringToNumber(searchParams.get('funeralDate.lte')),
    };
    if (!start || !end) return;
    return [start, end];
  }, [searchParams]);

  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap lg:flex-nowrap'>
      <div className='flex w-full gap-3 lg:w-auto lg:flex-none'>
        <CustomRangePicker
          classNameLabel='flex-1 min-w-[150px] lg:flex-none'
          defaultValue={DEFAULT_DATE_RANGE}
          onChange={(values) => {
            let newValues: [string | number, string | number] = ['', ''];
            if (values[0] && values[1]) {
              newValues = [...(values as [number, number])];
            }
            onFilterChange({
              filter: JSON.stringify({
                ['reservation.funeralInformation.funeralDate.gte']: values[0],
                ['reservation.funeralInformation.funeralDate.lte']: values[1],
              }),
            });
          }}
        />
      </div>
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

export default SA_AdditionalCostFilter;
