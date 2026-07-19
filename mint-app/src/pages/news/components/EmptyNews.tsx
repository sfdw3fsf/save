import EmptyImage from '@/assets/images/empty_list.png';
import { useTranslation } from 'react-i18next';
export const EmptyNews = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  return (
    <div className='w-full flex flex-col gap-6 justify-center items-center my-12'>
      <span className='size-28 shrink-0'>
        <img src={EmptyImage} alt='emptyFaq' />
      </span>
      <p className='text-sm text-slate-500 text-center'>{tCommon('news.empty')}</p>
    </div>
  );
};
