import React from 'react';
import Image from '@/assets/images/landing/Pie.png';
type TestimonialCardProps = {
  image?: string;
  title?: string;
  subtitle?: string;
  onClick?: () => void;
};

export default function TestimonialCard() {
  return (
    <div className='max-w-[66rem] max-h-[39rem] h-full w-full p-10 bg-gray-950 rounded-3xl flex flex-row items-center'>
      {/* Title */}
      <div className='flex flex-col gap-[6.8125rem]'>
        <h1 className='text-7xl font-bold text-gray-25 leading-[5.625rem]'>Artificial Intelligence</h1>
        <p className='text-2xl font-normal text-gray-25'>
          Our state-of-the-art AI processors and memory solutions meet the growing computational demands of data centers and
          AI-focused businesses.
        </p>
        <div>Button</div>
      </div>
      {/* Image */}
      <div className='max-w-[420px] w-full max-h-[420px] h-full'>
        <img src={Image} alt='Card Image'></img>
      </div>
    </div>
  );
}
