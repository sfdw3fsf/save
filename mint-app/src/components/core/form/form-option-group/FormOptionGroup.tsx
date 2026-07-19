import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import { twMerge } from 'tailwind-merge';
import Option from '../../option/Option';
import { FormOptionInput, FormOptionInputValue } from '../form-option/FormOption';

export type FormOptionValuesItem = Omit<FormOptionInput, 'type'> | null;

export type FormOptionGroupProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> &
  Pick<FormOptionInput, 'type'> & {
    values: FormOptionValuesItem[];
    required?: boolean;
    showSuccessMessage?: boolean;
    replace?: boolean;
    className?: string;
    customGroupClassName?: string;
    customOptionClassName?: string;
    onCustomChange?: (value: FormOptionInputValue) => void;
  };

const FormOptionGroup = <T extends FieldValues>({
  name,
  control,
  values,
  replace = false,
  className,
  customGroupClassName,
  customOptionClassName,
  type,
  onCustomChange,
}: FormOptionGroupProps<T>): JSX.Element => {
  const {
    field,
    fieldState: { error },
  } = useController({ control, name });

  const handleChange = (
    checked: boolean,
    index: number,
    checkedValue: FormOptionInputValue,
    uncheckedValue: FormOptionInputValue,
  ) => {
    if (onCustomChange) {
      onCustomChange?.(checkedValue); // handle external changes in some cases
      return;
    }
    if (type === 'radio') {
      field.onChange(checkedValue);
      return;
    }

    const currentValue = (field.value || []) as FormOptionInputValue[];
    let newValue;
    if (replace) {
      newValue = checked ? [...currentValue, checkedValue] : currentValue.filter((value) => value !== checkedValue);
    } else {
      newValue = [...currentValue];
      newValue[index] = checked ? checkedValue : uncheckedValue;
    }
    field.onChange(newValue);
  };

  if (values.length < 1) return <></>;
  return (
    <div className={twMerge('flex flex-col gap-3 w-full items-start justify-start', customGroupClassName)}>
      {values.map((item, index) => {
        if (!item) return null;
        const { checkedValue = true, uncheckedValue = false, ...checkboxProps } = item;
        return (
          <Option
            className={className}
            customOptionClassName={customOptionClassName}
            {...checkboxProps}
            type={type}
            key={index}
            isError={!!error}
            name={`${name}.${index}`}
            checked={
              type === 'radio'
                ? field.value === checkedValue
                : replace
                  ? field.value?.includes(checkedValue)
                  : field.value?.[index] === checkedValue
            }
            onChange={(checked: boolean) => handleChange(checked, index, checkedValue, uncheckedValue)}
          />
        );
      })}
    </div>
  );
};

export default FormOptionGroup;
