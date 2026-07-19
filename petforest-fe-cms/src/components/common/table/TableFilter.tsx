'use client';
import { ReactNode } from 'react';
import { useTranslation } from 'react-i18next';

type TableFilterProps = {
  total?: number;
  children?: ReactNode;
  hideTotal?: boolean;
};

const TableFilter = ({ total = 0, children, hideTotal = false }: TableFilterProps) => {
  const { t: tCommon } = useTranslation('common');
  return (
    <div className='w-full flex justify-between flex-wrap items-center gap-3'>
      {!hideTotal && <span className='text-neutral-80'>{`${tCommon('total')} ${total}${tCommon('number')}`}</span>}
      <div className='w-full lg:w-auto'>{children}</div>
    </div>
  );
};

export default TableFilter;
