import CustomButton from '@/components/common/CustomButton';
import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { StockOrderListFilter } from '@/store/stock-order/request/stock-order.request';
import { ICONS } from '@/theme';
import { orUndefined, parseStringToNumber } from '@/utils/parser/io.parser';
import { useQueryClient } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { DebouncedFunc } from 'lodash';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

type StockTableDetailFilterProps = {
  onFilterChange: DebouncedFunc<(filter: StockOrderListFilter) => void>;
  onDownloadExcel: () => void;
  onSendNoti: () => void;
};

const DEFAULT_DATE_RANGE: [number, number] = [
  dayjs().startOf('day').subtract(1, 'month').valueOf(),
  dayjs().endOf('day').valueOf(),
];

const StockTableDetailFilter = ({ onFilterChange, onDownloadExcel, onSendNoti }: StockTableDetailFilterProps) => {
  const { t: tCommon } = useTranslation('common');

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();

  const defaultValueRangePicker = useMemo<[number, number] | undefined>(() => {
    const { start, end } = {
      start: parseStringToNumber(searchParams.get('gte')),
      end: parseStringToNumber(searchParams.get('lte')),
    };
    if (!start || !end) return;
    return [start, end];
  }, [searchParams]);

  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap lg:flex-nowrap'>
      <div className='flex w-full lg:w-auto lg:flex-none'>
        <CustomButton
          onClick={onSendNoti}
          type='ghost'
          icon={<Image src={ICONS.NOTI} alt='noti' />}
          className='flex-1 !h-[38px] lg:flex-none'>
          {tCommon('button.send_noti')}
        </CustomButton>
      </div>
      <div className='flex w-full lg:w-auto lg:flex-none'>
        <CustomRangePicker
          classNameLabel='flex-1 min-w-[150px] lg:flex-none'
          defaultValue={DEFAULT_DATE_RANGE}
          onChange={(values) => {
            let newValues: [string | number, string | number] = ['', ''];
            if (values[0] && values[1]) {
              newValues = [...(values as [number, number])];
            }
            onFilterChange({
              filter: JSON.stringify({ ['funeralDate.gte']: newValues[0], ['funeralDate.lte']: newValues[1] }),
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

export default StockTableDetailFilter;
