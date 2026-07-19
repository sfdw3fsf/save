import { initialPagingState, PagingREQ, PagingState } from '@/utils/types/paging.type';
import { DoubleLeftOutlined, DoubleRightOutlined } from '@ant-design/icons';
import { DndContext, DragEndEvent } from '@dnd-kit/core';
import { SyntheticListenerMap } from '@dnd-kit/core/dist/hooks/utilities';
import { restrictToVerticalAxis } from '@dnd-kit/modifiers';
import { SortableContext, verticalListSortingStrategy } from '@dnd-kit/sortable';
import { Pagination, Table } from 'antd';
import { ColumnsType, TableProps } from 'antd/es/table';
import { FilterValue, RowSelectionType, SorterResult, TableCurrentDataSource } from 'antd/es/table/interface';
import { TablePaginationConfig } from 'antd/lib';
import { arrayMoveImmutable } from 'array-move';
import clsx from 'clsx';
import React, { useEffect, useId, useMemo, useState } from 'react';
import { ResizeCallbackData } from 'react-resizable';
import DragRow from './DragRow';
import { ResizableTitle } from './ResizableTitle';

interface RowContextProps {
  setActivatorNodeRef?: (element: HTMLElement | null) => void;
  listeners?: SyntheticListenerMap;
}

const RowContext = React.createContext<RowContextProps>({});

export type CustomTableBlueProps<T> = {
  columns: ColumnsType<T>;
  data: T[];
  onChange?: (selectedRowKeys: React.Key[], selectedRows: T[]) => void;
  onChangeTable?: (
    pagination: TablePaginationConfig,
    filters: Record<string, FilterValue | null>,
    sorter: SorterResult<any> | SorterResult<any>[],
    extra: TableCurrentDataSource<any>,
  ) => void;
  onSelect?: (record: T, selected: boolean, selectedRows: T[]) => void;
  onSelectAll?: (selected: boolean, selectedRows: T[], changeRows: T[]) => void;
  onPageNumberChange?: ({ pageNumber, pageSize }: PagingREQ) => void;
  getCheckboxProps?: (record: T) => { disabled?: boolean };
  handleReoder?: (array: any[], oldIndex: number, newIndex: number) => void;
  showSelect?: boolean;
  selectedRowKeys?: React.Key[];
  className?: string;
  styleHeader?: React.CSSProperties;
  isBlue?: boolean;
  isBorderEnd?: boolean;
  paging?: PagingState;
  onClickRow?: (record: T) => void;
  isLoading?: boolean;
  isAlignLeftHeader?: boolean;
  isNoWrap?: boolean;
  showPaging?: boolean;
  showIndex?: boolean;
  extra?: React.ReactNode;
  is1614Size?: boolean;
  isScroll?: boolean;
  isHover?: boolean;
  type?: RowSelectionType;
  serialProps?: any;
  leftHeight?: number;
  rowBackgroundColor?: (record: T) => string;
} & TableProps<T>;

const CustomTable = <T extends object>({
  columns,
  data,
  onChange,
  onChangeTable,
  onSelectAll,
  onSelect,
  size = 'middle',
  selectedRowKeys,
  showSelect,
  className,
  styleHeader,
  isBlue = true,
  isBorderEnd = true,
  onPageNumberChange,
  paging,
  onClickRow,
  handleReoder,
  isLoading,
  scroll,
  isAlignLeftHeader,
  isNoWrap,
  showPaging = true,
  showIndex = true,
  extra,
  serialProps,
  getCheckboxProps,
  isScroll = true,
  isHover = true,
  type = 'checkbox',
  rowBackgroundColor,
  leftHeight = 130,
  ...rest
}: CustomTableBlueProps<T>) => {
  const [columnsResize, setColumnsResize] = useState(() => columns.map((col) => ({ ...col, initialWidth: col.width || 30 })));

  const id = useId();

  const [dataSource, setDataSource] = useState<T[]>(data);
  const [tableHeight, setTableHeight] = useState<number | string>('');

  const isGoToStart = useMemo(() => {
    if (!paging || paging.pageNumber <= 1) return false;
    return true;
  }, [paging]);

  const isGoToEnd = useMemo(() => {
    if (!paging || !paging.total || paging.pageNumber >= paging.total / paging.pageSize) return false;
    return true;
  }, [paging]);

  const totalPages = useMemo(() => {
    return paging?.total ? Math.ceil(paging?.total / (paging?.pageSize || 1)) : 0;
  }, [paging?.total, paging?.pageSize]);

  const handleResize =
    (index: number) =>
    (e: React.SyntheticEvent<Element>, { size }: ResizeCallbackData) => {
      setColumnsResize((prevColumns) => {
        const nextColumns = [...prevColumns];
        const minWidth = nextColumns[index].initialWidth || 30;
        const newWidth = Math.max(size.width, Number(minWidth));
        nextColumns[index] = {
          ...nextColumns[index],
          width: newWidth,
        };
        return nextColumns;
      });
    };

  const mergedColumns = columnsResize?.map((col, index) => ({
    ...col,
    onHeaderCell: (column: any) => ({
      width: column.width,
      style: {
        ...styleHeader,
      },
      onResize: handleResize(index),
    }),
  })) as [];

  const handlePageChange = (pageNumber: number, pageSize: number) => {
    onPageNumberChange && onPageNumberChange({ pageNumber, pageSize });
  };

  const onGoToStart = () => {
    onPageNumberChange && onPageNumberChange({ pageNumber: 1, pageSize: paging?.pageSize || initialPagingState.pageSize });
  };

  const onGoToEnd = () => {
    onPageNumberChange &&
      paging?.total &&
      onPageNumberChange({
        pageNumber: totalPages,
        pageSize: paging?.pageSize || initialPagingState.pageSize,
      });
  };

  const onDragEnd = ({ active, over }: DragEndEvent) => {
    if (active.id !== over?.id) {
      const activeIndex = dataSource.findIndex((record: any) => record.key === active?.id);
      const overIndex = dataSource.findIndex((record: any) => record.key === over?.id);
      const updatedData = arrayMoveImmutable(dataSource || [], activeIndex, overIndex);
      if (handleReoder) {
        handleReoder(dataSource, activeIndex, overIndex);
      }
      setDataSource(updatedData);
    }
  };

  useEffect(() => {
    setColumnsResize((pre) => pre.map((item, index) => ({ ...item, fixed: columns[index]?.fixed })));
  }, [columns]);

  useEffect(() => {
    setDataSource(data);
  }, [data]);

  useEffect(() => {
    const updateTableHeight = () => {
      const container = document.getElementById('fixedTable');
      const tableHeader = document.getElementsByClassName('ant-table-thead')[0] as HTMLElement;
      if (container) {
        const { top } = container.getBoundingClientRect();

        const paddingOffset = leftHeight;
        const headerHeight = tableHeader ? tableHeader.offsetHeight : 0;
        const tableHeight = window.innerHeight - top - paddingOffset - headerHeight;

        if (tableHeight < 200) {
          setTableHeight('50vh');
        } else {
          setTableHeight(tableHeight);
        }
      }
    };

    updateTableHeight();
    window.addEventListener('resize', updateTableHeight);
    return () => window.removeEventListener('resize', updateTableHeight);
  }, [leftHeight]);

  return (
    <div className='custom-table border-input-none flex flex-col h-full'>
      <div className='flex-grow overflow-auto'>
        <DndContext id={id} modifiers={[restrictToVerticalAxis]} onDragEnd={onDragEnd}>
          <SortableContext items={dataSource.map((i: any) => i.key)} strategy={verticalListSortingStrategy}>
            <Table
              rowKey={(record: any) => record.id || record.key}
              components={{
                header: {
                  cell: ResizableTitle,
                },
                body: {
                  row: DragRow,
                },
              }}
              columns={[
                ...(showIndex
                  ? [
                      {
                        title: 'No.',
                        dataIndex: 'serial',
                        key: 'index',
                        width: 40,
                        ...serialProps,
                        render: (text: string, record: T, index: number) => (
                          <div>
                            {(paging && paging.pageNumber && paging.pageSize ? (paging?.pageNumber - 1) * paging?.pageSize : 0) +
                              (index + 1)}
                          </div>
                        ),
                      },
                    ]
                  : []),
                ...mergedColumns,
              ]}
              size={size}
              dataSource={[...dataSource]}
              loading={isLoading}
              scroll={isScroll ? scroll || { x: 'max-content', y: tableHeight ?? 'calc(100vh-70px)' } : undefined}
              rowSelection={
                showSelect
                  ? {
                      type: type,
                      onChange,
                      onSelect,
                      onSelectAll,
                      selectedRowKeys,
                      getCheckboxProps,
                      columnWidth: 30,
                    }
                  : undefined
              }
              onRow={(record) => {
                return {
                  style: {
                    backgroundColor: rowBackgroundColor ? rowBackgroundColor(record) : 'transparent',
                  },
                  onClick: () => {
                    onClickRow && onClickRow(record);
                  },
                };
              }}
              pagination={false}
              className={`${className}`}
              onChange={onChangeTable}
              {...rest}
            />
          </SortableContext>
        </DndContext>
      </div>

      {(!!extra || !!showPaging) && (
        <div
          className={`flex flex-col ${extra ? 'justify-between' : 'justify-center'} w-full items-center mt-5 gap-4 lg:flex-row `}>
          {extra && <div className='w-full lg:w-auto'>{extra}</div>}
          {/* Pagination */}
          {showPaging && (
            <div className='flex justify-end relative max-w-screen'>
              <button disabled={!isGoToStart} onClick={onGoToStart}>
                <DoubleLeftOutlined className={clsx(`${!isGoToStart ? '!text-disabled cursor-not-allowed' : ''}`)} />
              </button>
              <Pagination
                defaultCurrent={1}
                total={paging?.total || 1}
                size='small'
                onChange={handlePageChange}
                className='flex justify-center !mt-0'
                pageSize={paging?.pageSize || initialPagingState.pageSize}
                current={paging?.pageNumber || initialPagingState.pageNumber}
              />
              <button disabled={!isGoToEnd} onClick={onGoToEnd}>
                <DoubleRightOutlined className={clsx(`${!isGoToEnd ? '!text-disabled  cursor-not-allowed' : ''}`)} />
              </button>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default CustomTable;
