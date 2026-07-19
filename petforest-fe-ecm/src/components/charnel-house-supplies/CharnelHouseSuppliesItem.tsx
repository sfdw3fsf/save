'use client';

import { SupplyType } from '@/utils/constant/charnel-house-supplies/charnel-house-supplies.constant';
import Slider, { Settings } from 'react-slick';

type CharnelHouseSuppliesItemProps = {
  supply: SupplyType;
};

const CharnelHouseSuppliesItem = ({ supply }: CharnelHouseSuppliesItemProps) => {
  const settings: Settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    // appendDots: (dots) => (
    //   <div style={{ position: 'absolute', height: 'fit-content', marginTop: '-62%' }}>
    //     <ul>{dots}</ul>
    //   </div>
    // ),
  };

  return (
    <div className='supply-container'>
      <div className='supply-content'>
        <div className='mb:[4px] md:mb-10'>
          <h3 className='mb-4 text-[30px] font-bold leading-[1.5] supply-name'>{supply.name}</h3>
          <p className='text-[18px] text-[#343a40] supply-desc'>{supply.desc}</p>
          <p className='mt-1 text-[14px] font-medium text-[#999999] supply-note'>{supply.note}</p>
        </div>
        <div className='supply-img-slider slider-below'>
          {supply.images.length <= 1 && (
            <div>
              <img src={supply.images[0].link} alt='' />
              <p className='supply-img-title'>{supply.images[0].title}</p>
            </div>
          )}
          {supply.images.length > 1 && (
            <Slider {...settings}>
              {supply.images.map((img, index) => (
                <div key={index} className='!select-none !outline-none'>
                  <img src={img.link} alt={img.title} />
                  <p className='supply-img-title hidden md:block'>{img.title}</p>

                  <div className='block md:hidden'>
                    <p className='text-[#999] text-[12px] mt-[15px] md:mt-0'>* VAT 포함</p>
                    <div className='supply-cost-container'>
                      <div className='supply-cost-item'>
                        {img.cost ? (
                          <>
                            <h4 className='min-h-[46px]'>{img.cost.title}</h4>
                            <p>{img.cost.price}</p>
                          </>
                        ) : (
                          <>
                            <h4 className='min-h-[46px]'>
                              {supply.costs[index]?.title}{' '}
                              {supply.costs[index]?.isBuy && (
                                <a
                                  href={supply.costs[index].link}
                                  className='btn-supply-buy mr-[2px]'
                                  target='_blank'
                                  rel='noopener noreferrer'>
                                  구매하기 {'>'}
                                </a>
                              )}
                            </h4>
                            <p>{supply.costs[index]?.price}</p>
                          </>
                        )}
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </Slider>
          )}
        </div>
        <div className={` ${supply.costs.length > 1 && 'hidden md:block'}`}>
          <p className='text-[#999] text-[12px]'>* VAT 포함</p>
          <div className='supply-cost-container'>
            {supply.costs.map((cost, index) => (
              <div key={index} className='supply-cost-item'>
                <h4>
                  {cost.title}
                  {cost.isBuy && (
                    <a href={supply.costs[index].link} className='btn-supply-buy' target='_blank' rel='noopener noreferrer'>
                      구매하기 {'>'}
                    </a>
                  )}
                </h4>
                <p>{cost.price}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
      <div className='supply-img-slider slider-right'>
        {supply.images.length <= 1 && (
          <div>
            <img src={supply.images[0].link} alt='' />
            <p className='supply-img-title '>{supply.images[0].title}</p>
          </div>
        )}
        {supply.images.length > 1 && (
          <Slider {...settings}>
            {supply.images.map((img, index) => (
              <div key={index} className='!select-none !outline-none'>
                <img src={img.link} alt='' />
                <p className='supply-img-title'>{img.title}</p>
              </div>
            ))}
          </Slider>
        )}
      </div>
    </div>
  );
};

export default CharnelHouseSuppliesItem;
