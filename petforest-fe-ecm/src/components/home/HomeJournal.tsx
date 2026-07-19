/* eslint-disable jsx-a11y/anchor-is-valid */
import { announcementKeys, getPostListApi } from '@/store/customer-service/customer-service.api';
import { postRespToDto } from '@/store/customer-service/customer-service.service';
import { AnnouncementDTO } from '@/store/customer-service/dto/customer-service.dto';
import { POST_CATEGORIES } from '@/utils/enums/customer-service.enum';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import JournalInfoBanner from '../journal/JournalInfoBanner';
import { ListData } from '../list-data/ListData';

const HomeJournal = () => {
  const filter = {
    pageNumber: 1,
    pageSize: 4,
    filter: JSON.stringify({
      ['category.in']: Object.keys(POST_CATEGORIES).filter((item) => item.startsWith('JOURNAL')),
      ['visibility.in']: ['RELEASE'],
    }),
  };

  const { data: announcementList } = useQuery({
    queryKey: announcementKeys.list(filter),
    queryFn: () => getPostListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(postRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  return (
    <div className='sec lmt maxw !mb-[110px]'>
      <div className='sec-head !mb-[34px]'>
        <h2>펫포레스트 저널</h2>
        <p className='!mt-[15px] !mb-[30px] leading-[1.68] '>
          반려동물과의 원활한 교감을 위한 크고 작은 팁부터, 우리 주변의 작고 따뜻한 사연들을 모았습니다.
        </p>
        <a href='/about/journal' className='btn'>
          저널 더보기
        </a>
      </div>
      <div className='flex gap-[36px] home-journal-list'>
        <ListData<AnnouncementDTO>
          data={announcementList?.data.slice(0, 4) || []}
          renderItem={(item: AnnouncementDTO) => (
            <JournalInfoBanner key={item.id} id={item.id} image={item.thumbnail} category={item.category} text={item.title} />
          )}
          keyExtractor={(item) => item.id}
          numberOfColumns={4}
          styleContainer='journal-ul'
        />
      </div>
    </div>
  );
};

export default HomeJournal;
