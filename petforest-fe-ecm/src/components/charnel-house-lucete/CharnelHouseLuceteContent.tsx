'use client';

import { LUCETE_FEATURES } from '@/utils/constant/charnel-house-lucete/charnel-house-lucete-feature.constant';
import { LUCETE_PROCESSES } from '@/utils/constant/charnel-house-lucete/charnel-house-lucete-process.constant';
import Slider, { Settings } from 'react-slick';

const CharnelHouseLuceteContent = () => {
  const settings: Settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2500,
    appendDots: (dots) => (
      <div style={{ position: 'absolute', top: '0', height: 'fit-content', marginTop: '62%' }}>
        <ul>{dots}</ul>
      </div>
    ),
  };

  return (
    <div className='sec !mb-0'>
      <div className='sec-inner lmt maxw !pb-[70px] !pt-[60px] lg:!pt-[135px] lg:!pb-[150px]'>
        <div className='sec-title !mb-[46px] lg:!mb-[79px]'>
          <h2>루세떼</h2>
          <p>
            루세떼는 {"'밝게 빛나라'"}라는 의미를 가진 추모 보석입니다. 조금 더 가까운 곳에서 반려동물을 기억할 수 있도록
            펫포레스트가 함께하겠습니다.
          </p>
        </div>
        <div>
          <Slider {...settings}>
            {[...Array(5)].map((_, index) => (
              <div key={index}>
                <img src={`/images/charnel-house/lucete/lucete-top-${index + 1}.jpg`} alt='' />
              </div>
            ))}
          </Slider>
        </div>
      </div>
      <div className='sec-inner lmt maxw !py-[70px] lg:!py-[150px]'>
        <div className='sec-title'>
          <h2>루세떼 특징</h2>
        </div>
        <div className='lucete-feature-container'>
          {LUCETE_FEATURES.map((feature, index) => (
            <div key={index} className='lucete-feature-item'>
              <img src={feature.img} alt='' />
              <p>{feature.title}</p>
            </div>
          ))}
        </div>
      </div>
      <div className='sec-inner lmt maxw !py-[70px] lg:!py-[150px]'>
        <div className='sec-title lg:!mb-[150px]'>
          <h2>루세떼 제작 공정</h2>
        </div>
        <div className='lucete-process-container'>
          {LUCETE_PROCESSES.map((process, index) => (
            <div key={index} className='lucete-process-item'>
              <img src={process.img} alt='' />
              <p>
                {`0${index + 1}`}.
                <br />
                {process.title}
              </p>
            </div>
          ))}
        </div>
        <div className='lucete-process-slider'>
          <Slider {...settings}>
            {LUCETE_PROCESSES.map((feature, index) => (
              <div key={index} className='h-full !select-none !outline-none'>
                <img src={feature.img} alt='' className='block w-full h-auto max-w-full' />
                <h3 className='w-[30%] text-[24px] font-bold leading-[1.5] slider-title'>
                  {`0${index + 1}`}.
                  <br />
                  {feature.title}
                </h3>
              </div>
            ))}
          </Slider>
        </div>
      </div>
      <div className='sec-inner lmt maxw !border-b-0 !py-[70px] lg:!py-[150px]'>
        <div className='sec-title'>
          <h2>루세떼 주얼리</h2>
          <p>
            루세떼 제작 후 원하는 형태의 주얼리로 세공하여, 언제 어디서든 함께할 수 있습니다. 주얼리 제작을 원하실 경우 별도
            문의해주세요.
          </p>
        </div>
        <div className='lucete-jewelry-container'>
          {[...Array(6)].map((_, index) => (
            <div key={index} className='lucete-jewelry-item'>
              <img src={`/images/charnel-house/lucete/lucete-jewelry-${index + 1}.jpg`} alt='' />
            </div>
          ))}
        </div>
        <div className='lucete-jewelry-slider'>
          <Slider {...settings}>
            {[...Array(6)].map((feature, index) => (
              <div key={index} className='h-full !select-none !outline-none'>
                <img src={`/images/charnel-house/lucete/lucete-jewelry-${index + 1}.jpg`} alt='' />
              </div>
            ))}
          </Slider>
        </div>
      </div>
    </div>
  );
};

export default CharnelHouseLuceteContent;
