import Background from '@/assets/images/background-image-1.svg';
import { getNumberExperts } from '@/data/home/home-api';
import { ExpertIndustryRESP } from '@/data/home/response/expert-industry.response';
import { useQuery } from '@tanstack/react-query';

import { useTranslation } from 'react-i18next';

const webLanguages = {
  en: 'en',
  ko: 'ko',
};

const Advantage = () => {
  const { t: tCommon } = useTranslation('common');
  const { i18n } = useTranslation();

  const { data: expertIndustry } = useQuery({
    queryKey: ['getNumberExperts'],
    queryFn: () => getNumberExperts(),
  });

  return (
    <div className='relative w-full h-fit'>
      <img src={Background} className='object-contain w-96 absolute -left-2' />
      <div className='py-24 md:px-28 px-0'>
        <p className='text-primary-600 text-sm font-medium text-center'>{tCommon('home.Dr_wiz_expert')}</p>
        <p className='text-slate-800 md:text-4xl text-3xl font-bold mt-4 text-center mb-16'>
          {tCommon('home.Dr_wiz_expert_title')}
        </p>
        <div className='flex items-center md:flex-wrap overflow-x-auto scrollbar-none gap-3 xl:w-3/4 w-full flex-row md:justify-center mx-auto snap-x px-3'>
          {expertIndustry?.data?.map((item: ExpertIndustryRESP, index: number) => (
            <div key={index} className='snap-center relative rounded-2xl shadow-2xl md:w-1/4 min-w-60'>
              <img src={item.url} className='object-cover w-full h-[300px] rounded-2xl overflow-hidden' />
              <div className='absolute top-0 justify-center items-center flex flex-col w-full h-[300px]'>
                <p className='text-4xl font-bold text-white'>{item.count}</p>
                <p className='text-lg text-white font-normal whitespace-pre-line'>
                  {i18n.language === webLanguages.en ? item.descriptionInEnglish : item.descriptionInKorean}
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Advantage;
