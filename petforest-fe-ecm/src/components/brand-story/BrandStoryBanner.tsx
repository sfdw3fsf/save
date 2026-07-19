'use client';
import PageTitleBanner from '@/components/common/page-title-banner/PageTitleBanner';
import '@/components/home/home.css';
const BrandStoryBanner = () => {
  return (
    <PageTitleBanner title='기억과 교감의 숲, 펫포레스트' img='/images/about/brand-story/brand-story-banner.jpg'>
      <div className='mouse-wheel'>
        <span className='wheel-one'></span>
        <span className='wheel-two'></span>
        <span className='wheel-three'></span>
        <p className='text-[14px] text-[#f8f9fa] mt-4 tracking-wider leading-[23px]'>SCROLL</p>
      </div>
    </PageTitleBanner>
  );
};

export default BrandStoryBanner;
