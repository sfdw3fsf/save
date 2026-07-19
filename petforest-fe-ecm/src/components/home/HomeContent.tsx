/* eslint-disable jsx-a11y/anchor-is-valid */
/* eslint-disable @next/next/no-img-element */
'use client';
import { useGlobalStore } from '@/data/global/global.store';

import { useEffect } from 'react';
import HomeArticle from './HomeArticle';
import HomeAutoSlider from './HomeAutoSlider';
import HomeBanner from './HomeBanner';
import HomeBranch from './HomeBranch';
import HomeIntro from './HomeIntro';
import HomeJournal from './HomeJournal';

const HomeContent = () => {
  const { isIntro, setIsIntro } = useGlobalStore();

  useEffect(() => {
    setIsIntro(true);

    return () => {
      setIsIntro(false);
    };
  }, []);

  return (
    <>
      {isIntro && <HomeIntro />}
      {!isIntro && (
        <>
          <div className='min-h-[70vh] box-border'>
            <div className='relative overflow-auto'>
              <div className='lmt maxw pt-[150px] mx-auto'>
                <img className='home-banner-pc' src='/images/home/home-banner.jpg' alt='' />
                <img className='home-banner-mobile' src='/images/home/home-banner-mobile.jpg' alt='' />

                <div className='flex justify-between gap-[9px] home-banner-container !mt-[22px] md:!mt-[27px]'>
                  <p className='flex-1 home-banner-title !mt-0'>함께한 순간을 영원히 기억합니다</p>
                  <div className='flex-1'>
                    <p className='home-banner-content !mt-0'>
                      존중의 마음으로 반려동물의 마지막을 배웅하고, 정성과 위로의 손길로 보호자의 슬픔을 보듬겠습니다.
                    </p>
                    <a href='/about/brand-story' className='btn-home-feature'>
                      펫포레스트 소개
                    </a>
                  </div>
                </div>
              </div>
            </div>
            <HomeBanner />
            <HomeBranch />
            <HomeArticle />
            <HomeJournal />
          </div>
          <HomeAutoSlider />
        </>
      )}
    </>
  );
};

export default HomeContent;
