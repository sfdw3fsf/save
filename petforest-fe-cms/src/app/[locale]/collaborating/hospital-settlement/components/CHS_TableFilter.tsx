import CustomButton from '@/components/common/CustomButton';
import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { HospitalSettlementListFilter } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { ICONS } from '@/theme';
import { ButtonTypeEnum } from '@/utils/enums/button-type.enum';
import { getCurrentMonthRange } from '@/utils/helpers/date-time.helper';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useTranslation } from 'react-i18next';

type CHS_TableFilterProps = {
  onOpenSummaryTable: () => void;
  onFilterChange: DebouncedFunc<(filter: HospitalSettlementListFilter) => void>;
  onDownloadExcel: () => void;
};

const CHS_TableFilter = ({ onOpenSummaryTable, onFilterChange, onDownloadExcel }: CHS_TableFilterProps) => {
  const { t: tSettlement } = useTranslation('collaborating_settlement');
  const searchParams = useSearchParams();

  const [startDate, today] = getCurrentMonthRange();
  return (
    <div className='flex flex-1 w-full flex-wrap gap-3 min-h-[38px] lg:flex-nowrap'>
      <CustomButton
        type={ButtonTypeEnum.GHOST}
        onClick={onOpenSummaryTable}
        className=' !h-[39px] flex-1 flex lg:!hidden order-0 lg:order-none'
        icon={<Image src={ICONS.LOOK_GREEN} width={20} height={20} alt='summary' />}>
        {tSettlement('button.settlement_summary')}
      </CustomButton>
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
                ['reservation.funeralInformation.funeralDate.gte']: newValues[0],
                ['reservation.funeralInformation.funeralDate.lte']: newValues[1],
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
      <DownloadButton className='!h-[38px] flex-shrink-0 order-1 lg:order-none' onClick={onDownloadExcel} />
    </div>
  );
};

export default CHS_TableFilter;
