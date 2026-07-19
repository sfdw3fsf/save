import Button from '@/components/core/button/Button';
import { getHomeBanners } from '@/data/home/home-api';
import i18n from '@/i18n';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { useQuery } from '@tanstack/react-query';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Carousel } from 'react-responsive-carousel';
import { useNavigate } from 'react-router-dom';

const CarouselBanner = () => {
  const { t: tCommon } = useTranslation('common');
  const navigate = useNavigate();

  const { data: homeBanner } = useQuery({
    queryKey: ['homeBanner'],
    queryFn: () =>
      getHomeBanners({
        size: 100,
      }),
  });

  const { userInfo } = useAuthStore();

  const linkProjectRequest = useMemo(() => {
    switch (userInfo?.userRole) {
      case USER_ROLE.MEMBER:
        return ROUTES.PROJECT_REQUEST.INDIVIDUAL.REQUEST;
      default:
        return ROUTES.PROJECT_MANAGEMENT.EXPERT;
    }
  }, [userInfo?.userRole]);

  const [activeIndex, setActiveIndex] = useState(0);

  return (
    <div className='w-full relative md:h-96 h-[420px] '>
      <Carousel
        autoPlay
        infiniteLoop
        showStatus={false}
        showIndicators
        showArrows
        showThumbs={false}
        onChange={(index) => setActiveIndex(index)}>
        {homeBanner?.data.map((banner) => <img key={banner.id} src={banner.url} className='object-cover md:h-96 h-[420px]' />)}
      </Carousel>
      <div className='absolute w-dvw mx-auto flex-col gap-[30px] md:h-96 h-[420px] md:p-10 py-6 px-4 flex justify-center items-center top-0'>
        {/* Display text for the active banner only */}
        {homeBanner?.data && homeBanner?.data.length > 0 && (
          <>
            {i18n.language === 'en' ? (
              <div className='gap-[18px]'>
                <p className='md:text-5xl text-3xl text-white text-center whitespace-pre-line font-bold'>
                  {homeBanner.data[activeIndex].titleInEnglish}
                </p>
                <p className='md:text-2xl text-sm font-normal leading-6 text-center whitespace-pre-line text-slate-50'>
                  {homeBanner.data[activeIndex].descriptionInEnglish}
                </p>
              </div>
            ) : (
              <>
                <p className='md:text-5xl text-3xl text-white text-center whitespace-pre-line font-bold'>
                  {homeBanner.data[activeIndex].titleInKorean}
                </p>
                <p className='md:text-2xl text-sm text-center font-normal leading-6 text-slate-50 whitespace-pre-line'>
                  {homeBanner.data[activeIndex].descriptionInKorean}
                </p>
              </>
            )}
          </>
        )}
        <Button
          onClick={() => navigate(linkProjectRequest)}
          className='mx-auto py-[10px] min-w-40'
          sizing='xl'
          labelStyle='text-primary-1000 font-semibold text-md'>
          {tCommon('home.banner_button')}
        </Button>
      </div>
    </div>
  );
};

export default CarouselBanner;
