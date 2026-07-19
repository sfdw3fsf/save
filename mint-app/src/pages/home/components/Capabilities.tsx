import { getHomeCapabilities } from '@/data/home/home-api';
import { HomeCapabilitiesRESP } from '@/data/home/response/home-capabilities.response';
import { useQuery } from '@tanstack/react-query';
import i18next from 'i18next';
import { useTranslation } from 'react-i18next';
const Capabilities = () => {
  const { t: tCommon } = useTranslation('common');

  const { data: capabilities } = useQuery({
    queryKey: ['capabilities'],
    queryFn: () =>
      getHomeCapabilities({
        size: 100,
      }),
  });

  const bigImageFirst = (capabilities: HomeCapabilitiesRESP[]) => {
    return (
      <>
        <div className='relative col-span-2 row-span-2 min-w-72 md:min-w-fit '>
          <img
            src={capabilities[0].url}
            alt={capabilities[0].titleInKorean}
            className='md:w-[560px] w-full md:h-[500px] h-[320px] object-cover rounded-lg'
          />
          <p className='absolute bottom-4 left-4 text-white lg:text-4xl text-xl font-bold whitespace-pre-line'>
            {i18next.language === 'en' ? capabilities[0].titleInEnglish : capabilities[0].titleInKorean}
          </p>{' '}
        </div>

        {capabilities?.map((item, index) =>
          index === 0 ? null : (
            <div key={index} className='relative col-span-1 row-span-1 min-w-72 md:min-w-fit '>
              <img
                src={item.url}
                alt={item.titleInKorean}
                className='md:w-[560px] w-full md:h-[242px] h-[320px]  object-cover rounded-lg'
              />
              <p className='absolute bottom-4 left-4 text-white lg:text-2xl text-xl font-bold whitespace-pre-line'>
                {i18next.language === 'en' ? item.titleInEnglish : item.titleInKorean}
              </p>{' '}
            </div>
          ),
        )}
      </>
    );
  };

  const bigImageLast = (capabilities: HomeCapabilitiesRESP[]) => {
    return (
      <>
        {capabilities?.map((item, index) =>
          index === capabilities.length - 1 ? null : (
            <div key={item.id} className='relative min-w-72 md:min-w-fit '>
              <img src={item.url} alt={item.titleInKorean} className='w-full md:h-[242px] h-[320px] object-cover rounded-lg' />
              <p className='absolute bottom-4 left-4 text-white  lg:text-2xl text-xl font-bold whitespace-pre-line'>
                {i18next.language === 'en' ? item.titleInEnglish : item.titleInKorean}
              </p>{' '}
            </div>
          ),
        )}

        <div className='relative col-span-2 row-span-2 col-start-3 row-start-3 min-w-72 md:min-w-fit '>
          <img
            src={capabilities[capabilities.length - 1].url}
            alt={capabilities[capabilities.length - 1].titleInKorean}
            className='md:w-[560px] w-full md:h-[500px] h-[320px] object-cover rounded-lg'
          />
          <p className='absolute bottom-4 left-4 text-white lg:text-4xl text-xl font-bold whitespace-pre-line'>
            {i18next.language === 'en'
              ? capabilities[capabilities.length - 1].titleInEnglish
              : capabilities[capabilities.length - 1].titleInKorean}
          </p>{' '}
        </div>
      </>
    );
  };

  return (
    <div className='lg:py-24 lg:px-28 xl:w-4/5 w-full p-3'>
      <h2 className='text-primary-600 text-sm text-center font-medium'>{tCommon('home.capabilities')}</h2>
      <p className='text-slate-800 md:text-4xl text-3xl text-center font-bold  md:px-0 px-5 mx-auto mb-20 mt-4 whitespace-pre-line break-keep'>
        {tCommon('home.capability_title')}
      </p>
      <div className='md:grid md:grid-cols-4 md:grid-flow-row overflow-x-auto scrollbar w-full mx-auto flex flex-row gap-4 md:max-w-[1204px] px-8'>
        {Array.isArray(capabilities?.data) && bigImageFirst(capabilities?.data.slice(0, Math.min(5, capabilities?.data.length)))}
        {Array.isArray(capabilities?.data) && bigImageLast(capabilities?.data.slice(-Math.min(5, capabilities?.data.length)))}
      </div>
    </div>
  );
};

export default Capabilities;
