import React from 'react';
import { twMerge } from 'tailwind-merge';

interface PaginationButtonProps {
  page: number;
  currentPage: number;
  onPageChange: (page: number) => void;
  className?: string;
}

const PaginationButton: React.FC<PaginationButtonProps> = ({ page, currentPage, onPageChange, className }) => {
  return (
    <button
      onClick={() => onPageChange(page)}
      className={twMerge(
        'rounded-full size-7 md:size-10 gap-x-1',
        currentPage === page ? 'bg-primary-500 text-white' : 'text-slate-500 hover:bg-slate-150 hover:text-slate-800',
        className,
      )}>
      {page}
    </button>
  );
};

export default PaginationButton;
