import { DatePicker } from 'antd';
import { RangePickerProps } from 'antd/es/date-picker';
import dayjs, { Dayjs } from 'dayjs';
import { useMemo } from 'react';
import LabelForm from './form/LabelForm';

export type CustomDateStartEndInputProps = {
  placeholder?: [string, string];
  disabled?: boolean;
  classNameInput?: string;
  className?: string;
  classNameLabel?: string;
  bordered?: boolean;
  labelWidth?: number;
  label?: string;
  defaultValue?: [number | undefined, number | undefined] | null | undefined;
  value?: [Dayjs | number | string | undefined, Dayjs | number | null | string | undefined] | null | undefined;
  onChange?: (value: [number | undefined, number | undefined]) => void;
} & Omit<RangePickerProps, 'value' | 'onChange' | 'defaultValue'>;

const { RangePicker } = DatePicker;

const CustomRangePicker = ({
  label,
  disabled,
  classNameInput,
  classNameLabel,
  bordered,
  labelWidth,
  placeholder,
  value,
  className,
  onChange,
  defaultValue,
  ...rest
}: CustomDateStartEndInputProps) => {
  const rangeValue = useMemo(() => {
    if (!value) return;
    if (value[0] && dayjs(value[0]).isValid() && value[1] && dayjs(value[1]).isValid()) {
      return [dayjs(value[0]), dayjs(value[1])];
    }
  }, [value]);
  return (
    <LabelForm label={label} bordered={bordered} labelWidth={labelWidth} className={classNameLabel}>
      <RangePicker
        disabled={disabled}
        className={`h-[39px] w-full ${classNameInput} ${className}`}
        bordered={bordered}
        defaultValue={
          defaultValue && defaultValue[0] !== undefined && defaultValue[1] !== undefined
            ? [dayjs(defaultValue[0]).startOf('day'), dayjs(defaultValue[1]).startOf('day')]
            : undefined
        }
        placeholder={placeholder}
        value={value?.map((v: any) => (v ? dayjs(v) : undefined)) as [Dayjs | undefined, Dayjs | undefined] | undefined}
        onChange={(values) => {
          if (!onChange) return;
          if (values && values[0]?.isValid() && values[1]?.isValid()) {
            onChange([values[0].startOf('day').valueOf(), values[1].endOf('day').valueOf()]);
          } else {
            onChange([undefined, undefined]);
          }
        }}
        {...rest}
      />
    </LabelForm>
  );
};

export default CustomRangePicker;
