/* eslint-disable jsx-a11y/anchor-is-valid */
import { HOME_BRANCHES } from '@/utils/constant/home/home-branch.constants';

const HomeBranch = () => {
  return (
    <div className='sec border-b border-b-[#ccc] !mb-[80px] md:!mb-[120px]'>
      <div className='sec-head !mb-[45px] md:!mb-0'>
        <h2>펫포레스트 지점 안내</h2>
      </div>
      <div className='my-[50px] maxw lmt flex gap-[48px] home-branch-list md:my-[80px] lg:my-[30px]'>
        {HOME_BRANCHES.map((branch, index) => (
          <div key={index} className='home-branch-item !mb-[60px]'>
            <a href={branch.link}>
              <img src={branch.img} alt='' />
            </a>
            <p className='text-[23px] text-[#1a1a1a] leading-[1.5] font-bold flex items-center gap-[14px] branch-name'>
              {branch.name}
              {branch.isNew && <span className='home-tag'>NEW</span>}
            </p>
            <p className='mt-[5px] text-[#272727] leading-[1.5]'>{branch.address}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default HomeBranch;
