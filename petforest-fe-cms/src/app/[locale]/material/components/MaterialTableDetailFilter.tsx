import CustomDatePicker from '@/components/common/CustomDatePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { MaterialListFilter } from '@/store/material/request/material.request';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import dayjs from 'dayjs';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type MaterialTableDetailFilterProps = {
  onFilterChange?: DebouncedFunc<(filter: MaterialListFilter) => void>;
  isDetail?: boolean;
  materialType?: MATERIAL_TYPE;
  onDownloadExcel: () => void;
};

const MaterialTableDetailFilter = ({
  onFilterChange,
  isDetail,
  materialType,
  onDownloadExcel,
}: MaterialTableDetailFilterProps) => {
  const searchParams = useSearchParams();

  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap  lg:flex-nowrap'>
      {/* {materialType && [MATERIAL_TYPE.FUNERAL, MATERIAL_TYPE.LUCETTE].includes(materialType) && ( */}
      <CustomDatePicker
        picker='month'
        defaultValue={dayjs()}
        onChange={(date) => {
          const startOfDay = date?.startOf('month').valueOf() || undefined;
          const endOfDay = date?.endOf('month').valueOf() || undefined;
          onFilterChange &&
            onFilterChange({
              filter: JSON.stringify({
                ['registerDate.gte']: startOfDay,
                ['registerDate.lte']: endOfDay,
              }),
            });
        }}
        className=' flex-1 min-w-[150px] lg:flex-none'
      />
      {/* )} */}
      <SearchInput
        defaultValue={!isDetail ? searchParams.get('search')?.toString() : ''}
        onChange={(e) => {
          onFilterChange && onFilterChange({ search: e.target.value });
        }}
      />
      <DownloadButton className='!h-[38px]' onClick={onDownloadExcel} />
    </div>
  );
};

export default MaterialTableDetailFilter;
