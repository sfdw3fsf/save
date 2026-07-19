'use client';
import '@/components/journal/journal.css';
import JournalInfoBanner from '@/components/journal/JournalInfoBanner';
import { TypeTab } from '@/components/journal/TypeTab';
import { ListData } from '@/components/list-data/ListData';

import { getPostListApi, journalKeys } from '@/store/customer-service/customer-service.api';
import { postRespToDto } from '@/store/customer-service/customer-service.service';
import { AnnouncementDTO, JournalKeys } from '@/store/customer-service/dto/customer-service.dto';
import { AnnouncementListREQ } from '@/store/customer-service/request/customer-service.request';
import { JOURNAL_FILTERS } from '@/utils/constant/customer-service/customer-service.constant';
import { POST_CATEGORIES } from '@/utils/enums/customer-service.enum';
import { initialPagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { Pagination } from 'antd';
import { useState } from 'react';

const JournalPage = () => {
  const [activeTag, setActiveTag] = useState<JournalKeys>(POST_CATEGORIES.JOURNAL);
  const [filter, setFilter] = useState<AnnouncementListREQ>({
    pageNumber: initialPagingState.pageNumber,
    pageSize: initialPagingState.pageSize,
    filter: JSON.stringify({
      ['category.in']: Object.keys(POST_CATEGORIES).filter((item) => item.startsWith('JOURNAL')),
      ['visibility.in']: ['RELEASE'],
    }),
  });

  const { data: announcementList } = useQuery({
    queryKey: journalKeys.list(filter),
    queryFn: () => getPostListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(postRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const handleSelectTab = (tab: any) => {
    setActiveTag(tab);
    setFilter((pre) => ({
      ...pre,
      filter: JSON.stringify({
        ['category.in']:
          tab === JOURNAL_FILTERS[0] ? Object.keys(POST_CATEGORIES).filter((item) => item.startsWith('JOURNAL')) : [tab],
        ['visibility.in']: ['RELEASE'],
      }),
    }));
  };

  const handlePageChange = (pageNumber: number, pageSize: number) => {
    setFilter((pre) => ({ ...pre, pageNumber, pageSize }));
  };

  return (
    <div className='cont lmt maxw' id='journal-list'>
      <div className='cont-head cont-head2 text-center !mb-[45px] md:mb-0'>
        <h1 className='text-green-main text-[25px] font-bold md:text-[36px]'>펫포레스트 저널</h1>
        <p className='mt-[17px] text-[14px] text-grey-sub  md:mt-[20px] md:text-[16px]'>
          반려동물과의 원활한 교감을 위한 크고 작은 팁부터, 우리 주변의 작고 따뜻한 사연들을 모았습니다.
        </p>

        <ListData
          data={JOURNAL_FILTERS as any}
          keyExtractor={(item) => item}
          renderItem={(item: JournalKeys) => (
            <TypeTab key={item} selectedTab={activeTag} tab={item} handleSelectTab={handleSelectTab} />
          )}
          styleList='!gap-[7px]'
          styleContainer='mt-[35px] md:mt-[50px]'
        />
      </div>

      <div className='cont-body'>
        <ListData<AnnouncementDTO>
          data={announcementList?.data || []}
          renderItem={(item: AnnouncementDTO) => (
            <JournalInfoBanner key={item.id} id={item.id} image={item.thumbnail} category={item.category} text={item.title} />
          )}
          keyExtractor={(item) => item.id}
          numberOfColumns={4}
          styleContainer='journal-ul'
        />
        <div className='mt-[30px] md:mt-[48px]'>
          <Pagination
            defaultCurrent={1}
            total={announcementList?.total || 1}
            size='small'
            onChange={handlePageChange}
            className='flex justify-center !mt-0'
            pageSize={filter?.pageSize || initialPagingState.pageSize}
            current={filter?.pageNumber || initialPagingState.pageNumber}
          />
        </div>
      </div>
    </div>
  );
};

export default JournalPage;
