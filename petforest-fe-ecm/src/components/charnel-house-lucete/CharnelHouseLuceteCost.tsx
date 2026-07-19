import { LUCETE_COSTS } from '@/utils/constant/charnel-house-lucete/charnel-house-lucete-cost.constant';

const CharnelHouseLuceteCost = () => {
  return (
    <div className='sec !mb-0 bg-grey-cost '>
      <div className='sec-inner lmt lmtw !py-[70px] lg:!py-[150px]'>
        <div className='sec-title'>
          <h2>루세떼 비용 안내</h2>
          <p>모든 비용은 VAT 포함 입니다.</p>
        </div>
        <div className='cost-container'>
          {LUCETE_COSTS.map((cost, index) => (
            <div key={index} className='cost-table'>
              <div className='cost-table-item !items-start mt-6'>
                <p className='w-[60%] font-semibold text-[17px] charnel-house-cost-content'>
                  {cost.content} <br />
                  <span className='text-[14px] font-normal '>{cost.note}</span>
                </p>
                <p className='charnel-house-cost-price'>{cost.price}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default CharnelHouseLuceteCost;
