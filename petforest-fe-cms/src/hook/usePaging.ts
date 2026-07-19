import { SORT_ORDER } from '@/utils/enums/sort-order.enum';
import { objectReplace } from '@/utils/helpers/params.helper';
import { PagingREQ } from '@/utils/types/paging.type';
import { FilterValue, SorterResult, TableCurrentDataSource } from 'antd/es/table/interface';
import { TablePaginationConfig } from 'antd/lib';
import { debounce, isArray } from 'lodash';
import { usePathname, useRouter, useSearchParams } from 'next/navigation';

import { orUndefined } from '@/utils/parser/io.parser';
import queryString from 'query-string';
import { useEffect, useState } from 'react';
import useRouterHandler from './useRouterHandler';

export type PagingFilterType<T> = {
  initialPaging?: PagingREQ;
  initialFilter?: T;
  checkPage1?: boolean;
  debounceTime?: number;
  searchParamDefault?: string[];
  checkedParams?: boolean;
  replace?: boolean;
  isPushURL?: boolean;
  filterKeyMapping?: Record<string, (value: any) => Record<string, any>>;
  customFilter?: Record<string, (value: any) => Record<string, any>>;
};

export function usePaging<T extends object>({
  initialPaging,
  initialFilter,
  checkPage1 = false,
  checkedParams = true,
  debounceTime = 0,
  isPushURL = true,
  searchParamDefault,
  replace = false,
  filterKeyMapping,
  customFilter,
}: PagingFilterType<T>) {
  const [filter, setFilter] = useState<T & PagingREQ>({
    ...(initialPaging ?? {}),
    ...initialFilter,
  } as T & PagingREQ);

  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();
  const { currentParams } = useRouterHandler();

  // when filter change, update url by new filter
  const handleFilterChange = debounce((filter: T, replace: boolean = false) => {
    setFilter((prev: any) => {
      const cur: any = objectReplace(filter, ['All'], '');

      let result: any = { ...prev, ...cur };
      result.filter = JSON.stringify(objectReplace({ ...JSON.parse(prev.filter || '{}'), ...JSON.parse(cur.filter || '{}') }));

      cur.filter = JSON.stringify(objectReplace({ ...JSON.parse(cur.filter || '{}') }));
      result = { ...objectReplace(replace ? { ...result, ...cur } : { ...result }), pageNumber: 1 };
      if (isPushURL) {
        handlePushFilter(result);
      }
      return result;
    });
  }, debounceTime);

  // when paging change, update url by new paging
  const handlePageChange = (paging: { pageNumber?: number; pageSize?: number }) => {
    const newParams = { ...filter, ...paging };
    setFilter(newParams);
    if (isPushURL) {
      handlePushFilter(newParams as any);
    }
  };

  const resetFilterPaging = (obj?: any) => {
    const newParams = {
      ...(initialPaging ? { pageNumber: initialPaging.pageNumber, pageSize: initialPaging.pageSize } : {}),
      ...obj,
    };
    setFilter(newParams as any);
    if (isPushURL) {
      handlePushFilter(newParams as any);
    }
  };

  // when paging change, update url by new paging
  const handlePageChange1 = (paging: { pageNumber1?: number; pageSize1?: number }) => {
    const newParams = { ...filter, ...paging };
    setFilter(newParams);
    if (isPushURL) {
      handlePushFilter(newParams);
    }
  };

  // when sort a column or filter of table
  const handleTableChange = (
    pagination: TablePaginationConfig,
    filters: Record<string, FilterValue | null>,
    sorter: SorterResult<T> | SorterResult<T>[],
    extra: TableCurrentDataSource<T>,
  ) => {
    //sorter
    const isSortable = !isArray(sorter) && sorter.field;
    const newTableFilter: Record<string, any> = {};
    const newField: Record<string, any> = {};

    Object.entries(filters).forEach(([key, value]) => {
      if (filterKeyMapping && filterKeyMapping[key]) {
        const filterObject = filterKeyMapping[key](orUndefined(value));

        if (filterObject?.type === 'array') {
          if (!newField[filterObject.newKey]) {
            newField[filterObject.newKey] = [];
          }
          if (filterObject.value) newField[filterObject.newKey].push(filterObject.value);
        } else if (filterObject?.newKey) {
          newField[filterObject.newKey] = filterObject.value;
        } else {
          Object.assign(newTableFilter, filterObject);
        }
      } else {
        if (isArray(value)) {
          newTableFilter[`${key}.in`] = value;
        } else {
          newTableFilter[`${key}.in`] = orUndefined(value);
        }
      }
    });
    const mergedFilter = { ...((filter as any).filter ? JSON.parse((filter as any).filter) : {}), ...newTableFilter };

    const sortOrder = isSortable ? (sorter.order === 'ascend' ? SORT_ORDER.ASC : SORT_ORDER.DESC) : undefined;
    const newFilter = {
      ...filter,
      pageNumber: 1,
      sortBy: isSortable && sorter.field,
      sortOrder: sortOrder,
      filter: Object.keys(mergedFilter).length === 0 ? undefined : JSON.stringify(mergedFilter),
      ...newField,
    };

    setFilter(newFilter);
  };

  const handlePushFilter = (filter: T & PagingREQ) => {
    if (checkedParams)
      if (checkPage1) {
        const pageNumber1 = searchParams.get('pageNumber1');
        const pageSize1 = searchParams.get('pageSize1');
        router.push(
          `${pathname}?${queryString.stringify({
            ...currentParams,
            ...filter,
            sortOrder: undefined,
            sortBy: undefined,
            filter: undefined,
            pageNumber1,
            pageSize1,
          })}`,
        );
      } else {
        const mode = searchParams.get('mode');
        router.push(
          `${pathname}?${queryString.stringify({
            ...currentParams,
            ...filter,
            sortOrder: undefined,
            sortBy: undefined,
            filter: undefined,
            ...(mode !== null && { mode }),
          })}`,
        );
      }
  };

  useEffect(() => {
    const pageSize = Number(searchParams.get('pageSize'));
    const pageNumber = Number(searchParams.get('pageNumber'));
    if (!pageSize || !pageNumber) return;
    setFilter((pre) => ({ ...pre, pageSize, pageNumber }));
  }, [searchParams]);

  useEffect(() => {
    setFilter((pre) => ({ ...pre, ...initialFilter }));
  }, [initialFilter]);

  return {
    filter,
    handleFilterChange,
    handlePageChange,
    resetFilterPaging,
    handlePageChange1,
    handleTableChange,
  };
}
