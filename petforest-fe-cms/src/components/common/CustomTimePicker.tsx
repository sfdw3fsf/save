import { TimePicker, TimePickerProps } from 'antd';
import clsx from 'clsx';
import dayjs, { Dayjs } from 'dayjs';
import LabelForm from './form/LabelForm';

export type CustomTimePickerInputProps = {
  placeholder?: string;
  disabled?: boolean;
  classNameInput?: string;
  classNameLabel?: string;
  className?: string;
  disabledBeforeDate?: boolean;
  beforeDate?: Dayjs;
  isYearOnly?: boolean;
  yearOnly?: string;
  bordered?: boolean;
  labelWidth?: number;
  onChange?: (value: Dayjs | null) => void;
  label?: string;
  format?: string;
  value?: Dayjs | string | null;
  defaultValue?: Dayjs | string | null;
} & TimePickerProps;

const CustomTimePicker = ({
  placeholder = '',
  disabled,
  classNameInput,
  classNameLabel,
  defaultValue,
  value,
  onChange,
  labelWidth,
  label,
  format,
  ...rest
}: CustomTimePickerInputProps) => {
  const parsedDefaultValue =
    (typeof value === 'string' || typeof value === 'number') && value ? dayjs(defaultValue, format) : defaultValue;

  const parsedValue = (typeof value === 'string' || typeof value === 'number') && value ? dayjs(value, format) : value;

  const handleChange = (val: Dayjs | null) => {
    if (onChange) {
      onChange(val);
    }
  };

  return (
    <LabelForm label={label} labelWidth={labelWidth} className={clsx(classNameLabel)}>
      <TimePicker
        disabled={disabled}
        placeholder={placeholder}
        onChange={handleChange}
        className={`h-[39px] w-full ${classNameInput}`}
        defaultValue={parsedDefaultValue || undefined}
        value={parsedValue || undefined}
        format={format}
        {...rest}
      />
    </LabelForm>
  );
};

export default CustomTimePicker;
