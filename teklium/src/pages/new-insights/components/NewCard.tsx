import React, { ReactNode } from 'react';

type NewCardProps = {
  image: string;
  partnerIcon: string;
  partnerName: string;
  title: string;
  date: string;
  timeToRead: string;
};
export default function NewCard({ image, partnerIcon, partnerName, title, date, timeToRead }: NewCardProps) {
  return (
    <section className='w-full h-full border border-slate-200 shadow-sm flex flex-col rounded-lg '>
      {/* Image */}
      <div className=''>
        <img src={image} alt='New Image' />
      </div>
      {/* Information Section*/}
      <section className='p-4 flex flex-col gap-2'>
        {/* Partner */}
        <div className='flex gap-2 justify-start items-center'>
          <span className='rounded-full'>
            <img className='size-6' src={partnerIcon} />
          </span>
          <p className='text-xs font-normal text-gray-950'>{partnerName}</p>
        </div>
        {/* Title */}
        <h1 className='text-base font-bold text-gray-900'>{title}</h1>
        {/* Time */}
        <div className='flex gap-2'>
          <p className='text-xs font-normal text-gray_true-500'>{date}</p>
          <p className='text-xs font-normal text-gray_true-500'>{timeToRead}</p>
        </div>
      </section>
    </section>
  );
}
