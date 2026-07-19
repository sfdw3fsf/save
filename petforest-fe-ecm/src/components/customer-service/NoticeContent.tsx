'use client';
import { announcementKeys, getPostListApi } from '@/store/customer-service/customer-service.api';
import { postRespToDto } from '@/store/customer-service/customer-service.service';
import { AnnouncementDTO } from '@/store/customer-service/dto/customer-service.dto';
import { AnnouncementListREQ } from '@/store/customer-service/request/customer-service.request';
import { MY_ROUTE } from '@/utils/constant/route.constant';
import { initialPagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { Pagination } from 'antd';
import Link from 'next/link';
import { useState } from 'react';
import { ListData } from '../list-data/ListData';

const NoticeContent = () => {
  const [filter, setFilter] = useState<AnnouncementListREQ>({
    pageNumber: initialPagingState.pageNumber,
    pageSize: initialPagingState.pageSize,
    filter: JSON.stringify({ ['category.in']: ['ANNOUNCEMENT'], ['visibility.in']: ['RELEASE'] }),
  });

  const { data: announcementList } = useQuery({
    queryKey: announcementKeys.list(filter),
    queryFn: () => getPostListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(postRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const handlePageChange = (pageNumber: number, pageSize: number) => {
    setFilter((pre) => ({ ...pre, pageNumber, pageSize }));
  };

  return (
    <div className='sec !mb-[70px] md:!mb-[150px]'>
      <div className='lmt maxw flex flex-col items-center justify-center mt-[50px] md:mt-[100px]'>
        <p className='text-[25px] text-green-main leading-[1.5] font-bold mb-[50px] md:mb-[80px] md:text-[33px]'>공지사항</p>
        <ListData<AnnouncementDTO>
          data={announcementList?.data || []}
          keyExtractor={(item) => item.id}
          renderItem={(notice: AnnouncementDTO) => (
            <Link
              href={`${MY_ROUTE.CUSTOMER_SERVICE.NOTICE}/${notice.id}`}
              className={`flex flex-col gap-[6px] border-b border-solid border-neutral-200 py-4 hover:bg-ligt-green hover:duration-[0.25s] ${notice.id === 1 && 'border-t'} md:flex-row md:justify-between `}>
              <div className='flex items-center gap-[10px]'>
                <p className='text-black-nero text-[15px] font-semibold md:text-[16px] md:font-medium'>{notice.title}</p>
              </div>
              <span className='text-[12px] text-grey-green md:text-[13px] md:mr-[20px]'>{notice?.createdAt}</span>
            </Link>
          )}
          styleContainer='w-full md:w-[60%]'
          styleList='flex-col !gap-0'
        />
        <div className='mt-[30px] md:mt-[60px]'>
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

export default NoticeContent;
