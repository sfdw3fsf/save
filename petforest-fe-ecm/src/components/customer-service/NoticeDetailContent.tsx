'use client';
import { announcementKeys, getPostDetailApi, getPostListApi } from '@/store/customer-service/customer-service.api';
import { announcementDetailRespToDto, postRespToDto } from '@/store/customer-service/customer-service.service';
import { MY_ROUTE } from '@/utils/constant/route.constant';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { Divider } from 'antd';
import Link from 'next/link';
import { useParams } from 'next/navigation';

const NoticeDetailContent = () => {
  const { noticeId } = useParams();

  const { data: announcementDetailData } = useQuery({
    queryKey: announcementKeys.detail(Number(noticeId)),
    queryFn: () => getPostDetailApi(Number(noticeId)),
    select: (response) => {
      return response.data.data && announcementDetailRespToDto(response.data.data);
    },
    enabled: !!noticeId,
  });

  const { data: announcementList } = useQuery({
    queryKey: announcementKeys.lists(),
    queryFn: () =>
      getPostListApi({
        pageNumber: 1,
        pageSize: 3,
        filter: JSON.stringify({ ['category.in']: ['ANNOUNCEMENT'], ['visibility.in']: ['RELEASE'] }),
      }),
    select: (response) => {
      return response.data.data.filter((item) => item.id !== Number(noticeId)).map(postRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  return (
    <div className='sec'>
      <div className='lmt maxw flex flex-col items-center  justify-center mt-[50px] md:mt-[100px]'>
        <p className='text-[25px] text-green-main leading-[1.5] font-bold mb-[22px] md:mb-[17px] md:text-[24px] md:text-black-nero'>
          {announcementDetailData?.title}
        </p>
        <span className='text-[14px] text-grey-green text-center mb-[35px] md:w-[60%] md:pb-[32px] md:mb-[39px] md:border-b md:border-solid md:border-neutral-200'>
          {announcementDetailData?.createdAt}
        </span>
        {/* <div className='mx-[5px] mb-[20px] md:w-[60%] md:md-[40px]'>
          <img src={announcementDetailData?.thumbnail} alt='thumbnail' />
        </div> */}
        <div className='flex items-center justify-center'>
          <div
            className='ql-editor self-start text-left max-w-[650px]'
            dangerouslySetInnerHTML={{ __html: announcementDetailData?.content as string }}
          />
        </div>

        <Divider />

        <Link href={MY_ROUTE.CUSTOMER_SERVICE.NOTICE} className='btn btn-primary mt-[28px]'>
          목록보기
        </Link>
        <div className='mt-[55px] w-full flex flex-col md:w-[60%] md:flex-wrap md:mt-[73px]'>
          <p className='text-[20px] text-green-main leading-[1.5] font-bold mb-[20px] md:text-[24px] md:mb-[30px]'>
            공지사항 더보기
          </p>
          <div className='flex flex-col md:flex-row md:justify-between'>
            {announcementList?.slice(0, 2).map((notice, index) => (
              <Link
                href={`${MY_ROUTE.CUSTOMER_SERVICE.NOTICE}/${notice.id}`}
                key={index}
                className={`flex flex-col gap-[5px] border-b border-solid border-neutral-200 pb-[20px] pt-[7px] ${index === 0 && 'border-t'} md:w-[45%] md:border-t md:gap-[6px] md:border-b-0`}>
                <div className='flex items-center gap-[10px]'>
                  <p className='text-black-nero text-[15px] font-semibold md:text-[17px] md:font-medium'>{notice.title}</p>
                </div>
                <span className='text-[12px] text-grey-green  md:mr-[20px] md:text-[15px]'>{notice?.createdAt}</span>
              </Link>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default NoticeDetailContent;
