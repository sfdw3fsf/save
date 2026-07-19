import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import DateRangePicker, { DateRangePickerProps } from '../../date-range-picker/DateRangePicker';

type FormDateRangePickerInput = Pick<DateRangePickerProps, 'className' | 'size' | 'placeholder'>;
export type FormDateRangePickerProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> &
  FormDateRangePickerInput & {
    required?: boolean;
    showSuccessMessage?: boolean;
  };
const FormDateRangePicker = <T extends FieldValues>({ name, control, ...props }: FormDateRangePickerProps<T>): JSX.Element => {
  const {
    field,
    fieldState: { error },
  } = useController({ control, name });
  return (
    <DateRangePicker
      {...props}
      btnContStyle={error ? 'border-red-500 hover:border-red-500' : ''}
      value={field.value}
      onChange={(value) => field.onChange(value)}
    />
  );
};

export default FormDateRangePicker;
