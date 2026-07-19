'use client';
import { BRANCHES } from '@/utils/constant/branch.constant';
import {
  CHARNEL_HOUSE_BRANCHES_ONE,
  CHARNEL_HOUSE_BRANCHES_TWO,
} from '@/utils/constant/charnel-house/charnel-house-branch.constant';
import { useEffect, useRef, useState } from 'react';
import Slider, { Settings } from 'react-slick';

type CharnelHouseContentProps = {
  activeBranch: string;
  setActiveBranch: (activeBranch: string) => void;
};

const CharnelHouseContent = ({ activeBranch, setActiveBranch }: CharnelHouseContentProps) => {
  const [activeSlider, setActiveSlider] = useState(CHARNEL_HOUSE_BRANCHES_ONE);
  const sliderRef = useRef<Slider | null>(null);

  const selectBranch = (branch: string) => {
    setActiveBranch(branch);
  };

  const settings: Settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    appendDots: (dots) => (
      <div style={{ position: 'absolute', top: '0', height: 'fit-content', marginTop: '62%' }}>
        <ul>{dots}</ul>
      </div>
    ),
  };

  useEffect(() => {
    if (activeBranch === BRANCHES[0]) {
      setActiveSlider(CHARNEL_HOUSE_BRANCHES_ONE);
      return;
    }
    setActiveSlider(CHARNEL_HOUSE_BRANCHES_TWO);
    sliderRef.current?.slickGoTo(0);
  }, [activeBranch]);

  return (
    <div className='sec !mb-0'>
      <div className='sec-inner lmt maxw !border-b-0 lg:!pb-[196px]'>
        <div className='sec-title !mb-[35px] lg:!mb-[40px]'>
          <h2>봉안당</h2>
          <p className='!mt-[15px] lg:!mt-[20px]'>
            펫포레스트에서 볕이 가장 잘 드는 곳에 반려동물을 위한 추모의 공간을 마련했습니다. 아이의 마지막 보금자리를 늘 한결같은
            마음으로 지키겠습니다.
          </p>
        </div>
        <div className='tab-container lg:!gap-[15px]'>
          {BRANCHES.map((branch, index) => (
            <div
              key={index}
              onClick={() => selectBranch(branch)}
              className={`tab-title ${activeBranch === branch ? 'active' : ''}`}>
              {branch}
            </div>
          ))}
        </div>
        <div className='mt-[40px] lg:mt-[50px]'>
          <Slider ref={sliderRef} {...settings}>
            {activeSlider.map((branch, index) => (
              <div key={index}>
                <div>
                  <img src={branch.img} alt='' />
                  <p className='mt-6 text-center text-[13px] text-[#555] leading-[1.5]'>{branch.title}</p>
                </div>
              </div>
            ))}
          </Slider>
        </div>
      </div>
    </div>
  );
};

export default CharnelHouseContent;
