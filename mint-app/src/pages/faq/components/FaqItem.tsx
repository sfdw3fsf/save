import Badge from '@/components/core/badge/Badge';
import MinusIcon from '@/components/icons/MinusIcon';
import PlusIcon from '@/components/icons/PlusIcon';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useSearchParams } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';

type FaqItemProps = {
  question: string;
  answer: string;
  tag: string;
  className?: string;
};

export const FaqItem = ({ question, answer, tag, className }: FaqItemProps): JSX.Element => {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [isOverflowEnabled, setIsOverflowEnabled] = useState<boolean>(false);
  const [searchParams] = useSearchParams();
  const { t: tFaq } = useTranslation('faq');
  const searchedValue = searchParams.get('search') || '';
  const toggled = () => {
    setIsOpen((prev) => !prev);
  };
  // For mobile scrolling y behavior
  useEffect(() => {
    if (isOpen) {
      const timeoutId = setTimeout(() => {
        setIsOverflowEnabled(true);
      }, 300);
      return () => clearTimeout(timeoutId);
    } else {
      setIsOverflowEnabled(false);
    }
  }, [isOpen]);

  const getHighlightedText = (text: string, highlight: string) => {
    if (!highlight.trim()) {
      return text;
    }

    const parts = text.split(new RegExp(`(${highlight})`, 'gi')); // Case-insensitive search
    return parts.map((part, index) =>
      part.toLowerCase() === highlight.toLowerCase() ? (
        <span key={index} className='text-primary-500'>
          {part}
        </span>
      ) : (
        part
      ),
    );
  };
  return (
    <button onClick={toggled} className={twMerge('w-full border bg-white rounded-lg  ', className)}>
      <div className='p-4 flex flex-col gap-3'>
        <div className='w-full text-left flex justify-between'>
          <div className='flex flex-col'>
            <Badge
              color='neutral'
              shape='square-round'
              label={
                tFaq(`faqsDropdown.${tag.toLowerCase()}` as any).includes('.')
                  ? tag
                  : tFaq(`faqsDropdown.${tag.toLowerCase()}` as any)
              }
            />
          </div>
          <span className='text-slate-800 text-2xl'>{isOpen ? <MinusIcon /> : <PlusIcon />}</span>
        </div>
        <div className='flex flex-col gap-2 '>
          <div className='flex  gap-1.5 '>
            <span className='text-slate-300 font-medium w-5 text-base md:text-lg flex-shrink-0'>Q</span>
            <span className={`font-medium text-slate-800 text-base text-left md:text-lg ${!isOpen ? 'line-clamp-1' : ''}`}>
              {getHighlightedText(question, searchedValue)}
            </span>
          </div>

          <div
            className={twMerge(
              'transition-all duration-300 w-full flex gap-1.5',
              isOpen ? 'h-[72px]' : 'h-0',
              isOverflowEnabled ? 'overflow-y-auto' : 'overflow-hidden',
            )}>
            <span className='text-slate-300 font-normal w-5 text-sm md:text-base flex-shrink-0 ml-0.25'>A</span>
            <span className='text-slate-500 text-sm md:text-base text-left '>{getHighlightedText(answer, searchedValue)}</span>
          </div>
        </div>
      </div>
    </button>
  );
};
