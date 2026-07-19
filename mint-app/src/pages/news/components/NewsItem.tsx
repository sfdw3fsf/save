import Badge from '@/components/core/badge/Badge';
import ArrowUpRightIcon from '@/components/icons/ArrowUpRight';
import { NewsDTO } from '@/data/news/dto/news.dto';
import useDateFormat from '@/hooks/useDateFormat';
import { ROUTES } from '@/router/routes.constant';
import { IMAGES } from '@/utils/theme';
import { useNavigate } from 'react-router-dom';

type NewsItemProps = {
  news: NewsDTO;
};
const NewsItem = ({ news }: NewsItemProps) => {
  const navigate = useNavigate();
  const onClickNews = () => {
    navigate(`${ROUTES.NEWS.self}/${news.id}`);
  };
  return (
    <button
      onClick={onClickNews}
      className='flex flex-col gap-4 bg-white rounded-2xl overflow-hidden md:max-w-[389px] min-w-[343px] md:max-h-[474px] max-w-[343px] max-h-[450px] h-full shadow-md  w-full'>
      <div className='max-h-[240px] h-full max-w-[389px] w-full'>
        <img className='object-cover h-[240px] w-[389.33px] ' src={news?.mainImage.url || IMAGES.APP.PLACEHOLDER_IMAGE} />
      </div>
      <div className='bg-white flex flex-col gap-2 px-4 pb-4 h-[218px] w-full '>
        <div className='mb-1'>
          <Badge label={news?.category?.name} />
        </div>
        <p className='text-primary-500 text-sm font-bold text-start'>
          {useDateFormat('DD/MM/YYYY', news?.createdAt ? new Date(news.createdAt) : new Date())}
        </p>
        <div className='flex items-start gap-4 justify-between'>
          <p className='text-xl font-medium text-slate-800 text-start line-clamp-2'>{news?.title}</p>
          <ArrowUpRightIcon className='w-12 h-8' />
        </div>
        <p className='text-md font-normal text-slate-500 text-start line-clamp-2'>{news?.shortDescription}</p>
      </div>
    </button>
  );
};

export default NewsItem;
