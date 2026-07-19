import '@/components/history/history.css';
import BlockGroup from '@/components/history/HistoryBlockGroup';
import { BLOCK_GROUP_INFO } from '@/utils/constant/history/block-group.constant';

const HistoryPage = () => {
  return (
    <div className='cont' id='history'>
      <div className='cont-head cont-head2 flex justify-center items-center flex-col text-center '>
        <h1 className='text-green-main md:text-[36px] text-[25px] font-bold'>펫포레스트가 걸어온 길</h1>
        <p className='mt-[17px] text-grey-sub md:text-[17px] text-[14px]'>
          지나온 여정보다 더 따뜻하고 의미 있는 발걸음을 이어가겠습니다.
        </p>
      </div>
      <div className='sec !mb-0 lmt maxw border-none'>
        <div className='sec-inner '>
          <div className='sec-body'>
            <div className='history-cont'>
              {BLOCK_GROUP_INFO.map((block, index) => (
                <BlockGroup key={index} year={block?.year} context={block?.context} />
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HistoryPage;
