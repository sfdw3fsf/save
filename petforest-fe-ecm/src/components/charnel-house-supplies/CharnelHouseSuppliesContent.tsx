import { SUPPLIES } from '@/utils/constant/charnel-house-supplies/charnel-house-supplies.constant';
import CharnelHouseSuppliesItem from './CharnelHouseSuppliesItem';

const CharnelHouseSuppliesContent = () => {
  return (
    <div className='sec'>
      <div className='sec-inner lmt maxw !border-b-0 !pb-0'>
        <div className='sec-title !mb-[54px] !lg:mb-[96px]'>
          <h2>장례 용품</h2>
          <p>
            장례 용품 하나하나 내 가족을 보내주는 마음으로 준비했습니다. 펫포레스트가 마련한 작은 정성들이 아이의 마지막에 편안한
            온기로 전달되기를 바랍니다.
          </p>
        </div>
        <div className='mt-0 md:mt-[78px]'>
          {SUPPLIES.map((supply, index) => (
            <CharnelHouseSuppliesItem key={index} supply={supply} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default CharnelHouseSuppliesContent;
