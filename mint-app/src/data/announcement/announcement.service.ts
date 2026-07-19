import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { ANNOUNCEMENT_SEARCH_TYPE } from '@/utils/enums/data/announcement-type.enum';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { getAnnouncement } from './announcement.api';
import { AnnouncementRESP } from './response/announcement.response';
const itemsPerPage = 10;

export const useAnnouncement = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const queryClient = useQueryClient();
  const page = Number(searchParams.get('pageNo')) || 1;
  const search = searchParams.get('search');
  const categoryValue = searchParams.get('category') || '';
  const { data, isLoading } = useQuery<AnnouncementRESP, Error>({
    queryKey: [QUERY_KEYS.ANNOUNCEMENTS, itemsPerPage, page, search, categoryValue],
    queryFn: () =>
      getAnnouncement({
        page: page,
        size: itemsPerPage,
        searchType: ANNOUNCEMENT_SEARCH_TYPE.ALL,
        search: search || undefined,
        category: categoryValue,
        direction: 'DESC',
      }),
  });

  if (data ? page < data.pagination.totalPages : null) {
    queryClient.prefetchQuery({
      queryKey: [QUERY_KEYS.ANNOUNCEMENTS, itemsPerPage, page + 1, search, categoryValue],
      queryFn: () =>
        getAnnouncement({
          page: page + 1,
          size: itemsPerPage,
          searchType: ANNOUNCEMENT_SEARCH_TYPE.ALL,
          search: search || undefined,
          category: categoryValue,
          direction: 'DESC',
        }),
    });
    if (data ? page > 1 : null) {
      queryClient.prefetchQuery({
        queryKey: [QUERY_KEYS.ANNOUNCEMENTS, itemsPerPage, page - 1, search, categoryValue],
        queryFn: () =>
          getAnnouncement({
            page: page - 1,
            size: itemsPerPage,
            searchType: ANNOUNCEMENT_SEARCH_TYPE.ALL,
            search: search || undefined,
            category: categoryValue,
            direction: 'DESC',
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
