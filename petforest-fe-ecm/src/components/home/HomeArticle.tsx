import { getPostListApi, pressReportKeys } from '@/store/customer-service/customer-service.api';
import { postRespToDto } from '@/store/customer-service/customer-service.service';
import { Post } from '@/store/customer-service/dto/customer-service.dto';
import { POST_CATEGORIES, POST_EXPOSURE } from '@/utils/enums/customer-service.enum';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import Link from 'next/link';
import { useMemo } from 'react';
import { ListData } from '../list-data/ListData';

const HomeArticle = () => {
  const filter = {
    filter: JSON.stringify({
      ['category.in']: [POST_CATEGORIES.PRESS_REPORT],
      ['visibility.in']: ['RELEASE'],
    }),
  };

  const { data: pressReportList } = useQuery({
    queryKey: pressReportKeys.list(filter),
    queryFn: () => getPostListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(postRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const pressReportExposed = useMemo(() => {
    return pressReportList?.data.filter((item) => item.exposure === POST_EXPOSURE.EXPOSED).slice(0, 5);
  }, [pressReportList?.data]);

  return (
    <div className='sec border-b border-b-[#ccc] md:mb-[120px]'>
      <div className='sec-head !mb-[26px] md:!mb-8'>
        <h2>펫포레스트 언론보도</h2>
        <a href='/about/article' className='btn mt-5 md:mt-[25px]'>
          보도 더보기
        </a>
      </div>
      <div className='mb-[80px] items-center justify-center lmt max-w-[1024px] md:mb-[130px]'>
        <ListData<Post>
          data={pressReportExposed || []}
          keyExtractor={(item) => item.id}
          renderItem={(article: Post) => (
            <Link
              href={article.link}
              target='_blank'
              rel='noopener noreferrer'
              className={`flex flex-col gap-[10px] border-b border-solid border-neutral-200 py-[16px] cursor-pointer hover:bg-ligt-green hover:duration-[0.25s] ${article.id === 1 && 'border-t'} md:py-[20px] md:flex-row md:justify-between pr-[20px]`}>
              <div className='flex items-center gap-[11px]'>
                <p className='text-black-nero text-[15px] font-semibold md:text-[16px] md:font-medium'>{article.title}</p>
              </div>
              <span className='text-[12px] text-grey-green md:text-[13px] '>{article.createdAt}</span>
            </Link>
          )}
          styleList='flex-col !gap-0'
        />
      </div>
    </div>
  );
};

export default HomeArticle;
