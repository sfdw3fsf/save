import { PagingREQ, PagingState } from '@/utils/types/paging.type';
import { DoubleLeftOutlined, DoubleRightOutlined } from '@ant-design/icons';
import { Pagination } from 'antd';
import clsx from 'clsx';
import React from 'react';

interface PaginationProps {
  paging: PagingState;
  onPageNumberChange?: ({ pageNumber, pageSize }: PagingREQ) => void;
}

const CustomPagination: React.FC<PaginationProps> = ({ paging, onPageNumberChange }) => {
  const isGoToStart = paging.pageNumber > 1;
  const isGoToEnd = paging.total ? paging.pageNumber < Math.ceil(paging.total / paging.pageSize) : false;

  const handlePageChange = (pageNumber: number, pageSize: number) => {
    onPageNumberChange && onPageNumberChange({ pageNumber, pageSize });
  };

  const onGoToStart = () => {
    onPageNumberChange && onPageNumberChange({ pageNumber: 1, pageSize: paging.pageSize });
  };

  const onGoToEnd = () => {
    if (paging.total) {
      onPageNumberChange &&
        onPageNumberChange({
          pageNumber: Math.ceil(paging.total / paging.pageSize),
          pageSize: paging.pageSize,
        });
    }
  };

  return (
    <div className='flex relative max-w-screen justify-center'>
      <button disabled={!isGoToStart} onClick={onGoToStart}>
        <DoubleLeftOutlined className={clsx(`${!isGoToStart ? '!text-disabled cursor-not-allowed' : ''}`)} />
      </button>
      <Pagination
        defaultCurrent={1}
        total={paging.total || 1}
        size='small'
        onChange={handlePageChange}
        className='flex justify-center !mt-0'
        pageSize={paging.pageSize}
        current={paging.pageNumber}
      />
      <button disabled={!isGoToEnd} onClick={onGoToEnd}>
        <DoubleRightOutlined className={clsx(`${!isGoToEnd ? '!text-disabled  cursor-not-allowed' : ''}`)} />
      </button>
    </div>
  );
};

export default CustomPagination;
