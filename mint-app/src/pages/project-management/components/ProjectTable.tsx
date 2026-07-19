import { EmptySearch } from '@/components/core/faq/EmptySearch';
import Loading from '@/components/core/loading/Loading';
import SecondPagination, { SecondPaginationConfigsProps } from '@/components/core/second-pagination/SecondPagination';
import { useMemo } from 'react';
import { twMerge } from 'tailwind-merge';

export type ProjectTableColumn<TKey extends keyof TData, TData> = {
  header: string;
  value?: TKey | null;
  render?: JSX.Element | ((data: TData[TKey], record?: TData) => JSX.Element | null);
  renderHeader?: (header: string) => JSX.Element | null;
  hidden?: boolean;
};

type ProjectTableProps<TData> = {
  columns: ProjectTableColumn<keyof TData, TData>[];
  data: TData[];
  isLoading?: boolean;
  onRowClick?: (row: TData) => void;
  paginationConfigs: SecondPaginationConfigsProps;
};
const ProjectTable = <TData,>({
  columns,
  data,
  isLoading = false,
  onRowClick,
  paginationConfigs,
}: ProjectTableProps<TData>): JSX.Element => {
  const isEmpty = useMemo(() => data?.length === 0, [data?.length]);

  const handleRowClick = (row: TData) => {
    onRowClick?.(row);
  };
  if (isEmpty && !isLoading) return <EmptySearch />;
  return (
    <div
      className={twMerge(
        'w-full md:border md:border-slate-200 rounded-lg md:bg-white my-4 overflow-x-auto relative',
        isEmpty && 'h-[200px] overflow-hidden',
      )}>
      {isLoading && <Loading className='bg-[#C0C0C080] absolute min-h-full h-full' />}
      <div className='table w-full md:table-auto'>
        {/* Table Header */}
        <div className='md:table-header-group hidden'>
          <div className='table-row'>
            {columns?.map((column, index) => {
              if (column?.hidden) return null;
              return (
                <div key={index} className={'text-slate-500 text-xs font-medium table-cell border-b'}>
                  {column.renderHeader ? column.renderHeader(column.header) : <div className='p-3'>{column.header}</div>}
                </div>
              );
            })}
          </div>
        </div>

        {/* Table Body */}
        <div className='table-row-group'>
          {data?.map((row, rowIndex) => (
            <div
              key={rowIndex}
              className={
                'md:table-row border bg-white border-slate-200 rounded-lg md:border-none md:rounded-none mb-4 md:mb-0 p-4 md:p-0 hover:bg-gray-100'
              }
              onClick={() => handleRowClick(row)}
              role='button'
              tabIndex={0}
              onKeyDown={(event) => {
                if (event.key === 'Enter' || event.key === ' ') {
                  handleRowClick(row);
                }
              }}>
              {columns?.map((column, colIndex) => {
                const val = column.value ? row[column.value] : null;
                if (column?.hidden) return null;
                return (
                  <div key={colIndex} className={'p-3 md:table-cell md:h-14 md:border-b'} style={{ verticalAlign: 'middle' }}>
                    <div className='grid grid-cols-3 md:block'>
                      {/* Mobile view header for card layout */}
                      {column.header && (
                        <span className='col-span-1 md:hidden text-slate-500 text-xs font-medium'>{column.header} </span>
                      )}

                      <span
                        className={twMerge(
                          'text-sm font-normal text-slate-800 md:line-clamp-none line-clamp-1',
                          column.header ? 'col-span-2' : 'col-span-3',
                        )}>
                        {
                          typeof column.render === 'function'
                            ? column.render(val!, row) // Call render function with value
                            : column.render
                              ? column.render // Render ReactNode directly
                              : String(val ?? '') // Convert value to string and render
                        }
                      </span>
                    </div>
                  </div>
                );
              })}
            </div>
          ))}
        </div>
      </div>
      <SecondPagination {...paginationConfigs} />
    </div>
  );
};

export default ProjectTable;
