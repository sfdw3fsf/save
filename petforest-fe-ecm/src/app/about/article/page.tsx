'use client';
import PageContainer from '@/components/common/page-container/PageContainer';
import { ListData } from '@/components/list-data/ListData';

import { PostListREQ } from '@/store/branch/request/branch.request';
import { getPostListApi, pressReportKeys } from '@/store/customer-service/customer-service.api';
import { postRespToDto } from '@/store/customer-service/customer-service.service';
import { Post } from '@/store/customer-service/dto/customer-service.dto';
import { POST_CATEGORIES } from '@/utils/enums/customer-service.enum';
import { initialPagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { Pagination } from 'antd';
import Link from 'next/link';
import { useState } from 'react';

const ArticlePagePage = () => {
  const [filter, setFilter] = useState<PostListREQ>({
    pageNumber: initialPagingState.pageNumber,
    pageSize: initialPagingState.pageSize,
    filter: JSON.stringify({
      ['category.in']: [POST_CATEGORIES.PRESS_REPORT],
      ['visibility.in']: ['RELEASE'],
    }),
  });

  const { data: pressReportList } = useQuery({
    queryKey: pressReportKeys.list(filter),
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
    <PageContainer>
      <div className='sec !mb-[70px] md:!mb-[105px]'>
        <div className='lmt maxw flex flex-col items-center justify-center mt-[50px] md:mt-[100px]'>
          <p className='text-[25px] text-green-main leading-[1.5] font-bold mb-[50px] md:mb-[80px] md:text-[33px]'>언론 보도</p>
          <ListData<Post>
            data={pressReportList?.data || []}
            keyExtractor={(item) => item.id}
            renderItem={(article: Post) => (
              <Link
                href={article.link}
                target='_blank'
                rel='noopener noreferrer'
                className={`flex flex-col gap-[6px] border-b border-solid border-neutral-200 py-[16px] cursor-pointer hover:bg-ligt-green hover:duration-[0.25s] ${article.id === 1 && 'border-t'} md:py-[20px] md:flex-row md:justify-between pr-[20px]`}>
                <div className='flex items-center gap-[11px] leading-[20px]'>
                  <p className='text-black-nero text-[15px] font-semibold md:text-[16px] md:font-medium'>{article.title}</p>
                </div>
                <span className='text-[12px] text-grey-green md:text-[13px] '>{article.createdAt}</span>
              </Link>
            )}
            styleContainer='md:w-[60%]'
            styleList='flex-col !gap-0'
          />
          <div className='mt-[30px] md:mt-[60px]'>
            <Pagination
              defaultCurrent={1}
              total={pressReportList?.total || 1}
              size='small'
              onChange={handlePageChange}
              className='flex justify-center !mt-0'
              pageSize={filter?.pageSize || initialPagingState.pageSize}
              current={filter?.pageNumber || initialPagingState.pageNumber}
            />
          </div>
        </div>
      </div>
    </PageContainer>
  );
};

export default ArticlePagePage;
