'use client';

import { getPntDetailApi, pntSettingKeys } from '@/store/pnt-link/pnt-link.api';
import { pntSettingDetailRespToDto } from '@/store/pnt-link/pnt-link.service';
import { PNT_ROOM } from '@/utils/constant/pnt/pnt.constant';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import 'swiper/css';
import 'swiper/css/pagination';
import { Autoplay } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/react';

const DELAY = 1500;

const page = () => {
  const searchParams = useSearchParams();

  const id = useMemo(() => searchParams.get('id') || '', [searchParams]);

  const { data: pntDetailData } = useQuery({
    queryKey: pntSettingKeys.detail(id),
    queryFn: () => getPntDetailApi(id),
    select: (response) => {
      return response?.data?.data && pntSettingDetailRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
    enabled: !!id,
  });

  return (
    <div className='flex w-screen h-screen min-h-fit bg-[#E7EDE4] items-center justify-center max-lg:pt-12 max-lg:pb-28'>
      <div className='flex w-full 2xl:max-w-[1600px] h-full max-lg:h-fit min-h-fit items-center justify-center '>
        {/* image */}
        <div className='flex w-full h-full max-lg:h-[800px] max-sm:h-[520px] items-center justify-center'>
          <div className='flex max-2xl:w-2/3 w-4/5 h-full max-h-[1000px] items-center justify-center'>
            {!!pntDetailData?.files?.length && (
              <Swiper
                direction='vertical'
                loop
                slidesPerView={1}
                slidesPerGroup={1}
                autoplay={{
                  delay: DELAY,
                  disableOnInteraction: false,
                  pauseOnMouseEnter: true,
                }}
                modules={[Autoplay]}
                className='mySwiper w-full h-full'>
                {pntDetailData?.files.map((url, index) => (
                  <SwiperSlide key={index}>
                    <div className='flex w-full h-full items-center justify-center'>
                      <img className='object-contain w-full h-full max-xl:h-auto shrink-0 max-xl:aspect-[3/4]' src={url} />
                    </div>
                  </SwiperSlide>
                ))}
              </Swiper>
            )}
          </div>
        </div>

        {/* info */}
        <div className='flex w-full h-fit items-center justify-center text-center lg:py-12'>
          <div className='flex w-5/6 items-center justify-center'>
            <div className='flex w-full flex-col items-center justify-center'>
              <div className='mb-14 max-lg:mt-14'>
                <img src='/images/Logo.svg' alt='Logo' className='w-32 h-auto shrink-0' />
              </div>

              <div className='relative flex w-full items-center justify-center py-12 px-6 bg-white rounded-3xl shadow-[0_1px_2px_0_rgba(228,229,231,0.24)]'>
                {!!pntDetailData?.room && (
                  <div className='absolute -top-5 flex w-full items-center justify-center mb-12'>
                    <div className='flex h-10 items-center justify-center bg-[#0b3b20] rounded-full px-4 text-lg leading-none font-bold text-white'>
                      {PNT_ROOM[pntDetailData?.room]}
                    </div>
                  </div>
                )}

                <div className='flex w-full flex-col items-center justify-center'>
                  <div className='flex w-full flex-col gap-5 justify-center items-center'>
                    <div className='text-lg'>우리아이</div>
                    <div className='font-bold text-5xl text-[#0b3b20]'>{pntDetailData?.petName ?? '-'}</div>
                    {/* <div className='text-xl text-[#a2a2a2] mt-1 font-semibold'>{pntDetailData?.breedName}</div> */}
                  </div>

                  <span className='w-full max-w-80 h-[1px] bg-[#cdd6d1] mt-14 mb-12' />

                  <div className='flex w-full flex-col gap-3 justify-center items-center '>
                    <div className='text-lg'>보호자 성함</div>
                    <div className='font-bold text-4xl text-[#0b3b20]'>{pntDetailData?.guardianName ?? '-'}</div>
                  </div>
                </div>
              </div>

              <div className='text-xl text-[#224b35] font-semibold my-4'>함께한 순간을 영원히 기억합니다</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default page;
