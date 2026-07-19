import React from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import { EmptyFaq } from '../faq/EmptyFaq';
import { EmptySearch } from '../faq/EmptySearch';

type TableRowData = { [key: string]: string | number };

export type TableCardColumn = {
  header: string;
  value: string;
  classHeader?: string;
  classElement?: string;
  renderStyle?: JSX.Element | ((data: string | number) => JSX.Element | null);
};

const TableCard = ({ columns, data }: { columns: TableCardColumn[]; data: TableRowData[] }) => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const totalElements = searchParams.get('totalElements') ? Number(searchParams.get('totalElements')) : 0;
  const searchValue = searchParams.get('search') || '';

  const handleRowClick = (row: TableRowData) => {
    navigate(`./${row.id}`);
  };

  // Case 1: Empty Data
  if (totalElements === 0 && searchValue === '') {
    return <EmptyFaq />;
  }

  // Case 2: Empty Search
  if (totalElements === 0 && searchValue !== '') {
    return <EmptySearch />;
  }

  return (
    <div className='w-full md:border md:border-slate-200 rounded-lg md:bg-white'>
      <div className='table md:max-h-[682px] w-full md:table-auto'>
        {/* Table Header */}
        <div className='md:table-header-group hidden'>
          <div className='table-row'>
            {columns.map((column, index) => (
              <div
                key={index}
                className={twMerge('text-slate-500 text-xs font-medium p-3 table-cell border-b', column.classHeader)}
                style={{ verticalAlign: 'middle' }}>
                {column.header}
              </div>
            ))}
          </div>
        </div>

        {/* Table Body */}
        <div className='table-row-group'>
          {data.map((row, index) => (
            <div
              key={index}
              className='md:table-row border bg-white border-slate-200 rounded-lg md:border-none md:rounded-none mb-4 md:mb-0 p-4 md:p-0 hover:bg-gray-100'
              onClick={() => handleRowClick(row)}
              role='button'
              tabIndex={0}
              onKeyDown={(event) => {
                if (event.key === 'Enter' || event.key === ' ') {
                  handleRowClick(row);
                }
              }}>
              {columns.map((column, colIndex) => (
                <div
                  key={colIndex}
                  className={twMerge(`p-3 md:table-cell md:border-b md:h-14 `, column.classElement)}
                  style={{ verticalAlign: 'middle' }}>
                  <div className='grid grid-cols-3  md:block '>
                    {/* Mobile view header for card layout */}
                    <span className='col-span-1 md:hidden text-slate-500 text-xs font-medium'>{column.header}:</span>
                    <span className='col-span-2 text-sm font-normal text-slate-800 md:line-clamp-none line-clamp-1'>
                      {typeof column.renderStyle === 'function'
                        ? column.renderStyle(row[column.value])
                        : column.renderStyle
                          ? React.cloneElement(column.renderStyle, {
                              children: row[column.value],
                            })
                          : row[column.value]}
                    </span>
                  </div>
                </div>
              ))}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default TableCard;
