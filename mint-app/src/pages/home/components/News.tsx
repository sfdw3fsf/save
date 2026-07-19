import Button from '@/components/core/button/Button';
import ArrowUpRightIcon from '@/components/icons/ArrowUpRight';
import { listNewsAPI } from '@/data/news/news.api';
import { NewsREQ } from '@/data/news/request/news.request';
import { ROUTES } from '@/router/routes.constant';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { fDate } from '@/utils/helpers/datetime.helper';
import { useQuery } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';

type NewItemProps = {
  image: string;
  category: {
    id: number;
    name: string;
  };
  createdAt: number;
  newsId: number;
  title: string;
  description: string;
};

const NewItem = ({ category, newsId, createdAt, description, image, title }: NewItemProps) => {
  const convertEpochToDateTime = (epochMills: number): string => {
    const date = new Date(epochMills);
    return fDate(date, 'YYYY MM DD');
  };

  // handle display ellipsis
  const MAX_LENGTH = 102;
  const handleDisplayEllipsis = (text: string): string => {
    if (text.length > MAX_LENGTH) {
      return text.slice(0, MAX_LENGTH) + '...';
    }
    return text;
  };

  const buildNewLink = (id: number): string => {
    return ROUTES.NEWS.DETAIL.replace(':id', id.toString());
  };

  const link = buildNewLink(newsId);

  return (
    <div className='overflow-clip rounded-xl h-fit lg:w-[370px] w-full shadow-md hover:shadow-primary-200 hover:shadow-lg cursor-pointer'>
      <a href={link} target='_blank' rel='noreferrer'>
        <img src={image} alt={title} className='h-60 lg:w-[370px] w-full rounded-t-2xl object-cover' />
      </a>
      <a href={link} target='_blank' rel='noreferrer'>
        <div className='p-4 flex gap-2 flex-col max-h-80 min-h-80'>
          <div className='px-4 bg-[#E2F7E1] w-fit  rounded-lg text-green-1000 font-medium text-sm py-0.5'>
            <p className='italic'>{category.name}</p>
          </div>
          <p className='text-primary-400 text-sm font-bold'>{convertEpochToDateTime(createdAt)}</p>
          <div className='flex items-start justify-between gap-4'>
            <p className='text-slate-800 text-xl font-bold line-clamp-4'>{title}</p>
            <ArrowUpRightIcon className='w-12 h-9' />
          </div>
          <p className='text-slate-500 text-base font-normal'>{handleDisplayEllipsis(description)}</p>
        </div>
      </a>
    </div>
  );
};
const News = () => {
  const { t: tCommon } = useTranslation('common');

  const request: Partial<NewsREQ> = {
    page: 1,
    size: 3,
  };

  const { data: listNews } = useQuery({
    queryKey: [QUERY_KEYS.NEWS, request],
    queryFn: () => listNewsAPI(request),
  });

  const gotoNews = () => {
    window.open(ROUTES.NEWS.self, '_blank');
  };

  return (
    <div className='lg:py-24 py-12 lg:px-28 px-3 w-full xl:w-4/5  flex flex-col gap-16'>
      <div>
        <h2 className='text-primary-600 text-sm text-center mb-3 font-medium'>News</h2>
        <p className='text-slate-800 md:text-4xl text-3xl text-center font-bold max-w-[768px] md:px-0 px-5 mx-auto mb-9'>
          {tCommon('home.news_title')}
        </p>
      </div>
      <div className='flex justify-center gap-4 md:flex-row flex-col overflow-x-auto w-full py-3'>
        {listNews?.data.map((item) => (
          <NewItem
            key={item.id}
            newsId={item.id}
            category={item.category}
            createdAt={item.createdAt}
            image={item.mainImage.url}
            description={item.shortDescription}
            title={item.title}
          />
        ))}
      </div>
      <Button className='md:w-40 w-full mx-auto text-primary-1000 font-semibold text-base' onClick={gotoNews}>
        {tCommon('home.see_more')}
      </Button>
    </div>
  );
};

export default News;
