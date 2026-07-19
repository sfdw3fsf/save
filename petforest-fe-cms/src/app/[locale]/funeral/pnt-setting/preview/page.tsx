'use client';

import { getReservationPhotoDetailApi, reservationPhotoKeys } from '@/store/funeral/reservation-photo/reservation-photo.api';
import { reservationPhotoDetailRespToDto } from '@/store/funeral/reservation-photo/reservation-photo.service';
import { ROOM_TYPE } from '@/utils/enums/funeral/funeral-mngt.enums';
import { parseStringToNumber } from '@/utils/parser/io.parser';
import { useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import 'swiper/css';
import 'swiper/css/pagination';
import { Autoplay } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/react';

const DELAY = 1500;

const page = () => {
  const { t: tSettlement } = useTranslation('settlement');
  const { t: tEnum } = useTranslation('enum');

  const searchParams = useSearchParams();

  const reservationPhotoId = useMemo(() => parseStringToNumber(searchParams.get('id')), [searchParams]);

  const { data } = useQuery({
    queryKey: reservationPhotoKeys.detail(reservationPhotoId!),
    queryFn: () => getReservationPhotoDetailApi(reservationPhotoId!),
    select: (response) => {
      return response?.data?.data && reservationPhotoDetailRespToDto(response.data.data);
    },
    enabled: reservationPhotoId !== null,
  });

  const fileIds = useMemo(
    () =>
      searchParams
        .get('ids')
        ?.split(',')
        ?.map((fileId) => parseStringToNumber(fileId))
        ?.filter((fileId) => fileId !== null) || [],
    [searchParams],
  );

  const roomType = useMemo(() => {
    const type = searchParams.get('room');
    if (!type || !(type in ROOM_TYPE)) return null;
    return type as ROOM_TYPE;
  }, [searchParams]);

  const fileUrls = useMemo<string[]>(() => {
    const mapped = data?.files?.map((file) => (fileIds?.includes(file.id) ? file.url : null))?.filter((url) => url !== null);

    if (!mapped?.length) return [];

    const result = [...mapped];
    // repeat the array to reach minimum slides for loop mode
    while (result.length < 5) {
      result.push(...mapped);
    }
    return result;
  }, [data?.files, fileIds]);

  if (reservationPhotoId === null) {
    return null;
  }

  return (
    <div className='flex w-full h-full min-h-fit bg-[#E7EDE4] items-center justify-center max-lg:pt-12 max-lg:pb-28'>
      <div className='flex w-full 2xl:max-w-[1600px] h-full max-lg:h-fit min-h-fit items-center justify-center max-lg:flex-col'>
        {/* image */}
        <div className='flex w-[50%] h-full max-lg:h-[800px] max-sm:h-[520px] items-center justify-center'>
          <div className='flex max-2xl:w-2/3 w-4/5 h-full max-h-[1000px] items-center justify-center'>
            {!!fileUrls?.length && (
              <Swiper
                direction='horizontal'
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
                {fileUrls.map((url, index) => (
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
                {!!roomType && (
                  <div className='absolute -top-5 flex w-full items-center justify-center mb-12'>
                    <div className='flex h-10 items-center justify-center bg-[#0b3b20] rounded-full px-4 text-lg leading-none font-bold text-white'>
                      {tEnum(`reservation.room_type.${roomType}`, roomType)}
                    </div>
                  </div>
                )}

                <div className='flex w-full flex-col items-center justify-center'>
                  <div className='flex w-full flex-col gap-5 justify-center items-center'>
                    <div className='text-lg'>{tSettlement('preview.pet_name')}</div>
                    <div className='font-bold text-5xl text-[#0b3b20]'>{data?.reservation?.petName ?? '-'}</div>
                    <div className='text-xl text-[#a2a2a2] mt-1 font-semibold'>{data?.reservation?.petBreed?.name}</div>
                  </div>

                  <span className='w-full max-w-80 h-[1px] bg-[#cdd6d1] mt-14 mb-12' />

                  <div className='flex w-full flex-col gap-3 justify-center items-center '>
                    <div className='text-lg'>{tSettlement('preview.guardian_name')}</div>
                    <div className='font-bold text-4xl text-[#0b3b20]'>{data?.reservation?.guardianName ?? '-'}</div>
                  </div>
                </div>
              </div>

              <div className='text-xl text-[#224b35] font-semibold my-4'>{tSettlement('preview.memory_text')}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default page;
