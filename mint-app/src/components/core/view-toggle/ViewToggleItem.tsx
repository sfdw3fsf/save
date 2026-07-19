import { ButtonHTMLAttributes } from 'react';
import { twMerge } from 'tailwind-merge';

export type ViewToggleItemProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  label: string;
  isActive?: boolean;
};

const ViewToggleItem = ({ label, isActive = false, className, ...props }: ViewToggleItemProps) => {
  return (
    <button
      className={twMerge(
        'flex w-full items-center justify-center rounded-lg px-0.5 py-1 gap-1.5 text-slate-500 bg-slate-50 enabled:hover:text-slate-800 disabled:text-comp-disabled',
        isActive && 'bg-white shadow-elevation-sm text-slate-800',
        className,
      )}
      {...props}>
      <span>{label}</span>
    </button>
  );
};

export default ViewToggleItem;
