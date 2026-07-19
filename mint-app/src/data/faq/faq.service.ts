import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { getFaqs } from './faq.api';
import { FaqRESP } from './response/faq.response';
export const useFaqs = (): any => {
  const [searchParams, setSearchParams] = useSearchParams();
  const queryClient = useQueryClient();
  const itemsPerPage = 10;
  const categoryValue = searchParams.get('category') || '';
  const currentPage = Number(searchParams.get('pageNo')) || 1;
  const searchValue = searchParams.get('search') || '';

  //Fetch
  const { isLoading, isError, data } = useQuery<FaqRESP, Error>({
    queryKey: [QUERY_KEYS.FAQS, { searchValue, categoryValue, currentPage, itemsPerPage }],
    queryFn: () =>
      getFaqs({
        search: searchValue,
        category: categoryValue,
        page: currentPage,
        size: itemsPerPage,
        // sort: 'id',
        direction: 'DESC',
      }),
  });

  //Prefetch for pagination
  if (data ? currentPage < data.pagination.totalPages : null) {
    queryClient.prefetchQuery({
      queryKey: [QUERY_KEYS.FAQS, { searchValue, categoryValue, currentPage: currentPage + 1, itemsPerPage }],
      queryFn: () =>
        getFaqs({
          search: searchValue,
          category: categoryValue,
          page: currentPage + 1,
          size: itemsPerPage,
          // sort: 'id',
          direction: 'DESC',
        }),
    });
  }
  if (data ? currentPage > 1 : null) {
    queryClient.prefetchQuery({
      queryKey: [QUERY_KEYS.FAQS, { searchValue, categoryValue, currentPage: currentPage - 1, itemsPerPage }],
      queryFn: () =>
        getFaqs({
          search: searchValue,
          category: categoryValue,
          page: currentPage - 1,
          size: itemsPerPage,
          // sort: 'id',
          direction: 'DESC',
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
