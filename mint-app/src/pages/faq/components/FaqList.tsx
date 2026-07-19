import { EmptyFaq } from '@/components/core/faq/EmptyFaq';
import { EmptySearch } from '@/components/core/faq/EmptySearch';
import Spinner from '@/components/core/spinner/Spinner';
import { useFaqs } from '@/data/faq/faq.service';
import React from 'react';
import { useSearchParams } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import { FaqItem } from './FaqItem';

type Faq = {
  question: string;
  answer: string;
  category: string;
};

type FaqListProps = {
  faqs?: Faq[];
  className?: string;
};

const FaqList: React.FC<FaqListProps> = ({ className }) => {
  const { data: faqs, isLoading, isError } = useFaqs();
  const [searchParams] = useSearchParams();
  const totalElements = searchParams.get('totalElements') ? Number(searchParams.get('totalElements')) : 0;
  const searchValue = searchParams.get('search') || '';

  // Case 0: Data is loading
  if (isLoading) {
    return <Spinner />;
  }

  // Case 1: Empty data
  if (totalElements === 0 && searchValue === '') {
    return <EmptyFaq />;
  }

  // Case 2: Empty Search
  if (totalElements === 0 && searchValue !== '') {
    return <EmptySearch />;
  }
  // Case 3 (default): Has Datas
  return (
    <div className={twMerge('flex flex-col gap-4 md:gap-6 rounded-lg shadow-sm mb-6 mt-2 ', className)}>
      {faqs?.data.map((faq: any) => (
        <FaqItem key={faq.id} question={faq.question} answer={faq.answer} tag={faq?.category.name} />
      ))}
    </div>
  );
};

export default FaqList;
