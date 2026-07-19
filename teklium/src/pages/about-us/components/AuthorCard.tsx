import React from 'react';
import AuthorImage from '@/assets/images/about-us/CeoImage.png';
export default function AuthorCard() {
  return (
    <div className=' p-3 flex flex-col gap-3 bg-gray-50 shadow-lg'>
      <div>
        <img src={AuthorImage} alt='AuthorImage'></img>
      </div>
      <div className='flex flex-col '>
        <h1 className='text-gray-950 text-lg'>Jason Ho</h1>
        <p className='text-gray-600 font-normal text-xs'>CEO</p>
      </div>
    </div>
  );
}
