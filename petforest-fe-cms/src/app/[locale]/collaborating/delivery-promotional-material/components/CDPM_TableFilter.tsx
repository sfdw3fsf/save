import CustomButton from '@/components/common/CustomButton';
import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { HospitalDeliveryListFilter } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { ICONS } from '@/theme';
import { ButtonTypeEnum } from '@/utils/enums/button-type.enum';
import { getCurrentMonthRange } from '@/utils/helpers/date-time.helper';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useTranslation } from 'react-i18next';

type CDPM_TableFilterProps = {
  onFilterChange: DebouncedFunc<(filter: HospitalDeliveryListFilter) => void>;
  onDownloadExcel: () => void;
};

const CDPM_TableFilter = ({ onFilterChange, onDownloadExcel }: CDPM_TableFilterProps) => {
  const { t: tDeliveryPromotionalMaterial } = useTranslation('collaborating_delivery_promotional_material');

  const searchParams = useSearchParams();

  const [startDate, today] = getCurrentMonthRange();

  return (
    <div className='flex flex-1 w-full flex-wrap gap-3 min-h-[38px] lg:flex-nowrap'>
      <CustomButton
        type={ButtonTypeEnum.GHOST}
        className='flex flex-1 md:!hidden'
        icon={<Image src={ICONS.LOOK_GREEN} width={20} height={20} alt='summary' />}>
        {tDeliveryPromotionalMaterial('button.sumary')}
      </CustomButton>
      <CustomRangePicker
        defaultValue={[startDate, today]}
        onChange={(values) => {
          let newValues: [string | number, string | number] = ['', ''];
          if (values[0] && values[1]) {
            newValues = [...(values as [number, number])];
          }
          onFilterChange({ filter: JSON.stringify({ ['requestedAt.gte']: newValues[0], ['requestedAt.lte']: newValues[1] }) });
        }}
        classNameLabel='flex-1 md:w-full min-w-[200px]'
      />

      <div className='flex-1 flex grow  gap-3'>
        <div className='flex-1 min-w-[150px] grow'>
          <SearchInput
            defaultValue={orUndefined(searchParams.get('search'))}
            onChange={(e) => {
              onFilterChange({ search: e.target.value });
            }}
          />
        </div>
        <DownloadButton className='!h-[38px] flex-shrink-0  md:!inline-flex' onClick={onDownloadExcel} />
      </div>
    </div>
  );
};

export default CDPM_TableFilter;
