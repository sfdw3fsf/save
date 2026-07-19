import CheckIcon from '@/components/icons/CheckIcon';
import { ReactNode } from 'react';
import { twJoin, twMerge } from 'tailwind-merge';

export type OptionProps = {
  type: 'radio' | 'checkbox';
  sizing?: 'md' | 'sm';
  label?: ReactNode;
  description?: string;
  disabled?: boolean;
  name: string;
  checked?: boolean;
  onChange?: (checked: boolean) => void;
  isError?: boolean;
  className?: string;
  customOptionClassName?: string;
  expandComponent?: ReactNode;
};

const theme = {
  icon: {
    md: 'size-5',
    sm: 'size-4',
  },
  cont: {
    default: 'bg-primary-500 enabled:hover:bg-primary-600',
    error: 'bg-red-500 enabled:hover:bg-red-600',
  },
  outline: {
    default: 'outline-slate-200 enabled:hover:outline-slate-500',
    error: 'outline-comp-border-danger-strong',
  },
  radioIcon: {
    md: 'size-[7.5px]',
    sm: 'size-1.5',
  },
  rounded: {
    radio: 'rounded-full',
    checkbox: 'rounded-[4px]',
  },
};

const Option = ({
  name,
  type,
  sizing = 'sm',
  onChange,
  label,
  description,
  checked = false,
  disabled = false,
  isError = false,
  className,
  customOptionClassName,
  expandComponent,
}: OptionProps): JSX.Element => {
  return (
    <div className={twMerge('flex flex-col w-full gap-3 items-start justify-start', customOptionClassName)}>
      <label htmlFor={name} className={twMerge('flex w-fit h-fit items-start justify-start gap-1', className)}>
        <div className='h-5 flex items-center justify-center'>
          <div className='relative flex items-center justify-center m-0.5'>
            <input
              id={name}
              name={name}
              type={type}
              checked={checked}
              disabled={disabled}
              onChange={(e) => onChange?.(e.target.checked)}
              className={twMerge(
                theme.icon[sizing],
                'appearance-none disabled:bg-slate-100 shrink-0',
                theme.rounded[type],
                checked
                  ? twJoin(
                      isError ? theme.cont.error : theme.cont.default,
                      'disabled:outline disabled:outline-1 disabled:outline-slate-200 enabled:active:shadow-focus-brand',
                    )
                  : twJoin(
                      isError ? theme.outline.error : theme.outline.default,
                      'outline outline-1 bg-transparent enabled:hover:bg-white enabled:active:bg-white enabled:active:shadow-focus-neutral',
                    ),
              )}
            />
            {checked && (
              <div className={twMerge(theme.icon[sizing], 'absolute flex items-center justify-center shrink-0')}>
                {type === 'checkbox' && (
                  <CheckIcon className={twMerge('size-3', disabled ? 'text-comp-disabled' : 'text-white')} />
                )}
                {type === 'radio' && (
                  <span
                    className={twMerge(theme.radioIcon[sizing], 'rounded-full', disabled ? 'bg-comp-disabled' : 'bg-white')}
                  />
                )}
              </div>
            )}
          </div>
        </div>

        {!!label && (
          <div className='flex flex-col text-sm font-normal gap-1 text-slate-800'>
            <div>{label}</div>
            {!!description && <div className='text-comp-disabled'>{description}</div>}
          </div>
        )}
      </label>
      {checked && expandComponent}
    </div>
  );
};

export default Option;
