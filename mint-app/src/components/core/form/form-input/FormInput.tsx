import EyeIcon from '@/components/icons/EyeIcon';
import EyeSlashIcon from '@/components/icons/EyeSlashIcon';
import { HTMLInputTypeAttribute, InputHTMLAttributes, ReactNode, useMemo, useState } from 'react';
import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import IconButton from '../../icon-button/IconButton';

type FormInputType = Extract<HTMLInputTypeAttribute, 'text' | 'password'>;

export type FormInputProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> &
  InputHTMLAttributes<HTMLInputElement> & {
    leftInputNode?: ReactNode;
    rightInputNode?: ReactNode;
    sizing?: 'md' | 'sm' | 'lg';
    showSuccessMessage?: boolean;
    type?: FormInputType;
  };

const FormInput = <T extends FieldValues>({
  name,
  control,
  leftInputNode,
  rightInputNode,
  sizing = 'md',
  disabled = false,
  showSuccessMessage = false,
  type = 'text',
  ...inputProps
}: FormInputProps<T>): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const {
    field,
    fieldState: { error: fieldError },
  } = useController({ control, name });

  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [showPassword, setShowPassword] = useState<boolean>(false);
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

      <input
        id={name}
        name={name}
        type={showPassword ? 'text' : type}
        disabled={disabled}
        className={twMerge(
          'w-full px-4 py-3',
          leftInputNode && 'pl-0',
          (rightInputNode || type === 'password') && 'pr-0',
          sizing === 'md' && 'h-[46px]',
          sizing === 'sm' && 'h-[38px]',
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

      {(!!rightInputNode || type === 'password') && (
        <div className='mr-3 flex items-center justify-end gap-1 text-md font-normal text-comp-text-tertiary'>
          {rightInputNode}
          {type === 'password' && (
            <IconButton
              title={showPassword ? tCommon('component.hide_password') : tCommon('component.show_password')}
              variant='raw'
              aria-label={showPassword ? tCommon('component.hide_password') : tCommon('component.show_password')}
              onClick={() => setShowPassword((prev) => !prev)}
              icon={showPassword ? <EyeSlashIcon /> : <EyeIcon />}
            />
          )}
        </div>
      )}
    </div>
  );
};

export default FormInput;
