import PageContainer from '@/components/core/page-container/PageContainer';
import Pagination from '@/components/core/pagination/Pagination';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import { getFaqsCategoriesAPI } from '@/data/faq/faq.api';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useSearchParams } from 'react-router-dom';
import SearchSection from '../../components/core/faq/SearchSection';
import { SupportSection } from '../../components/core/faq/SupportSection';
import FaqList from './components/FaqList';

const FaqPage = PermissionAccept((): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const { control } = useForm();
  const [searchParams] = useSearchParams();
  const totalPages = Number(searchParams.get('totalPages'));
  const { data: faqsCategory } = useQuery({
    queryKey: ['drop'],
    queryFn: () => getFaqsCategoriesAPI(),
  });

  const dropdownItems = useMemo(
    () => [
      { label: tFaq('entire'), value: 'entire' },
      ...(faqsCategory?.data?.map((faqs) => ({
        label: tFaq(`faqsDropdown.${faqs.name.toLowerCase()}` as any).includes('.')
          ? faqs.name
          : tFaq(`faqsDropdown.${faqs.name.toLowerCase()}` as any),
        value: faqs.name,
      })) || []),
    ],
    [faqsCategory?.data, tFaq],
  );

  return (
    <PageContainer
      subtitle={tFaq('faqs')}
      showTitle
      isFullScreen
      withFooter
      hiddenScroll
      title={tFaq('frequentlyAskedQuestions')}>
      <div className='w-full max-w-[768px] pt-10 lg:pt-12 max-sm:py-6 px-4 md:px-0'>
        <SearchSection control={control} dropdownItems={dropdownItems} localeName='faqsDropdown' />

        <FaqList />
        {totalPages !== 0 && <Pagination />}

        <SupportSection />
      </div>
    </PageContainer>
  );
});

export default FaqPage;
