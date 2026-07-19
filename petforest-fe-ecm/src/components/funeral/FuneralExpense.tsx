'use client';
import useResize from '@/hook/useResize';
import { FUNERAL_EXPENSES } from '@/utils/constant/funeral/funeral-expense.constant';

const FuneralExpense = () => {
  const { isMobile, isTablet } = useResize();

  return (
    <div className='sec !mb-0' id='expense'>
      <div className='sec-inner lmt maxw'>
        <div className='sec-title'>
          <h2>장례 비용</h2>
          <p>
            온전히 장례에 집중할 수 있도록 모든 장례 비용을 투명하게 공개합니다. 품격 있는 장례를 위해 작은 소품 하나도 마음을
            담아 준비합니다.
          </p>
          <div className='mt-[10px]'>
            <p className='!text-[13px] !text-[#999] leading-[1.68]'>
              • 기재된 장례 비용은 10kg 미만 기준이며, 부가세 포함된 금액입니다.
            </p>
          </div>
        </div>
        <div>
          <div className='text-center'>
            {isMobile || isTablet ? (
              <>
                <p className='funeral-expense-common text-black-70 !flex flex-col text-[12.5px] font-light px-0 '>
                  <span className='text-[15px] font-semibold w-full border-b border-[rgba(76,76,76,0.5)] px-[20px] pb-[5px]'>
                    공통 장례 서비스
                  </span>
                  <span className='px-[20px] pt-[10px] md:pt-0 md:px-0'>
                    염습 + 단독 추모실 제공 + 국화 헌화 + 개별 화장 + 한지 유골 주머니 + 장례 증명서 발급
                  </span>
                </p>
              </>
            ) : (
              <p className='funeral-expense-common !px-[20px]'>
                공통 장례 서비스 : 염습 + 단독 추모실 제공 + 국화 헌화 + 개별 화장 + 한지 유골 주머니 + 장례 증명서 발급
              </p>
            )}
          </div>
          <div className='flex gap-[48px] flex-wrap funeral-expense-container'>
            {FUNERAL_EXPENSES.slice(0, 3).map((expense, index) => (
              <div key={index} className='funeral-expense-item'>
                <img src={expense.img} alt='' />
                <div className='mt-4 mb-1 flex justify-between pb-[4px] border-b border-grey-mist md:border-none'>
                  <p className='text-[18px] text-[#343a40] leading-[1.5] font-bold funeral-expense-title'>{expense.title}</p>
                  <p className='text-[14px] text-[#495057] leading-[1.5] font-bold funeral-expense-price'>{expense.price}원</p>
                </div>
                <p className='text-[#495057] leading-[1.68] funeral-expense-content'>
                  {expense.content.split('*고급 수의 + 고급 오동나무관').map((part, i, arr) =>
                    i < arr.length - 1 ? (
                      <span key={i}>
                        {part}
                        <span className='font-semibold underline underline-offset-2'>*고급 수의 + 고급 오동나무관</span>
                      </span>
                    ) : (
                      part
                    ),
                  )}
                </p>
                {expense.note && <p className='text-[#999] text-[14px] leading-[1.68] funeral-expense-note'>{expense.note}</p>}
              </div>
            ))}
          </div>
          <div className='flex gap-[48px] flex-wrap funeral-expense-container'>
            {FUNERAL_EXPENSES.slice(-3).map((expense, index) => (
              <div key={index} className='funeral-expense-item'>
                <img src={expense.img} alt='' />
                <div className='mt-4 mb-1 flex justify-between pb-[4px] border-b border-grey-mist md:border-none'>
                  <p className='text-[18px] text-[#343a40] leading-[1.5] font-bold funeral-expense-title'>{expense.title}</p>
                  <p className='text-[14px] text-[#495057] leading-[1.5] font-bold funeral-expense-price'>{expense.price}원</p>
                </div>
                <p className='text-[#495057] leading-[1.68] funeral-expense-content'>
                  {expense.content.split('*고급 수의 + 고급 오동나무관').map((part, i, arr) =>
                    i < arr.length - 1 ? (
                      <span key={i}>
                        {part}
                        <span className='font-bold underline underline-offset-2'>*고급 수의 + 고급 오동나무관</span>
                      </span>
                    ) : (
                      part
                    ),
                  )}
                </p>
                {expense.note && <p className='text-[#999] text-[14px] leading-[1.68] funeral-expense-note'>{expense.note}</p>}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default FuneralExpense;
