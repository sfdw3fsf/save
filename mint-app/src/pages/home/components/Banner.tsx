import { getHomeOverview } from '@/data/home/home-api';
import i18n from '@/i18n';
import { useQuery } from '@tanstack/react-query';
import { useMediaQuery } from '@uidotdev/usehooks';
import { AnimatedCounter } from 'react-animated-counter';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
const Banner = () => {
  const { t: tCommon } = useTranslation('common');
  const isSmallDevice = useMediaQuery('max-width : 425px');

  const { data: homeOverView } = useQuery({
    queryKey: ['getHomeOverview'],
    queryFn: () => getHomeOverview(),
  });

  const isEnglish = i18n.language === 'en';

  return homeOverView?.data.active ? (
    <div
      className={twMerge(
        'flex lg:flex-row flex-col sm:gap-8 gap-4 xl:w-4/5 w-full justify-center lg:py-10 py-5 px-3 lg:px-28 mt-16 md:mt-0',
      )}>
      <div className='flex flex-col justify-center items-center gap-4 border p-6 w-full rounded-2xl'>
        <div className='flex md:gap-4 gap-2 items-center font-bold text-primary-600'>
          <div className={twMerge('flex', !isSmallDevice ? 'text-4xl' : 'text-2xl')}>
            <AnimatedCounter
              value={homeOverView?.data.totalExpert}
              decimalPrecision={0}
              fontSize={!isSmallDevice ? '36px' : '24px'}
              color='#277A64'
            />
            +
          </div>
        </div>
        <p className='sm:text-lg text-md text-slate-500 font-semibold'>
          {isEnglish ? homeOverView?.data.totalExpertTitleInEnglish : homeOverView?.data.totalExpertTitleInKorean}
        </p>
      </div>

      <div className='flex flex-col justify-center items-center gap-4 border p-6 w-full rounded-2xl'>
        <div className={twMerge('flex items-center font-bold text-primary-600', !isSmallDevice ? 'text-4xl' : 'text-2xl')}>
          <AnimatedCounter
            value={homeOverView?.data.totalSuccessCases}
            decimalPrecision={0}
            fontSize={!isSmallDevice ? '36px' : '24px'}
            color='#277A64'
          />
          <span className={`${i18n.language === 'en' ? 'pl-1' : 'pl-0'}`}>{tCommon('home.cases')} +</span>
        </div>
        <p className='sm:text-lg text-md text-slate-500 font-semibold'>
          {isEnglish ? homeOverView?.data.totalSuccessCaseTitleInEnglish : homeOverView?.data.totalSuccessCaseTitleInKorean}
        </p>
      </div>

      <div className='flex flex-col justify-center items-center gap-4 border p-6 w-full rounded-2xl'>
        <div className={twMerge('flex items-center font-bold text-primary-600', !isSmallDevice ? 'text-4xl' : 'text-2xl')}>
          <AnimatedCounter
            value={homeOverView?.data.totalSuccessStory}
            decimalPrecision={0}
            fontSize={!isSmallDevice ? '36px' : '24px'}
            color='#277A64'
          />
          +
        </div>
        <p className='sm:text-lg text-md text-slate-500 font-semibold'>
          {isEnglish ? homeOverView?.data.totalSuccessStoryTitleInEnglish : homeOverView?.data.totalSuccessStoryTitleInKorean}
        </p>
      </div>
    </div>
  ) : null;
};

export default Banner;
