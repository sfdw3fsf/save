'use client';
import useResize from '@/hook/useResize';
import { BRANCHES } from '@/utils/constant/branch.constant';
import {
  FUNERAL_TRANSPORTATION_CATEGORIES,
  TRANSPORTATION_ACCOMPANIED,
  TRANSPORTATION_COST_1,
  TRANSPORTATION_COST_2,
  TRANSPORTATION_UNACCOMPANIED,
} from '@/utils/constant/funeral-transportation/funeral-transportation-service.constant';
import { useSearchParams } from 'next/navigation';
import { useState } from 'react';
import Slider, { Settings } from 'react-slick';

const FuneralTransportationContent = () => {
  const searchParams = useSearchParams();
  const activeServiceParam = searchParams.get('activeService')?.toString();
  const [activeCategory, setActiveCategory] = useState(
    activeServiceParam === 'unaccompanied' ? FUNERAL_TRANSPORTATION_CATEGORIES[1] : FUNERAL_TRANSPORTATION_CATEGORIES[0],
  );
  const [activeBranch, setActiveBranch] = useState(BRANCHES[0]);
  const { isMobile, isTablet } = useResize();

  const selectCategory = (category: string) => {
    setActiveCategory(category);
  };

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

  return (
    <>
      <div className='cont-head' style={{ backgroundImage: `url('/images/funeral/transportation/transportation-banner.png')` }}>
        <article>
          <p className='title'>{'차량 서비스'}</p>
          <p className='sub-title'>
            장례식장에 들어서는 모든 발걸음을 기억합니다. <br className='view_mo' />
            장례식장의 방문이 힘든 보호자를 위해, <br className='view_pc' />
            슬픔이 머문 <br className='view_mo' />
            자리마다 펫포레스트가 먼저 달려가겠습니다.
          </p>
        </article>
      </div>
      <div className='mt-[90px] transportation'>
        <div className='container flex items-center justify-center gap-[44px] category-tabs-container'>
          {FUNERAL_TRANSPORTATION_CATEGORIES.map((catgeory, index) => (
            <div
              key={index}
              onClick={() => selectCategory(catgeory)}
              className={`transportation-category-tab ${activeCategory === catgeory ? 'active' : ''}`}>
              {catgeory}
            </div>
          ))}
        </div>
        <div className='container mt-[60px] category-tabs-content'>
          {activeCategory === FUNERAL_TRANSPORTATION_CATEGORIES[0] && (
            <div>
              <div className='text-center'>
                <p className='main-txt'>{TRANSPORTATION_ACCOMPANIED.content}</p>
                <p className='sub-txt'>
                  · 운구서비스는 출발하시는 곳에서 펫포레스트까지 오시는{' '}
                  <span className='text-green-main font-medium border-b border-green-main'>{"'편도'"}</span>만 운영하며, 장례가
                  끝난 후에는 일반 택시를 불러드립니다.
                </p>
              </div>
              <div className='pt-[56px] pb-[138px] content-main-img'>
                <img src={TRANSPORTATION_ACCOMPANIED.img} alt='' />
              </div>
            </div>
          )}
          {activeCategory === FUNERAL_TRANSPORTATION_CATEGORIES[1] && (
            <div className='mb-[20px] md:mb-0'>
              <div className='text-center mb-[35px] md:mb-0'>
                <p className='main-txt !whitespace-normal sm:!whitespace-break-spaces'>{TRANSPORTATION_UNACCOMPANIED.content}</p>
              </div>

              {isMobile || isTablet ? (
                <Slider {...settings}>
                  {TRANSPORTATION_UNACCOMPANIED.process.map((process, index) => (
                    <div key={index} className='h-full !select-none !outline-none'>
                      <img src={process.img} alt='' className='block w-full h-auto max-w-full' />
                      <div className='flex mt-6 slider-content'>
                        <h3 className='text-[24px] font-bold leading-[1.5]'>
                          {' '}
                          <span className='text-[24px] text-green-main mr-[10px]'>{`0${index + 1}`}</span>
                          {process.step}
                        </h3>
                        <div className='slider-detail'>
                          <p className='text-[20px] leading-[1.68]'>{process.content}</p>
                          {process.note && <p className='mt-[10px] text-[14px] text-[#757676] opacity-50'>{process.note}</p>}
                          {/* <ul className='mt-4'>
                            {process.contents.map((content, key) => (
                              <li key={key} className='li-dot'>
                                {content}
                              </li>
                            ))}
                          </ul> */}
                        </div>
                      </div>
                    </div>
                  ))}
                </Slider>
              ) : (
                <div className='flex flex-wrap mt-[90px] mb-[124px] gap-y-[47px] gap-x-[24px]'>
                  {TRANSPORTATION_UNACCOMPANIED.process.map((process, index) => (
                    <div key={index} className='transportation-unaccompanied-step'>
                      <div className='step-img'>
                        <img src={process.img} alt='' className='object-cover w-full h-auto max-w-full' />
                      </div>
                      <div className='mt-[22px]'>
                        <p className='text-[23px] font-bold text-[#1a1a1a]'>
                          <span className='text-[24px] text-green-main mr-[10px]'>{`0${index + 1}`}</span>
                          {process.step}
                        </p>
                        <p className='mt-3 leading-[1.44] text-[#343434]'>{process.content}</p>
                        {process.note && <p className='mt-[10px] text-[14px] text-[#757676] opacity-50'>{process.note}</p>}
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}
        </div>
        <div className='pt-[137px] pb-[130px] bg-[#fafafb] transportation-detail'>
          <div className='container text-center'>
            <p className='text-[30px] font-extrabold text-green-main transportation-detail-title'>운구 비용 안내</p>
            <p className='mt-[15px] text-[15px] text-[#4c4c4c] transportation-detail-info'>
              운구 서비스는 <span className='text-[#272727] font-bold border-b border-[#272727]'>편도 비용</span> 입니다.
            </p>
            <p className='mt-[15px] text-[15px] text-[#4c4c4c] transportation-detail-vat'>모든 비용은 VAT 포함 입니다.</p>
          </div>
          <div className='transportation-cost-container'>
            <div className='tab-container'>
              {BRANCHES.map((branch, index) => (
                <div
                  key={index}
                  onClick={() => selectBranch(branch)}
                  className={`tab-title ${activeBranch === branch ? 'active' : ''}`}>
                  {branch}
                </div>
              ))}
            </div>
            <div className='mt-[20px] md:mt-[50px]'>
              {activeBranch === BRANCHES[0] && (
                <div className='flex gap-[48px] transportation-cost-tables'>
                  {TRANSPORTATION_COST_1.map((cost, index) => (
                    <div key={index} className='transportation-cost-item'>
                      <p className='cost-title'>{cost.title}</p>
                      {cost.costs.map((item, key) => (
                        <div key={key} className='cost-content-container'>
                          <p className='cost-content'>{item.content}</p>
                          <p className='cost-price'>{item.price}원</p>
                        </div>
                      ))}
                    </div>
                  ))}
                </div>
              )}
              {activeBranch === BRANCHES[1] && (
                <div className='flex gap-[48px] transportation-cost-tables'>
                  {TRANSPORTATION_COST_2.map((cost, index) => (
                    <div key={index} className='transportation-cost-item'>
                      <p className='cost-title'>{cost.title}</p>
                      {cost.costs.map((item, key) => (
                        <div key={key} className='cost-content-container'>
                          <p className='cost-content'>{item.content}</p>
                          <p className='cost-price'>{item.price}원</p>
                        </div>
                      ))}
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default FuneralTransportationContent;
