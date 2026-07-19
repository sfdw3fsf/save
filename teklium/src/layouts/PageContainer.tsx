import React, { ReactNode } from 'react';
import { Helmet, HelmetProvider } from 'react-helmet-async';
import Footer from './footer/Footer';

type PageContainerProps = {
  children?: ReactNode;
  title?: string;
  isTitlePart?: boolean;
  subTitle?: string;
  mainTitle?: string;
  detailTitle?: string;
  isSubtitlePart?: boolean;
};
export default function PageContainer({
  children,
  title,
  isTitlePart = false,
  subTitle,
  mainTitle,
  detailTitle,
  isSubtitlePart = false,
}: PageContainerProps) {
  return (
    <>
      <HelmetProvider>
        <Helmet>{title} |Teklium</Helmet>
      </HelmetProvider>
      {/* <div className='pt-navbar px-4 lg:px-48 h-full w-full'> */}
      <div className='flex flex-col justify-center px-4 lg:px-48 items-center pt-navbar bg-white'>
        {isTitlePart && (
          <div className='lg:px-[115px] '>
            <section className='flex flex-col gap-6 justify-center items-center pt-12 lg:pb-12'>
              {isSubtitlePart && <p className='text-royal-600 text-sm font-medium text-center '>{subTitle}</p>}
              <div className='gap-3 flex flex-col'>
                <h1 className='text-gray-950 lg:text-6xl text-4xl font-bold text-center'>{mainTitle}</h1>
                <p className='lg:text-xl text-lg text-gray-600 text-center  '>{detailTitle}</p>
              </div>
            </section>
          </div>
        )}
        {children}
      </div>
      <Footer />
    </>
  );
}
