import { EmptySearch } from '@/components/core/faq/EmptySearch';
import SearchSection from '@/components/core/faq/SearchSection';
import PageContainer from '@/components/core/page-container/PageContainer';
import Pagination from '@/components/core/pagination/Pagination';
import Spinner from '@/components/core/spinner/Spinner';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import { getNewsCategoriesAPI } from '@/data/news/news.api';
import { useNews } from '@/data/news/news.service';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useSearchParams } from 'react-router-dom';
import { EmptyNews } from '../components/EmptyNews';
import NewsItem from '../components/NewsItem';

const NewsPage = PermissionAccept((): JSX.Element => {
  const { control } = useForm();
  const { t: tCommon } = useTranslation('common');
  const [searchParams] = useSearchParams();
  const totalElements = Number(searchParams.get('totalElements'));
  const searchValue = searchParams.get('search') || '';
  const { t: tFaq } = useTranslation('faq');

  const { data: listNews, isLoading } = useNews();
  const { data: newsCategory } = useQuery({
    queryKey: ['drop'],
    queryFn: () => getNewsCategoriesAPI(),
  });

  const dropdownItems = useMemo(
    () => [
      { label: tFaq('entire'), value: 'entire' },
      ...(newsCategory?.data?.map((faqs) => ({
        label: tFaq(`faqsDropdown.${faqs.name.toLowerCase()}` as any).includes('.')
          ? faqs.name
          : tFaq(`faqsDropdown.${faqs.name.toLowerCase()}` as any),
        value: faqs.name,
      })) || []),
    ],
    [newsCategory?.data, tFaq],
  );

  return (
    <PageContainer
      title={tCommon('news.title')}
      subtitle='News'
      showTitle
      isFullScreen
      hiddenScroll
      withFooter
      className='bg-white'>
      <div className='flex flex-col md:py-12 py-4 md:gap-4 gap-4 lg:gap-10   justify-center items-center w-full bg-[#F9FAFB]'>
        <div className='flex flex-col md:gap-6 gap-4 md:px-8 px-4 justify-center items-center w-full'>
          <div className='max-w-[768px] w-full '>
            <SearchSection control={control} dropdownItems={dropdownItems} localeName='faqsDropdown' />
          </div>
          {isLoading ? (
            <div className='flex justify-between items-center '>
              <Spinner raw />
            </div>
          ) : (
            <div className='flex flex-row w-full flex-wrap justify-center gap-6 max-w-[1300px]'>
              {listNews?.data.map((news: any, index: any) => <NewsItem key={index} news={news} />)}
            </div>
          )}

          {!isLoading && totalElements === 0 && searchValue !== '' && <EmptySearch />}
          {!isLoading && searchValue === '' && totalElements === 0 && <EmptyNews />}
        </div>

        {totalElements !== 0 && <Pagination />}
      </div>
    </PageContainer>
  );
});
export default NewsPage;
