'use client';
import { FUNERAL_SPECIAL_SERVICES, FuneralSpecialServiceType } from '@/utils/constant/funeral/funeral-special-service.constant';
import { serviceMap } from '@/utils/constant/funeral/mapping-service.constant';
import { useSearchParams } from 'next/navigation';
import { useEffect, useState } from 'react';

const FuneralSpecialService = () => {
  const searchParams = useSearchParams();
  const [activeService, setActiveService] = useState(FUNERAL_SPECIAL_SERVICES[0]);

  const selectCategory = (service: FuneralSpecialServiceType) => {
    setActiveService(service);
  };

  useEffect(() => {
    const activeServiceParam = searchParams.get('activeService')?.toString();

    if (activeServiceParam && serviceMap[activeServiceParam] !== undefined) {
      setActiveService(FUNERAL_SPECIAL_SERVICES[serviceMap[activeServiceParam]]);

      const element = document.getElementById('special');
      if (element) {
        element.scrollIntoView({ behavior: 'instant', block: 'start' });
      }
    }
  }, [searchParams]);

  return (
    <div className='sec !mb-0' id='special'>
      <div className='sec-inner lmt maxw'>
        <div className='sec-title'>
          <h2>특별한 서비스</h2>
          <p>
            보호자 편의를 위해 펫포레스트만의 특별한 서비스를 준비했습니다. 늘 보호자의 입장에서 생각하는 펫포레스트가 되겠습니다.
          </p>
        </div>
        <div className='mt-[50px] mb-[20px]'>
          <div className='flex justify-center mb-[52px] gap-1 funeral-service-category-list'>
            {FUNERAL_SPECIAL_SERVICES.map((service, index) => (
              <div
                key={index}
                className={`funeral-service-category ${activeService.category === service.category ? 'active' : ''}`}
                onClick={() => selectCategory(service)}>
                {service.category}
              </div>
            ))}
          </div>
          <div className='flex gap-[48px] funeral-service-content-container'>
            <img src={activeService.img} alt='' className='funeral-service-img' />
            <div className='funeral-service-content'>
              <p className='text-[26px] mb-4 leading-[1] font-bold funeral-service-content-title'>{activeService.category}</p>
              <p className='whitespace-pre-line text-[18px] leading-[1.68] funeral-service-content-detail'>
                {activeService.content}
              </p>
              {activeService.note && <p className='li-dot mt-[28px] funeral-service-content-note'>{activeService.note}</p>}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FuneralSpecialService;
