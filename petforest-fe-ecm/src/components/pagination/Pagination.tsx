'use client';
import { ChevronLeftIcon, ChevronRightIcon } from '@chakra-ui/icons';

const Pagination = ({
  totalPages,
  onPageChange,
  currentPage,
  setCurrentPage,
}: {
  totalPages: number;
  onPageChange: (page: number) => void;
  setCurrentPage: (currentPage: number) => void;
  currentPage: number;
}) => {
  const handlePrevious = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
      onPageChange(currentPage - 1);
    }
  };

  const handleNext = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
      onPageChange(currentPage + 1);
    }
  };

  const handlePageClick = (pageNumber: number) => {
    if (pageNumber !== currentPage) {
      setCurrentPage(pageNumber);
      onPageChange(pageNumber);
    }
  };

  const renderPageButton = (pageNumber: number) => (
    <button
      key={`page-${pageNumber}`}
      className={`mx-2 text-[16px] font-medium text-black ${currentPage !== pageNumber ? 'opacity-60' : ''}`}
      onClick={() => handlePageClick(pageNumber)}>
      {pageNumber}
    </button>
  );

  const renderEllipsis = (number: number) => (
    <div key={`ellipsis-${number}`} className='text-[16px] opacity-60'>
      ...
    </div>
  );

  const renderPageButtons = () => {
    const pageButtons = [];
    const maxVisibleButtons = 6;

    // Function to push a page button to the array
    const addPageButton = (pageNumber: number) => {
      pageButtons.push(renderPageButton(pageNumber));
    };

    if (totalPages < maxVisibleButtons) {
      // If total pages are less than or equal to maxVisibleButtons, show all pages
      for (let i = 1; i <= totalPages; i++) {
        addPageButton(i);
      }
    } else {
      let firstVisible = Math.max(2, currentPage - Math.floor((maxVisibleButtons - 2) / 2));
      let lastVisible = Math.min(totalPages - 1, firstVisible + maxVisibleButtons - 3);

      // Adjust if the current page is near the start
      if (currentPage <= Math.ceil((maxVisibleButtons - 2) / 2)) {
        firstVisible = 2;
        lastVisible = Math.min(totalPages - 1, firstVisible + maxVisibleButtons - 3);
      }

      // Adjust if the current page is near the end
      if (currentPage >= totalPages - Math.floor((maxVisibleButtons - 2) / 2)) {
        lastVisible = totalPages - 1;
        firstVisible = Math.max(2, lastVisible - maxVisibleButtons + 3);
      }

      // Always show the first page
      addPageButton(1);

      // Show '...' if the first visible page is not 2
      if (firstVisible > 2) {
        pageButtons.push(renderEllipsis(1));
      }

      // Show the visible pages
      for (let i = firstVisible; i <= lastVisible; i++) {
        addPageButton(i);
      }

      // Show '...' if the last visible page is not the second-to-last page
      if (lastVisible < totalPages - 1) {
        pageButtons.push(renderEllipsis(2));
      }

      // Always show the last page
      addPageButton(totalPages);
      // Ensure we always show 6 buttons (including ellipses) when possible
      if (pageButtons.length < maxVisibleButtons) {
        // Fill in missing buttons if necessary (e.g., add buttons around the current page)
        for (let i = 2; i <= totalPages - 1; i++) {
          if (!pageButtons.some((button) => button.props.pageNumber === i)) {
            pageButtons.splice(1, 0, renderPageButton(i));
            if (pageButtons.length >= maxVisibleButtons) break;
          }
        }
      }
    }

    return pageButtons;
  };
  return (
    <div className='flex items-center justify-center'>
      <button
        onClick={() => handlePrevious()}
        disabled={currentPage === 1}
        type='button'
        aria-label='Go to previous page'
        className='mx-2'>
        <ChevronLeftIcon w={6} h={6} color={currentPage === 1 ? '#999999' : '#000000'} />
      </button>

      {renderPageButtons()}

      <button
        type='button'
        onClick={handleNext}
        disabled={currentPage === totalPages}
        aria-label='Go to next page'
        className='mx-2'>
        <ChevronRightIcon w={6} h={6} color={currentPage === totalPages ? '#999999' : '#000000'} />
      </button>
    </div>
  );
};

export default Pagination;
