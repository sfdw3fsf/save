import CalendarBlankIcon from '@/components/icons/CalendarBlankIcon';
import i18n, { I18nLanguage } from '@/i18n';
import { toLocaleDateString } from '@/utils/helpers/datetime.helper';
import { DatePicker, DatesProvider } from '@mantine/dates';
import dayjs from 'dayjs';
import 'dayjs/locale/en';
import 'dayjs/locale/ko';
import { useEffect, useMemo, useState } from 'react';
import { twMerge } from 'tailwind-merge';
import Button from '../button/Button';

export type DateRangePickerProps = {
  className?: string;
  btnContStyle?: string;
  value: [Date | null, Date | null];
  onChange: (value: [Date | null, Date | null]) => void;
  placeholder?: string;
  size?: 'sm' | 'md' | 'lg';
};
const DateRangePicker = ({
  value,
  onChange,
  placeholder,
  className,
  size = 'sm',
  btnContStyle,
}: DateRangePickerProps): JSX.Element => {
  const language = i18n.language as I18nLanguage;
  useEffect(() => {
    dayjs.locale(language);
  }, [language]);

  const [open, setOpen] = useState(false);
  const renderLabel = useMemo(() => {
    if (!!value[0] && !!value[1]) return `${toLocaleDateString(value[0])} - ${toLocaleDateString(value[1])}`;
    if (value[0]) return toLocaleDateString(value[0]);
    return placeholder || '';
  }, [placeholder, value]);
  return (
    <DatesProvider settings={{ locale: language }}>
      <div className={twMerge('relative inline-block w-full', className)} onMouseLeave={() => setOpen(false)}>
        <div onClickCapture={() => setOpen((prev) => !prev)}>
          <Button
            className={twMerge('w-full', btnContStyle)}
            labelContStyle='justify-between w-full'
            labelStyle={twMerge(!!value[0] && !!value[1] && 'text-slate-800')}
            rightIcon={<CalendarBlankIcon />}>
            {renderLabel}
          </Button>
        </div>
        {open && (
          <div className='block w-full absolute pt-1 z-[10]'>
            <div className='overflow-hidden w-full flex items-center justify-center bg-white'>
              <DatePicker
                type='range'
                value={value}
                size={size}
                onChange={(value) => onChange?.(value)}
                className='border border-slate-200 rounded-lg p-3'
                styles={{
                  day: {
                    color: 'black',
                  },
                }}
              />
            </div>
          </div>
        )}
      </div>
    </DatesProvider>
  );
};

export default DateRangePicker;
