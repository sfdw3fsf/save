import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
// import { getNEWS } from './faq.api';
// import { FaqRESP } from './response/faq.response';
// import { getNewsAPI } from './news.api';
import { listNewsAPI } from './news.api';
export const useNews = (): any => {
  const [searchParams, setSearchParams] = useSearchParams();
  const queryClient = useQueryClient();
  const itemsPerPage = 9;
  const categoryValue = searchParams.get('category') || '';
  const currentPage = Number(searchParams.get('pageNo')) || 1;
  const searchValue = searchParams.get('search') || '';

  //Fetch
  const { isLoading, isError, data } = useQuery({
    queryKey: [QUERY_KEYS.NEWS, { searchValue, categoryValue, currentPage, itemsPerPage }],
    queryFn: () =>
      listNewsAPI({
        search: searchValue,
        category: categoryValue,
        searchType: 'ALL',
        page: currentPage,
        size: itemsPerPage,
        // sort: 'id',
        // direction: 'DESC',
      }),
  });

  //Prefetch for pagination
  if (data ? currentPage < data.pagination.totalPages : null) {
    queryClient.prefetchQuery({
      queryKey: [QUERY_KEYS.NEWS, { searchValue, categoryValue, currentPage: currentPage + 1, itemsPerPage }],
      queryFn: () =>
        listNewsAPI({
          search: searchValue,
          category: categoryValue,
          page: currentPage + 1,
          size: itemsPerPage,
          searchType: 'ALL',
          // sort: 'id',
          // direction: 'DESC',
        }),
    });
  }
  if (data ? currentPage > 1 : null) {
    queryClient.prefetchQuery({
      queryKey: [QUERY_KEYS.NEWS, { searchValue, categoryValue, currentPage: currentPage - 1, itemsPerPage }],
      queryFn: () =>
        listNewsAPI({
          search: searchValue,
          category: categoryValue,
          page: currentPage - 1,
          size: itemsPerPage,
          searchType: 'ALL',
          // sort: 'id',
          // direction: 'DESC',
        }),
    });
  }

  //To give pagination the totalPages, currentPage(pageNo) information, searchSection and FaqList totalElements infor
  useEffect(() => {
    if (data) {
      searchParams.set('totalPages', data.pagination.totalPages.toString());
      searchParams.set('pageNo', currentPage.toString());
      searchParams.set('totalElements', data.pagination.totalElements.toString());
      setSearchParams(searchParams,{ replace: true });
    }
  }, [searchParams, setSearchParams, data?.pagination.totalPages, data, currentPage]);

  return { isLoading, isError, data };
};
