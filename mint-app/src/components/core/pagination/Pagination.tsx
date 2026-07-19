import CaretDoubleLeft from '@/components/icons/CaretDoubleLeft';
import CaretDoubleRight from '@/components/icons/CaretDoubleRightIcon';
import CaretLeft from '@/components/icons/CaretLeftIcon';
import CaretRight from '@/components/icons/CaretRightIcon';
import { useBreakpoint } from '@/hooks/useBreakpoint';
import React, { useMemo } from 'react';
import { useSearchParams } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import PaginationButton from './PaginationButton';
import PaginationControlButton from './PaginationControlButton';
interface PaginationProps {
  className?: string;
  isBgWhite?: boolean;
}

const Pagination: React.FC<PaginationProps> = ({ className, isBgWhite = false }) => {
  const { hasMinWidthMd } = useBreakpoint('md');
  const maxPages = useMemo(() => (hasMinWidthMd ? 10 : 5), [hasMinWidthMd]);
  const [searchParams, setSearchParams] = useSearchParams();
  const totalPages = Number(searchParams.get('totalPages')) || 1;
  const currentPage = Number(searchParams.get('pageNo')) || 0;

  const displayedPages = useMemo(() => {
    const halfMaxPages = Math.floor(maxPages / 2);
    const startPage = Math.max(1, currentPage - halfMaxPages);
    const endPage = Math.min(totalPages, startPage + maxPages - 1);

    const adjustedStartPage = Math.max(1, endPage - maxPages + 1);

    return Array.from({ length: endPage - adjustedStartPage + 1 }, (_, i) => adjustedStartPage + i);
  }, [currentPage, totalPages, maxPages]);

  return (
    <div
      className={twMerge(
        'flex justify-center items-center gap-4  p-5',
        className,
        isBgWhite && 'md:bg-white md:border-b border-l border-r md:border-slate-200 rounded-b-lg',
      )}>
      <PaginationControlButton
        onClick={() => {
          searchParams.set('pageNo', '1'), setSearchParams(searchParams, { replace: true });
        }}
        disabled={currentPage === 1}
        icon={<CaretDoubleLeft className='w-3 h-3 md:w-[14.35px] md:h-[14.35px]' />}
      />
      <PaginationControlButton
        onClick={() => {
          searchParams.set('pageNo', (currentPage - 1).toString()), setSearchParams(searchParams, { replace: true });
        }}
        disabled={currentPage === 1}
        icon={<CaretLeft className='w-3 h-3 md:w-[14.35px] md:h-[14.35px]' />}
      />

      <div className='flex justify-center items-center '>
        {displayedPages.map((page) => (
          <PaginationButton
            key={page}
            page={page}
            currentPage={currentPage}
            onPageChange={() => {
              searchParams.set('pageNo', page.toString()), setSearchParams(searchParams, { replace: true });
            }}
          />
        ))}
      </div>

      <PaginationControlButton
        // onClick={() => onPageChange(currentPage + 1)}
        onClick={() => {
          searchParams.set('pageNo', (currentPage + 1).toString()), setSearchParams(searchParams, { replace: true });
        }}
        disabled={currentPage === totalPages}
        icon={<CaretRight className='w-3 h-3 md:w-[14.35px] md:h-[14.35px]' />}
      />
      <PaginationControlButton
        onClick={() => {
          searchParams.set('pageNo', totalPages.toString()), setSearchParams(searchParams, { replace: true });
        }}
        // onClick={() => onPageChange(doublePlus)}
        disabled={currentPage === totalPages}
        icon={<CaretDoubleRight className='w-3 h-3 md:w-[14.35px] md:h-[14.35px]' />}
      />
    </div>
  );
};

export default Pagination;
