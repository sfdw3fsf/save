import '@/components/reservation/reservation.css';
const ReservationNaverPage = () => {
  return (
    <div className='cont' id='reserve'>
      <div className='md:!mt-[80px] !mt-[55px] cont-head cont-head2 flex justify-center items-center flex-col text-center '>
        <h1 className='text-green-main md:text-[33px] text-[27px] font-bold leading-none'>네이버 장례 예약</h1>
        <p className='mt-[17px] text-grey-sub text-[15px]'>원하시는 지점을 선택 해주세요.</p>
      </div>
      <div className='lmt maxw'>
        <div className='sec-body'>
          <div className='relative w-full max-w-[480px] mx-auto text-center'>
            <div className='flex flex-col gap-5 text-green-ivy mb-[20px]'>
              <a
                href='https://booking.naver.com/booking/6/bizes/563611?area=bbt'
                target='_blank'
                className='bg-grey-pearl border !font-medium border-green-ivy btn-check-reserve md:border-2'
                rel='noreferrer noopener'>
                경기 광주점
              </a>
              <a
                href='https://booking.naver.com/booking/13/bizes/1100733?preview=1&tab=details'
                className='bg-grey-pearl border !font-medium border-green-ivy btn-check-reserve md:border-2'
                target='_blank'
                rel='noreferrer noopener'>
                김포점
              </a>
            </div>
            <a href='/reservation' className='mx-auto text-center text-[14px] text-gray-400'>
              <span>{'<'}</span>&nbsp;
              <span className='relative'>
                <span className='underline-offset-4 underline'>{'이전 페이지로 이동'}</span>
              </span>
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ReservationNaverPage;
