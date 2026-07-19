import EmptyImage from '@/assets/images/empty_search.png';
import { useTranslation } from 'react-i18next';
const Empty = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  return (
    <div className='w-full flex flex-col justify-center items-center py-4'>
      <img src={EmptyImage} alt='Empty Search' className='h-[100px]' />
      <p className='text-sm text-slate-500 text-center mt-2'>{tCommon('component.empty')}</p>
    </div>
  );
};

export default Empty;
