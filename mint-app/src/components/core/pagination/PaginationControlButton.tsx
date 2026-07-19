import React from 'react';
import { twMerge } from 'tailwind-merge';

interface PaginationControlButtonProps {
  onClick: () => void;
  className?: string;
  disabled?: boolean;
  icon: React.ReactNode;
}

const PaginationControlButton: React.FC<PaginationControlButtonProps> = ({ onClick, className, disabled, icon }) => {
  return (
    <button
      onClick={onClick}
      className={twMerge(
        'px-1 md:px-0 w-7 h-7 md:w-10 md:h-10 flex items-center justify-center bg-white rounded-full border border-gray-300 text-gray-500 hover:bg-slate-50',
        className,
      )}
      disabled={disabled}>
      {icon}
    </button>
  );
};

export default PaginationControlButton;
