'use client';
import PageContainer from '@/components/common/page-container/PageContainer';
import FirstReservationForm from '@/components/reservation/FirstReservationForm';
import '@/components/reservation/reservation.css';
import { SecondReservationForm } from '@/components/reservation/SecondReservationForm';
import { Suspense, useEffect, useState } from 'react';

const ReservePage = () => {
  const [step, setStep] = useState<number>(1);
  const [selectedDay, setSelectedDay] = useState<number | null>(null);
  const [time, setTime] = useState<string | null>(null);

  const handleNextStep = () => {
    setStep((pre) => pre + 1);
  };

  useEffect(() => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  }, [step]);

  return (
    <PageContainer className='unset'>
      <div className='lmt maxw pt-[35px] mb-[76px] md:mb-[166px] md:pt-[65px]' id='reserve'>
        <div className='relative w-full max-w-[480px] mx-auto text-center'>
          <div className='cont-head cont-head2 flex items-center text-center !mt-0'>
            <h1 className='text-black ml-[34px] text-[27px] flex-grow text-center font-bold leading-none md:text-[33px]'>
              {step === 1 ? '지점 및 일시선택' : '예약정보'}
            </h1>
            <div className='ml-auto flex space-x-2'>
              <span className={`step step-1 ${step === 1 && 'active'}`}></span>
              <span className={`step step-1 ${step === 2 && 'active'}`}></span>
            </div>
          </div>
          {step === 1 ? (
            <FirstReservationForm
              handleNextStep={handleNextStep}
              selectedDay={selectedDay}
              setSelectedDay={setSelectedDay}
              setTime={setTime}
              time={time}
            />
          ) : (
            <SecondReservationForm date={selectedDay ?? 0} time={time || ''} />
          )}
        </div>
      </div>
    </PageContainer>
  );
};

const WrappedReservePage = () => (
  <Suspense>
    <ReservePage />
  </Suspense>
);

export default WrappedReservePage;
