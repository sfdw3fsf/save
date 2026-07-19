import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { getInquiry } from './inquiry.api';
import { InquiryRESP } from './response/inquiry.response';
const itemsPerPage = 10;

export const useInquiry = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const queryClient = useQueryClient();
  const page = Number(searchParams.get('pageNo')) || 1;
  const search = searchParams.get('search');
  const categoryValue = searchParams.get('category') || undefined;

  const { data, isLoading } = useQuery<InquiryRESP, Error>({
    queryKey: [QUERY_KEYS.INQUIRIES, itemsPerPage, page, search, categoryValue],
    queryFn: () =>
      getInquiry({
        // page: page,
        // size: itemsPerPage,
        // searchType: ANNOUNCEMENT_SEARCH_TYPE.ALL,
        // search: search || undefined,
        // categoryId: Number(categoryValue),
        search: search || undefined,
        direction: 'DESC',
        categoryName: categoryValue,
        // categoryId: 1,
        // status: STATUS.NEW || STATUS.OLD,
        // answerStatus: ANSWER_STATUS.ANSWER || ANSWER_STATUS.PENDING,

        page: page,
        size: itemsPerPage,
      }),
  });

  if (data ? page < data.pagination.totalPages : null) {
    queryClient.prefetchQuery({
      queryKey: [QUERY_KEYS.INQUIRIES, itemsPerPage, page + 1, search, categoryValue],
      queryFn: () =>
        getInquiry({
          search: search || undefined,
          // categoryId: Number(categoryValue),
          // status: STATUS.NEW || STATUS.OLD,
          // answerStatus: ANSWER_STATUS.ANSWER || ANSWER_STATUS.PENDING,
          categoryName: categoryValue,
          direction: 'DESC',
          page: page,
          size: itemsPerPage,
        }),
    });
    if (data ? page > 1 : null) {
      queryClient.prefetchQuery({
        queryKey: [QUERY_KEYS.INQUIRIES, itemsPerPage, page - 1, search, categoryValue],
        queryFn: () =>
          getInquiry({
            search: search || undefined,
            // categoryId: Number(categoryValue),
            // categoryId: 1,
            // status: STATUS.NEW || STATUS.OLD,
            // answerStatus: ANSWER_STATUS.ANSWER || ANSWER_STATUS.PENDING,
            categoryName: categoryValue,
            direction: 'DESC',
            page: page,
            size: itemsPerPage,
          }),
      });
    }
  }
  //To give pagination the totalPages, currentPage(pageNo) information, searchSection and FaqList totalElements infor
  useEffect(() => {
    if (data) {
      searchParams.set('totalPages', data.pagination.totalPages.toString());
      searchParams.set('pageNo', page.toString());
      searchParams.set('totalElements', data.pagination.totalElements.toString());
      setSearchParams(searchParams,{ replace: true });
    }
  }, [searchParams, setSearchParams, data?.pagination.totalPages, data, page]);

  return { data, isLoading };
};
