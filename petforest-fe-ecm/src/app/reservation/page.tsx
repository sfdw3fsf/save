'use client';
import '@/components/reservation/reservation.css';
import { useRouter } from 'next/navigation';

const ReservationPage = () => {
  const router = useRouter();
  const handleNavigateReserve = () => {
    router.push('/reservation/reserve');
  };

  const handleNavigateNaver = () => {
    router.push('/reservation/reserve_naver');
  };

  return (
    <div className='cont !mb-[70px] md:!mb-[134px]' id='reservation'>
      <div className='md:!mt-[35px] !mt-[5px] cont-head cont-head2 flex justify-center items-center flex-col text-center !mb-[37px] md:!mb-[25px]'>
        <h1 className='text-green-main md:text-[33px] text-[27px] font-bold leading-none'>장례 예약</h1>
        <p className='mt-[17px] text-grey-sub text-[15px]'>원하시는 예약 절차를 선택 해주세요.</p>
        <a
          href='/reservation/check_reserve'
          className='inline-block py-[13px] px-[16px] mt-[20px] border border-gray-400 mx-auto text-center text-[13px] text-gray-400 leading-none rounded-[3px]'>
          <span>예약 조회</span>
        </a>
      </div>
      <div className='lmt maxw'>
        <div className='sec-body'>
          <div className='relative w-full max-w-[480px] mx-auto text-center'>
            <div className='flex flex-col gap-5'>
              <button className='bg-green-main text-white btn-check-reserve' onClick={handleNavigateReserve}>
                펫포레스트 간편 예약
              </button>
              <button
                onClick={handleNavigateNaver}
                rel='noreferrer noopener'
                className='bg-green-pale text-green-main btn-check-reserve'>
                네이버 예약
              </button>
            </div>
          </div>
          <div className='!mt-[79px] !mb-[30px] md:!mb-[35px] cont-head cont-head2 flex justify-center items-center flex-col text-center md:!mt-[122px]'>
            <h1 className='text-green-main md:text-[28px] text-[23px] font-bold leading-none'>문의 상담</h1>
            <p className='mt-[17px] md:mt-[14px] text-grey-sub text-[15px]'>운영시간: 오전 7시 ~ 오후 11시</p>
          </div>
          <div className='lmt maxw'>
            <div className='sec-body'>
              <div className='relative w-full max-w-[480px] mx-auto text-center'>
                <div className='flex flex-col gap-5'>
                  <a
                    href='https://pf.kakao.com/_aGPdxb'
                    target='_blank'
                    rel='noreferrer noopener'
                    className='bg-yellow-medium text-dark-grey btn-check-reserve'>
                    카카오톡 문의
                  </a>
                  <a href='tel:1577-0996' className='bg-grey-light text-dark-grey btn-check-reserve'>
                    유선전화 문의
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ReservationPage;
