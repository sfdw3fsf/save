import FormOption from '@/components/core/form/form-option/FormOption';
import ArrowDownIcon from '@/components/icons/ArrowDownIcon';
import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

// Define the months and years

// Generate years dynamically
const getYears = (): string[] => {
  const currentYear = new Date().getFullYear();
  return Array.from({ length: 50 }, (_, i) => (currentYear - i).toString());
};

type MonthYearPickerProps = {
  isCurrentWork?: boolean;
  onDateChange: (startMonth: number, startYear: number, endMonth: number, endYear: number) => void;
  initialStartMonth?: number;
  initialStartYear?: number;
  initialEndMonth?: number;
  initialEndYear?: number;
};

type CurrentlyValues = {
  checked: boolean; // This could be true/false for a checkbox-like form option
};
const MonthYearPicker: React.FC<MonthYearPickerProps> = ({
  onDateChange,
  initialStartMonth,
  initialStartYear,
  initialEndMonth,
  initialEndYear,
  isCurrentWork,
}) => {
  const [startMonth, setStartMonth] = useState<string | null>(initialStartMonth?.toString() || null);
  const [startYear, setStartYear] = useState<string | null>(initialStartYear?.toString() || null);
  const [endMonth, setEndMonth] = useState<string | null>(initialEndMonth?.toString() || null);
  const [endYear, setEndYear] = useState<string | null>(initialEndYear?.toString() || null);
  const { t: tCommon } = useTranslation('common');
  const years = getYears();
  const months = [
    { value: '1', label: tCommon('my-page-month.January') },
    { value: '2', label: tCommon('my-page-month.February') },
    { value: '3', label: tCommon('my-page-month.March') },
    { value: '4', label: tCommon('my-page-month.April') },
    { value: '5', label: tCommon('my-page-month.May') },
    { value: '6', label: tCommon('my-page-month.June') },
    { value: '7', label: tCommon('my-page-month.July') },
    { value: '8', label: tCommon('my-page-month.August') },
    { value: '9', label: tCommon('my-page-month.September') },
    { value: '10', label: tCommon('my-page-month.October') },
    { value: '11', label: tCommon('my-page-month.November') },
    { value: '12', label: tCommon('my-page-month.December') },
  ];
  useEffect(() => {
    if (startMonth && startYear && endMonth && endYear) {
      onDateChange(parseInt(startMonth), parseInt(startYear), parseInt(endMonth), parseInt(endYear));
    }
  }, [startMonth, startYear, endMonth, endYear, onDateChange]);

  // Filter the available months for the start month selection
  const filteredStartMonths = months.map((month) => ({
    ...month,
    disabled: !!(startYear === endYear && startYear && parseInt(month.value) > parseInt(endMonth || '0')),
  }));

  // Filter the available months for the end month selection
  const filteredEndMonths = months.map((month) => ({
    ...month,
    disabled: !!(startYear === endYear && parseInt(month.value) < parseInt(startMonth || '0')),
  }));

  // Filter the available years for the start and end year selection
  const filteredStartYears = years.map((year) => ({
    value: year,
    disabled: !!(endYear && parseInt(year) > parseInt(endYear)),
  }));

  const filteredEndYears = years.map((year) => ({
    value: year,
    disabled: !!(parseInt(year) < parseInt(startYear || '0')),
  }));
  const { control, watch } = useForm<CurrentlyValues>({
    defaultValues: {
      checked: false,
    },
  });
  const watchCurrently = watch('checked');
  useEffect(() => {
    if (watchCurrently) {
      const currentYear = new Date().getFullYear().toString();
      const currentMonth = (new Date().getMonth() + 1).toString();
      setEndMonth(currentMonth);
      setEndYear(currentYear);
    }
  }, [watchCurrently]);
  return (
    <div className='w-full'>
      <div className='flex flex-row gap-4 mb-6 w-full'>
        <div className='flex flex-col gap-1 w-full '>
          {isCurrentWork && (
            <div className='mb-6'>
              <FormOption
                checkedValue={true}
                uncheckedValue={false}
                control={control}
                label={tCommon('my-page-placeholder.currently_work')}
                name={'checked'}
                type={'checkbox'}
              />
            </div>
          )}
          <label htmlFor='startMonth' className='font-medium text-slate-800 text-sm'>
            {tCommon('my-page.start_date')} <span className='font-normal text-sm text-comp-icon-tertiary '>(Optional)</span>
          </label>
          <div className='flex flex-row gap-2.5 w-full'>
            <div className='relative w-full'>
              <select
                className='border border-gray-300 p-2 rounded-lg  w-full appearance-none'
                id='startMonth'
                value={startMonth || ''}
                onChange={(e) => setStartMonth(e.target.value)}>
                <option value='' disabled>
                  {tCommon('my-page-placeholder.month')}
                </option>
                {filteredStartMonths.map((month) => (
                  <option key={month.value} value={month.value} disabled={month.disabled}>
                    {month.label}
                  </option>
                ))}
              </select>
              <ArrowDownIcon className='size-4 absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 pointer-events-none' />
            </div>
            <div className='w-full relative '>
              <select
                className='border border-gray-300 p-2 rounded-lg w-full appearance-none'
                id='startYear'
                value={startYear || ''}
                onChange={(e) => setStartYear(e.target.value)}>
                <option value='' disabled>
                  {tCommon('my-page-placeholder.year')}
                </option>
                {filteredStartYears.map((year) => (
                  <option key={year.value} value={year.value} disabled={year.disabled}>
                    {year.value}
                  </option>
                ))}
              </select>
              <ArrowDownIcon className='size-4 absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 pointer-events-none' />
            </div>
          </div>
        </div>
      </div>

      <div className='flex flex-row gap-4  '>
        <div className='flex flex-col gap-1 w-full'>
          <label htmlFor='endMonth' className='font-medium text-slate-800 text-sm'>
            {tCommon('my-page.end_date')} <span className='font-normal text-sm text-comp-icon-tertiary '>(Optional)</span>
          </label>
          <div className='flex flex-row gap-2.5 w-full'>
            <div className='relative w-full'>
              <select
                disabled={watchCurrently}
                className={`appearance-none border border-gray-300 p-2 rounded-lg w-full ${watchCurrently ? 'bg-slate-100' : ''}`}
                id='endMonth'
                value={endMonth || ''}
                onChange={(e) => setEndMonth(e.target.value)}>
                <option value='' disabled>
                  {tCommon('my-page-placeholder.month')}
                </option>
                {filteredEndMonths.map((month) => (
                  <option key={month.value} value={month.value} disabled={month.disabled}>
                    {month.label}
                  </option>
                ))}
              </select>
              <ArrowDownIcon className='size-4 absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 pointer-events-none' />
            </div>
            <div className='relative w-full'>
              <select
                className={`appearance-none border border-gray-300 p-2 rounded-lg w-full ${watchCurrently ? 'bg-slate-100' : ''}`}
                disabled={watchCurrently}
                id='endYear'
                value={endYear || ''}
                onChange={(e) => setEndYear(e.target.value)}>
                <option value='' disabled>
                  {tCommon('my-page-placeholder.year')}
                </option>
                {filteredEndYears.map((year) => (
                  <option key={year.value} value={year.value} disabled={year.disabled}>
                    {year.value}
                  </option>
                ))}
              </select>
              <ArrowDownIcon className='size-4 absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 pointer-events-none' />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MonthYearPicker;
