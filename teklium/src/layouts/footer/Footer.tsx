import React from 'react';

export default function Footer() {
  return (
    <section className='px-48 pb-24 pt-12  '>
      <div className='pt-12 border-t w-full border-gray-200 flex flex-row justify-between'>
        {/* Left Part */}
        <div className='gap-6 flex flex-col '>
          <h1 className='text-gray-950 text-base font-medium'>TEKLIUM INC.</h1>
          <p className='text-sm text-gray-400'>© TEKLIUM INC.. All rights reserved.</p>
        </div>
        {/* Right Part */}
        <div className='flex flex-col gap-6'>
          <div className='flex flex-row gap-6'>
            <a className='text-sm font-medium text-gray-700'>About Us</a>
            <a className='text-sm font-medium text-gray-700'>News & Insights </a>
            <a className='text-sm font-medium text-gray-700'>Revolutionary Technologies</a>
          </div>
          <div className='flex flex-row gap-6'>
            <a className='text-sm font-medium text-gray-700'>Hydrogen Battery</a>
            <a className='text-sm font-medium text-gray-700'>The Carbon Alliance</a>
          </div>
        </div>
      </div>
    </section>
  );
}
