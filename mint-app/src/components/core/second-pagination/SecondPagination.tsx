import CaretDoubleLeft from '@/components/icons/CaretDoubleLeft';
import CaretDoubleRight from '@/components/icons/CaretDoubleRightIcon';
import CaretLeft from '@/components/icons/CaretLeftIcon';
import CaretRight from '@/components/icons/CaretRightIcon';
import { useBreakpoint } from '@/hooks/useBreakpoint';
import { RECORDS_PER_PAGE } from '@/hooks/useFilter';
import { useMemo } from 'react';
import { twMerge } from 'tailwind-merge';
import PaginationButton from '../pagination/PaginationButton';
import PaginationControlButton from '../pagination/PaginationControlButton';

// Because we have a component name "Pagination" => name it as SecondPagination
export type SecondPaginationConfigsProps = {
  size?: number; // ? / page
  total: number; // total pages
  page: number; // currentPage
  onChange?: (page?: number, size?: number) => void;
};

type SecondPaginationProps = SecondPaginationConfigsProps & {
  className?: string;
};
const SecondPagination = ({ total, page, size = RECORDS_PER_PAGE, onChange, className }: SecondPaginationProps): JSX.Element => {
  const { hasMinWidthMd: isWeb } = useBreakpoint('md');

  const maxPages = useMemo(() => (isWeb ? 10 : 5), [isWeb]);
  const displayedPages = useMemo(() => {
    const halfMaxPages = Math.floor(maxPages / 2);
    const startPage = Math.max(1, page - halfMaxPages);
    const endPage = Math.min(total, startPage + maxPages - 1);

    const adjustedStartPage = Math.max(1, endPage - maxPages + 1);

    return Array.from({ length: endPage - adjustedStartPage + 1 }, (_, i) => adjustedStartPage + i);
  }, [page, total, maxPages]);

  return (
    <div className={twMerge('flex justify-center items-center gap-4 mt-6 p-4', className)}>
      <PaginationControlButton
        onClick={() => {
          if (page === 1) return;
          onChange?.(1, size);
        }}
        icon={<CaretDoubleLeft className='w-3 h-3 md:w-3.5 md:h-3.5' />}
      />
      <PaginationControlButton
        onClick={() => {
          if (page === 1) return;
          onChange?.(page - 1, size);
        }}
        icon={<CaretLeft className='w-3 h-3 md:w-3.5 md:h-3.5' />}
      />

      <div className='flex justify-center items-center '>
        {displayedPages.map((displayedPage) => (
          <PaginationButton
            key={displayedPage}
            page={displayedPage}
            currentPage={page}
            onPageChange={() => {
              onChange?.(displayedPage, size);
            }}
          />
        ))}
      </div>

      <PaginationControlButton
        onClick={() => {
          if (page === total) return;
          onChange?.(page + 1, size);
        }}
        icon={<CaretRight className='w-3 h-3 md:w-3.5 md:h-3.5' />}
      />
      <PaginationControlButton
        onClick={() => {
          if (page === total) return;
          onChange?.(total, size);
        }}
        icon={<CaretDoubleRight className='w-3 h-3 md:w-3.5 md:h-3.5' />}
      />
    </div>
  );
};

export default SecondPagination;
