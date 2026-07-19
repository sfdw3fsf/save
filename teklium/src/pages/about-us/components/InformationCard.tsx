import React from 'react';
type InformationCardProps = {
  mainTitle: string;
  subTitle: string;
  detail: string;
};
export default function InformationCard({ mainTitle, subTitle, detail }: InformationCardProps) {
  return (
    <section className='max-2xl:max-w-[598px] max-2xl:max-h-[244px] w-full h-full p-4 border border-gray-200 shadow-sm rounded-lg flex flex-col gap-2'>
      <h1 className='text-royal-900 text-2xl font-bold'>{mainTitle}</h1>
      <p className='text-sm font-normal text-royal-600'>{subTitle}</p>
      <p className='text-base text-gray-500 font-normal'>{detail}</p>
    </section>
  );
}
