'use client';
import '@/components/journal/journal-detail.css';
import '@/components/journal/journal.css';
import JournalInfoBanner from '@/components/journal/JournalInfoBanner';
import { ListData } from '@/components/list-data/ListData';
import { getPostDetailApi, getPostListApi, journalKeys } from '@/store/customer-service/customer-service.api';
import { announcementDetailRespToDto, postRespToDto } from '@/store/customer-service/customer-service.service';
import { AnnouncementDTO } from '@/store/customer-service/dto/customer-service.dto';
import { JOURNAL_TEXT } from '@/utils/constant/customer-service/customer-service.constant';
import { MY_ROUTE } from '@/utils/constant/route.constant';
import { POST_CATEGORIES } from '@/utils/enums/customer-service.enum';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import Link from 'next/link';
import { useParams } from 'next/navigation';

const JournalDetailPage = () => {
  const { id } = useParams();

  const { data: journalDetailData } = useQuery({
    queryKey: journalKeys.detail(Number(id)),
    queryFn: () => getPostDetailApi(Number(id)),
    select: (response) => {
      return response.data.data && announcementDetailRespToDto(response.data.data);
    },
    enabled: !!id,
  });

  const { data: journalList } = useQuery({
    queryKey: journalKeys.lists(),
    queryFn: () =>
      getPostListApi({
        pageNumber: 1,
        pageSize: 3,
        filter: JSON.stringify({
          ['category.in']: Object.keys(POST_CATEGORIES).filter((item) => item.startsWith('JOURNAL')),
          ['visibility.in']: ['RELEASE'],
        }),
      }),
    select: (response) => {
      return response.data.data.filter((item) => item.id !== Number(id)).map(postRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  return (
    <div className='cont !overflow-hidden' id='single-journal'>
      <div className='cont-head !h-[270px] relative mb-[79px] md:mb-[80px] md:!h-[600px]'>
        <img src={journalDetailData?.thumbnail} alt={''} className='static w-full h-full object-cover object-bottom' />
        <div className='absolute top-1/2 left-1/2 w-[80%] text-center transform -translate-x-1/2 -translate-y-1/2'>
          <span className='border border-solid border-yellow-pale px-5 py-2 rounded-full md:text-[13px] text-[11px] text-yellow-pale font-medium'>
            {JOURNAL_TEXT[journalDetailData?.category as keyof typeof JOURNAL_TEXT]}
          </span>
          <h3 className='text-[25px] font-bold mt-4 text-yellow-pale md:text-[30px] '>{journalDetailData?.title}</h3>
        </div>
      </div>
      <div className='flex items-center justify-center'>
        <div className='text-left max-w-[650px]' dangerouslySetInnerHTML={{ __html: journalDetailData?.content as string }} />
      </div>
      <div className='lmtw lmt mt-[28px]'>
        <Link href={MY_ROUTE.ABOUT.JOURNAL} className='btn btn-primary'>
          목록보기
        </Link>
        <div className='border-gray-300 mt-[90px] text-center  md:text-left md:pt-20 pb-[35px] md:border-t '>
          <h3 className='text-green-main text-2xl font-bold'>다른 기사 살펴보기</h3>
        </div>
        <div id='journal-list'>
          <ListData<AnnouncementDTO>
            data={journalList || []}
            renderItem={(item: AnnouncementDTO) => (
              <JournalInfoBanner key={item.id} id={item.id} image={item.thumbnail} category={item.category} text={item.title} />
            )}
            keyExtractor={(item) => item.id}
            numberOfColumns={3}
            styleContainer='journal-ul exw'
          />
        </div>
      </div>
    </div>
  );
};

export default JournalDetailPage;
