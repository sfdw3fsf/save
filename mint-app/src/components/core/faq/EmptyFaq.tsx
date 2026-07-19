import EmptyImage from '@/assets/images/empty_list.png';
import { useTranslation } from 'react-i18next';
export const EmptyFaq = (): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  return (
    <div className='w-full flex flex-col gap-6 justify-center items-center my-12'>
      <span className='size-28 shrink-0'>
        <img src={EmptyImage} alt='emptyFaq' />
      </span>
      <p className='text-sm text-slate-500 text-center'>{tFaq('emptyList')}</p>
    </div>
  );
};
