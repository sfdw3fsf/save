import CustomRangePicker from '@/components/common/CustomRangePicker';
import SearchInput from '@/components/common/SearchInput';
import { ReservationPhotoListFilter } from '@/store/funeral/reservation-photo/request/reservation-photo.resquest';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type PhotoTableDetailFilterProps = {
  defaultDateRange?: [number, number];
  onFilterChange: DebouncedFunc<(filter: ReservationPhotoListFilter) => void>;
};

const PhotoTableDetailFilter = ({ defaultDateRange, onFilterChange }: PhotoTableDetailFilterProps) => {
  const searchParams = useSearchParams();

  return (
    <div className='grid w-full gap-3 min-h-[38px] grid-cols-1 lg:grid-cols-2'>
      <CustomRangePicker
        className='w-full'
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
      <div className='w-full min-w-[150px]'>
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

export default PhotoTableDetailFilter;
