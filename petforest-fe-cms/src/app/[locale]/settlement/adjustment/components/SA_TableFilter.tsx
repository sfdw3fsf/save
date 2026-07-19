import CustomMultiSelect from '@/components/common/CustomMultiSelect';
import CustomRangePicker from '@/components/common/CustomRangePicker';
import DownloadButton from '@/components/common/DownloadButton';
import SearchInput from '@/components/common/SearchInput';
import { SettlementAdjustmentListFilter } from '@/store/settlement/request/settlement.request';
import { PAYMENT_METHOD } from '@/utils/enums/common.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { orUndefined, parseStringToNumber } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';

type SA_TableFilterProps = {
  onFilterChange: DebouncedFunc<(filter: SettlementAdjustmentListFilter) => void>;
  onDownloadExcel: () => void;
};
const DEFAULT_DATE_RANGE: [number, number] = [dayjs().startOf('day').valueOf(), dayjs().endOf('day').valueOf()];

const SA_TableFilter = ({ onFilterChange, onDownloadExcel }: SA_TableFilterProps) => {
  const { t: tEnum } = useTranslation('enum');

  const searchParams = useSearchParams();

  const [search, setSearch] = useState<string>();
  const [selectedPaymentMethods, setSelectedPaymentMethods] = useState<string[]>([]);

  const defaultValueRangePicker = useMemo<[number, number] | undefined>(() => {
    const { start, end } = {
      start: parseStringToNumber(searchParams.get('funeralDate.gte')),
      end: parseStringToNumber(searchParams.get('funeralDate.lte')),
    };
    if (!start || !end) return;
    return [start, end];
  }, [searchParams]);

  const paymentMethodOptions = helperOption('payment_method', PAYMENT_METHOD, tEnum);
  const paymentMethodMapper = {
    [PAYMENT_METHOD.ACCOUNT]: 'accountAmount.gt',
    [PAYMENT_METHOD.CASH]: 'cash.gt',
    [PAYMENT_METHOD.CARD]: 'card.gt',
    [PAYMENT_METHOD.PRE_PAY]: 'prePay.gt',
  } as const;

  useEffect(() => {
    const updatedFilter: Record<string, number | string | undefined> = { search };
    const parsedSearch = parseStringToNumber(search, undefined);
    Object.keys(PAYMENT_METHOD).forEach((method) => {
      const param = paymentMethodMapper[method as PAYMENT_METHOD];
      updatedFilter[param] = selectedPaymentMethods.includes(method) ? parsedSearch : undefined;
    });
    onFilterChange(updatedFilter);
    onFilterChange({
      filter: JSON.stringify({
        ['reservationSettlements.some.reservationMethod.in']: selectedPaymentMethods,
      }),
    });
  }, [selectedPaymentMethods, search]);

  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap lg:flex-nowrap'>
      <div className='flex w-full gap-3 lg:w-auto lg:flex-none'>
        <CustomMultiSelect
          className='w-[246px]'
          value={selectedPaymentMethods}
          options={paymentMethodOptions || []}
          onChange={(values) => {
            setSelectedPaymentMethods(values);
          }}
        />
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
              setSearch(e.target.value);
            }}
          />
        </div>
        <DownloadButton className='!h-[38px] flex-shrink-0' onClick={onDownloadExcel} />
      </div>
    </div>
  );
};

export default SA_TableFilter;
