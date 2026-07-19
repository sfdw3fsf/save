import { DatePicker, DatePickerProps } from 'antd';
import clsx from 'clsx';
import dayjs, { Dayjs } from 'dayjs';
import { ReactNode } from 'react';
import LabelForm from './form/LabelForm';

export type CustomDateInputProps = {
  placeholder?: string;
  spanCol?: number;
  disabled?: boolean;
  classNameInput?: string;
  className?: string;
  disabledBeforeDate?: boolean;
  beforeDate?: Dayjs;
  isYearOnly?: boolean;
  yearOnly?: string;
  picker?: 'date' | 'week' | 'month' | 'quarter' | 'year';
  bordered?: boolean;
  showTime?: boolean;
  labelWidth?: number;
  onChange?: (value: Dayjs | null) => void;
  classNameLabel?: string;
  label?: ReactNode;
  isRequired?: boolean;
} & DatePickerProps;

const CustomDatePicker = ({
  placeholder = '',
  disabled,
  classNameInput,
  label,
  className,
  disabledBeforeDate = false,
  beforeDate = dayjs(),
  isYearOnly,
  yearOnly,
  picker,
  bordered,
  labelWidth,
  showTime,
  onChange,
  classNameLabel,
  isRequired,
  value,
  defaultValue,
  ...rest
}: CustomDateInputProps) => {
  const disabledDate = (current: Dayjs) => {
    if (isYearOnly) {
      return !dayjs(current).isSame(yearOnly, 'year');
    }
    return disabledBeforeDate ? (current ? current.isBefore(beforeDate, 'day') : false) : false;
  };
  return (
    <LabelForm
      label={label}
      isRequired={isRequired}
      bordered={bordered}
      labelWidth={labelWidth}
      className={className}
      classNameLabel={classNameLabel}>
      <DatePicker
        disabled={disabled}
        placeholder={placeholder}
        className={clsx(`h-[39px] w-full ${classNameInput}`)}
        disabledDate={disabledDate}
        picker={picker}
        onChange={onChange}
        showTime={showTime ? { format: 'HH:mm' } : false}
        defaultValue={defaultValue}
        value={value}
        {...rest}
      />
    </LabelForm>
  );
};

export default CustomDatePicker;
