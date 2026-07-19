/* eslint-disable jsx-a11y/anchor-is-valid */
import { FUNERAL_AFTERS } from '@/utils/constant/funeral/funeral-after.constant';

const FuneralAfter = () => {
  return (
    <div className='sec !mb-0'>
      <div className='sec-inner lmt maxw'>
        <div className='sec-title !mb-[36px] md:mb-[50px]'>
          <h2>장례 절차 후 선택 사항</h2>
          <p>
            기본 장례 절차를 마친 후 추가로 선택하실 수 있는 장례 과정 입니다. 전과정은 고격 요청 시에만
            <br />
            진행 합니다.
          </p>
        </div>
        <div className='flex gap-[48px] funeral-after-container '>
          {FUNERAL_AFTERS.map((after, index) => (
            <div key={index} className='funeral-after-item'>
              <img src={after.img} alt='' />
              <div className='flex mt-6 gap-8 funeral-after-item-content'>
                <h3 className='w-[30%] text-[24px] font-bold leading-[1.3] block'>{after.title}</h3>
                <div className='w-fit funeral-after-item-detail'>
                  <p className='text-[18px] text-[#676767] leading-[1.68]'>{after.subTitle}</p>
                  <ul className='mt-4'>
                    {after.contents.map((content, key) => (
                      <li key={key} className='li-dot'>
                        {content}
                      </li>
                    ))}
                  </ul>
                  <div className='mt-4'>
                    <a href={after.link} className='btn'>
                      {after.btnText}
                    </a>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default FuneralAfter;
