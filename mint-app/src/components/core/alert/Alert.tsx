import WarningCircleIcon from '@/components/icons/WarningCircleIcon';
import XIcon from '@/components/icons/XIcon';
import { cloneElement, ReactElement, ReactNode } from 'react';
import { twMerge } from 'tailwind-merge';
import { theme } from './alert.theme';

type AlertProps = {
  variant?: 'error' | 'toast-error' | 'toast-success' | 'toast-info';
  title?: string;
  children: ReactNode;
  className?: string;
  open?: boolean;
  onClose?: () => void;
  icon?: ReactElement;
};

const Alert = ({
  variant = 'error',
  title,
  children: message,
  className,
  open = true,
  onClose,
  icon,
}: AlertProps): JSX.Element => {
  if (!open) return <></>;

  return (
    <div
      className={twMerge(
        'flex items-start justify-between gap-3 px-3.5 pt-3.5 pb-4 border rounded-lg overflow-hidden',
        theme.base[variant],
        className,
      )}>
      <div className='flex items-center justify-center h-5 shrink-0'>
        {!icon ? (
          <WarningCircleIcon className={twMerge('h-full w-auto', theme.color.icon[variant])} />
        ) : (
          cloneElement(icon, { className: twMerge('h-full w-auto', theme.color.icon[variant]) })
        )}
      </div>

      <div className='flex flex-col w-full items-start justify-start gap-1 min-h-5 text-sm text-left'>
        {!!title && <div className={twMerge('font-medium', theme.color.main[variant])}>{title}</div>}
        <div className={twMerge('font-normal', theme.color.sub[variant])}>{message}</div>
      </div>

      {!!onClose && (
        <button className='flex items-center justify-center h-5 shrink-0' type='button' form='_notexist' onClick={onClose}>
          <XIcon className={twMerge('h-full w-auto', theme.color.sub[variant])} />
        </button>
      )}
    </div>
  );
};

export default Alert;
