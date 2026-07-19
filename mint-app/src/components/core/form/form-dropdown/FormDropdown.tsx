import CaretDownIcon from '@/components/icons/CaretDownIcon';
import { useMemo } from 'react';
import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import { twMerge } from 'tailwind-merge';
import Button from '../../button/Button';
import Dropdown, { DropdownProps } from '../../dropdown/Dropdown';
type FormDropDownInput = Pick<DropdownProps, 'items'>;
export type FormDropdownProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> &
  FormDropDownInput & {
    required?: boolean;
    showSuccessMessage?: boolean;
    placeholder: string;
    className?: string;
  };
const FormDropdown = <T extends FieldValues>({
  name,
  control,
  placeholder,
  items,
  className,
  ...props
}: FormDropdownProps<T>): JSX.Element => {
  const {
    field,
    fieldState: { error },
  } = useController({ control, name });
  const label = useMemo(() => items?.find((item) => item.value === field.value)?.label, [field.value, items]);
  return (
    <Dropdown {...props} items={items} onSelect={(value) => field.onChange(value)} className='w-full'>
      <Button
        className={twMerge(
          'w-full hover:border-primary-500 hover:bg-slate-50 text-md py-2',
          label ? 'font-medium' : 'font-normal',
          error && 'border-red-500 hover:border-red-500',
        )}
        rightIcon={<CaretDownIcon />}
        labelContStyle={twMerge('w-full justify-between', className)}
        labelStyle={label ? 'text-slate-800' : 'text-slate-500'}>
        {label || placeholder}
      </Button>
    </Dropdown>
  );
};

export default FormDropdown;
