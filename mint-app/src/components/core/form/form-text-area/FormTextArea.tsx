import { ReactNode, TextareaHTMLAttributes, useMemo, useState } from 'react';
import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import { twMerge } from 'tailwind-merge';

export type FormTextAreaProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> &
  TextareaHTMLAttributes<HTMLTextAreaElement> & {
    leftInputNode?: ReactNode;
    rightInputNode?: ReactNode;
    sizing?: 'md' | 'sm' | 'lg';
    showSuccessMessage?: boolean;
    disabledResize?: boolean;
  };

const FormTextArea = <T extends FieldValues>({
  name,
  control,
  leftInputNode,
  rightInputNode,
  sizing = 'md',
  disabled = false,
  showSuccessMessage = false,
  disabledResize = false,
  ...inputProps
}: FormTextAreaProps<T>): JSX.Element => {
  const {
    field,
    fieldState: { error: fieldError },
  } = useController({ control, name });

  const [isFocused, setIsFocused] = useState<boolean>(false);
  const isSuccess = useMemo<boolean>(() => !fieldError && showSuccessMessage, [fieldError, showSuccessMessage]);

  return (
    <div
      className={twMerge(
        'relative flex w-full h-fit items-center justify-start gap-2 rounded-lg overflow-hidden text-md text-slate-800 bg-white border border-slate-200 hover:bg-slate-50 hover:border-primary-500',
        field.value ? 'font-medium' : 'font-normal',
        isFocused && 'hover:bg-white border-primary-500',
        isFocused && field.value && 'hover:border-primary-200 border-primary-200',
        fieldError && 'border-red-500 hover:border-red-500',
        disabled && 'bg-slate-100 border-slate-200 hover:bg-slate-100 hover:border-slate-200',
        isSuccess && 'border-green-500 hover:border-green-500',
        isSuccess && !disabled && 'bg-white hover:bg-white',
      )}>
      {disabled && <div className='absolute w-full h-full'></div>}

      {!!leftInputNode && <div className='ml-4 flex items-center justify-start gap-1'>{leftInputNode}</div>}

      <textarea
        id={name}
        name={name}
        disabled={disabled}
        className={twMerge(
          'w-full px-4 py-3',
          leftInputNode && 'pl-0',
          rightInputNode && 'pr-0',
          sizing === 'md' && 'min-h-12',
          sizing === 'sm' && 'min-h-10',
          sizing === 'lg' && 'min-h-[194px]',
          disabledResize && 'resize-none',
        )}
        value={field.value}
        {...inputProps}
        onChange={(e) => {
          field.onChange(e);
          inputProps.onChange?.(e);
        }}
        onFocus={(e) => {
          setIsFocused(true);
          inputProps.onFocus?.(e);
        }}
        onBlur={(e) => {
          field.onBlur();
          setIsFocused(false);
          inputProps.onBlur?.(e);
        }}
      />

      {!!rightInputNode && (
        <div className='mr-3 flex items-center justify-end gap-1 text-md font-normal text-comp-text-tertiary'>
          {rightInputNode}
        </div>
      )}
    </div>
  );
};

export default FormTextArea;
