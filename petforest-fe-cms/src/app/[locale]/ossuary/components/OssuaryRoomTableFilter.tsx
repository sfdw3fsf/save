import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { OssuaryListFilter } from '@/store/ossuary/request/ossuary.request';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';

type OssuaryRoomTableFilterProps = {
  onFilterChange: DebouncedFunc<(filter: OssuaryListFilter) => void>;
  onDownloadExcel: () => void;
};

const OssuaryRoomTableFilter = ({ onFilterChange, onDownloadExcel }: OssuaryRoomTableFilterProps) => {
  const searchParams = useSearchParams();
  return (
    <div className='flex gap-3 flex-1'>
      <div className='flex-1 grow min-w-[150px]'>
        <SearchInput
          defaultValue={searchParams.get('search') || ''}
          onChange={(e) => {
            onFilterChange({ search: e.target.value });
          }}
        />
      </div>
      <DownloadButton className='!h-[38px] flex-shrink-0' onClick={onDownloadExcel} />
    </div>
  );
};

export default OssuaryRoomTableFilter;
