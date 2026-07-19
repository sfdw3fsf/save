import CustomButton from '@/components/common/CustomButton';
import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { LucetteListFilter } from '@/store/funeral/lucette/request/lucette.request';
import { ICONS } from '@/theme';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useTranslation } from 'react-i18next';

type LucetteTableFilterProps = {
  defaultDateRange?: [number, number];
  onFilterChange: DebouncedFunc<(filter: LucetteListFilter) => void>;
  onCreateMessage: () => void;
  onDownloadExcel: () => void;
};

const LucetteTableFilter = ({ onFilterChange, defaultDateRange, onCreateMessage, onDownloadExcel }: LucetteTableFilterProps) => {
  const { t: tCommon } = useTranslation('common');

  const searchParams = useSearchParams();

  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap lg:flex-nowrap'>
      <div className='flex w-full lg:w-auto lg:flex-none'>
        <CustomButton
          onClick={() => onCreateMessage()}
          type='ghost'
          icon={<Image src={ICONS.NOTI} alt='noti' />}
          className='flex-1 !h-[38px] lg:flex-none'>
          {tCommon('button.send_noti')}
        </CustomButton>
      </div>
      <div className='flex w-full lg:w-auto lg:flex-none'>
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

export default LucetteTableFilter;
