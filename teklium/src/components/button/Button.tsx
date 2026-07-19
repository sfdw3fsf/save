import { ReactNode } from 'react';
import { twMerge } from 'tailwind-merge';
type ButtonProps = {
  className?: string;
  variant?: string;
  leftIcon?: ReactNode;
  rightIcon?: ReactNode;
  children?: ReactNode;
  onClick?: () => void;
};
export default function Button({ variant, leftIcon, rightIcon, children, onClick, className }: ButtonProps) {
  return (
    <button
      onClick={onClick}
      className={twMerge(
        'py-[9px] px-[17px] shadow rounded-md font-bold text-base',
        className,
        variant === 'blue'
          ? 'bg-royal-600 text-white'
          : variant === 'red'
            ? 'bg-emphasize-42 text-gray-25'
            : 'bg-[#fff] text-gray-600',
      )}>
      {!!rightIcon && (
        <span className='flex items-center justify-center gap-1'>
          {rightIcon} {children}
        </span>
      )}
      {!!leftIcon && (
        <span className='flex items-center justify-center gap-1'>
          {leftIcon} {children}
        </span>
      )}
      {!(!!leftIcon && !!rightIcon) && children}
    </button>
  );
}
