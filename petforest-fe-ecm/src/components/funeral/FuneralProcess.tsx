'use client';
import { FUNERAL_PROCESSES } from '@/utils/constant/funeral/funeral-process.constant';
import Slider, { Settings } from 'react-slick';

const FuneralProcess = () => {
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
    <div className='sec !mb-0' id='process'>
      <div className='sec-inner lmt maxw'>
        <div className='sec-title sec-title-funeral'>
          <h2>펫포레스트 장례 절차</h2>
          <p>
            장례 예식의 모든 과정을 격식있고 진중한 태도로 진행합니다. 반려동물에게는 따뜻한 손길로, 보호자에게는 진실된 위로를
            전달합니다.
          </p>
        </div>
        <div>
          <Slider {...settings}>
            {FUNERAL_PROCESSES.map((process, index) => (
              <div key={index} className=' !select-none !outline-none'>
                <div>
                  <img src={process.img} alt='' />
                  <div className='flex flex-col mt-6 slider-content flex-col md:flex-row'>
                    <div className='md:w-1/3'>
                      <h3 className='text-[24px] font-bold leading-[1.5]'>{process.step}</h3>
                    </div>
                    <div>
                      <p className='text-[15px] leading-[1.68] md:text-[20px]'>{process.title}</p>
                      <div className='slider-detail'>
                        <ul className='mt-4'>
                          {process.contents.map((content, key) => (
                            <li key={key} className='li-dot'>
                              {content}
                            </li>
                          ))}
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </Slider>
        </div>
      </div>
    </div>
  );
};

export default FuneralProcess;
