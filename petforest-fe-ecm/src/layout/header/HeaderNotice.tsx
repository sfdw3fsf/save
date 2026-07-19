/* eslint-disable jsx-a11y/anchor-is-valid */
'use client';
import { getPostListApi, journalKeys } from '@/store/customer-service/customer-service.api';
import { postRespToDto } from '@/store/customer-service/customer-service.service';
import { CloseIcon } from '@chakra-ui/icons';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useEffect, useState } from 'react';

const HeaderNotice = () => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const filter = {
    pageNumber: 1,
    pageSize: 3,
    filter: JSON.stringify({
      ['category.in']: ['ANNOUNCEMENT'],
      ['visibility.in']: ['RELEASE'],
    }),
  };

  const { data: announcementList } = useQuery({
    queryKey: journalKeys.list(filter),
    queryFn: () => getPostListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(postRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % (announcementList?.total || 1));
    }, 3000);

    return () => clearInterval(intervalId);
  }, [announcementList?.total]);

  return (
    <div className='header-notice'>
      {announcementList?.data.map((notice, index) => (
        <a key={index} href='#' className={`header-notice-tag ${currentIndex === index ? 'current' : ''}`}>
          <p>{notice.title}</p>
        </a>
      ))}
      <div className='absolute top-0 right-[10px] font-normal cursor-pointer'>
        닫기 <CloseIcon boxSize={2.5} ml={1} />
      </div>
    </div>
  );
};

export default HeaderNotice;
