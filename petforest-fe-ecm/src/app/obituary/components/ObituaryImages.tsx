'use client';
import { ObituaryDTO } from '@/store/obituary/dto/obituary.dto';
import { useMemo } from 'react';
import ObituarySlider from './ObituarySlider';

type ObituaryImagesProps = {
  data?: ObituaryDTO;
};

const ObituaryImages = ({ data }: ObituaryImagesProps) => {
  const petImages = useMemo(() => {
    if (!data?.obituaryFileIds) return [];
    return data?.obituaryFileIds.map((item) => ({ img: item.url || '' }));
  }, [data]);

  return (
    <div className='flex flex-col items-center mt-[24px]'>
      <div className='btn-rouneded-obituary px-5 py-[6px] '>영원히 기억할게</div>
      <div className='mt-[22px] w-full'>
        <ObituarySlider data={petImages} />
      </div>

      <div className='text-center mt-[25px]'>
        <div className='text-[31px] font-bold'>{data?.petInformation.name}</div>
        <div className='text-[20px]'>{data?.petInformation.age}살</div>
      </div>
    </div>
  );
};

export default ObituaryImages;
