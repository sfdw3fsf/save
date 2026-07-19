import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import Option, { OptionProps } from '../../option/Option';

export type FormOptionInputValue = string | number | boolean;

export type FormOptionInput = Omit<OptionProps, 'name' | 'checked' | 'onChange'> & {
  checkedValue?: FormOptionInputValue;
  uncheckedValue?: FormOptionInputValue;
};

export type FormOptionProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> &
  FormOptionInput & {
    required?: boolean;
    showSuccessMessage?: boolean;
  };

const FormOption = <T extends FieldValues>({
  name,
  control,
  checkedValue = true,
  uncheckedValue = false,
  ...props
}: FormOptionProps<T>): JSX.Element => {
  const {
    field,
    fieldState: { error },
  } = useController({ control, name });

  return (
    <Option
      {...props}
      name={name}
      isError={!!error}
      checked={field.value === checkedValue}
      onChange={(checked: boolean) => field.onChange(checked ? checkedValue : uncheckedValue)}
    />
  );
};

export default FormOption;
