import EmptyImage from '@/assets/images/empty_search.png';
import { useTranslation } from 'react-i18next';
export const EmptySearch = (): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  return (
    <div className='w-full  flex flex-col gap-6 justify-center items-center my-16'>
      <span className='size-28 shrink-0'>
        <img src={EmptyImage} alt='Empty Search' />
      </span>
      <p className='text-sm text-slate-500 text-center mt-2'>{tFaq('emptySearch')}</p>
    </div>
  );
};
