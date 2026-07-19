import CustomRangePicker from '@/components/common/CustomRangePicker';
import CustomSelect from '@/components/common/CustomSelect';
import SearchInput from '@/components/common/SearchInput';
import { ReservationListFilter } from '@/store/funeral/reservation/request/reservation.request';
import { DETAIL_FUNERAL_STATUS_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { getLastMonthRange } from '@/utils/helpers/date-time.helper';
import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';
import { useTranslation } from 'react-i18next';

type HA_TableFilterProps = {
  filter: FilterREQ & PagingREQ;
  onFilterChange: DebouncedFunc<(filter: ReservationListFilter) => void>;
};

const HA_TableFilter = ({ onFilterChange, filter }: HA_TableFilterProps) => {
  const { t: tEnum } = useTranslation('enum');
  const searchParams = useSearchParams();
  const statusOptions = helperOption('status', ENUM_STATUS, tEnum);

  const [startDate, today] = getLastMonthRange();

  return (
    <div className='flex flex-1 w-full flex-wrap gap-3 min-h-[38px] lg:flex-nowrap'>
      <CustomSelect
        className='w-[97px]'
        defaultValue={ENUM_STATUS.INCOMPLETE}
        options={statusOptions}
        onChange={(value) => {
          onFilterChange({
            filter: JSON.stringify({
              ...filter,
              'funeralInformation.processStatus.in':
                value === ENUM_STATUS.COMPLETE
                  ? [DETAIL_FUNERAL_STATUS_OPTIONS.DEADLINE]
                  : Object.values(DETAIL_FUNERAL_STATUS_OPTIONS).filter(
                      (item) => item !== DETAIL_FUNERAL_STATUS_OPTIONS.DEADLINE,
                    ),
            }),
          });
        }}
      />
      <div className='order-3 w-full gap-3 flex flex-col lg:order-none lg:flex-row'>
        <CustomRangePicker
          defaultValue={[startDate, today]}
          onChange={(values) => {
            let newValues: [string | number, string | number] = ['', ''];
            if (values[0] && values[1]) {
              newValues = [...(values as [number, number])];
            }
            onFilterChange({
              filter: JSON.stringify({
                ...filter,
                ['funeralInformation.funeralDate.gte']: newValues[0],
                ['funeralInformation.funeralDate.lte']: newValues[1],
              }),
            });
          }}
          classNameLabel='min-w-[200px]'
        />
        <div className='flex-1 min-w-[150px] grow'>
          <SearchInput
            defaultValue={searchParams.get('search') || ''}
            onChange={(e) => {
              onFilterChange({ search: e.target.value });
            }}
          />
        </div>
      </div>
    </div>
  );
};

export default HA_TableFilter;
