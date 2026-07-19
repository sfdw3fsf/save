'use client';
import { ChevronLeftIcon, ChevronRightIcon } from '@chakra-ui/icons';
import { useRef, useState } from 'react';
import Slider, { Settings } from 'react-slick';
import '../obituary.css';

type ObituarySliderProps<T> = {
  data: T[];
};

const ObituarySlider = <T extends object>({ data }: ObituarySliderProps<T>) => {
  //State
  const [currentImage, setCurrentImage] = useState<number>(0);

  //Ref
  const sliderRef = useRef<any>();

  //Slick
  const settings: Settings = {
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    afterChange: (current) => setCurrentImage(current),
  };
  return (
    <>
      {data.length > 0 && (
        <div className='w-full slider-container mt-[22px] relative'>
          <div className='slick-page'>
            {currentImage + 1}/{data.length}
          </div>
          <button
            className='btn-slick left-3'
            onClick={() => {
              sliderRef.current.slickPrev();
            }}>
            <ChevronLeftIcon w={8} h={8} color={'#fff'} />
          </button>
          <Slider ref={sliderRef} {...settings}>
            {data.map((img, index) => (
              <div key={index} className='!select-none !outline-none h-[287px] w-full'>
                <img src={(img as any).img} alt={(img as any).img} className='h-full w-full rounded-xl object-cover' />
              </div>
            ))}
          </Slider>
          <button
            className='btn-slick right-3'
            onClick={() => {
              sliderRef.current.slickNext();
            }}>
            <ChevronRightIcon w={8} h={8} color={'#fff'} />
          </button>
        </div>
      )}
    </>
  );
};

export default ObituarySlider;
