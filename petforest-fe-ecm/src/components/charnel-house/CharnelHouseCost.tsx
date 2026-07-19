'use client';
import { BRANCHES } from '@/utils/constant/branch.constant';
import { CHARNEL_HOUSE_COST_ONE, CHARNEL_HOUSE_COST_TWO } from '@/utils/constant/charnel-house/charnel-house-cost.constant';
import { useEffect, useState } from 'react';

type CharnelHouseContentProps = {
  activeBranch: string;
  setActiveBranch: (activeBranch: string) => void;
};
const CharnelHouseCost = ({ activeBranch, setActiveBranch }: CharnelHouseContentProps) => {
  const [activeCost, setActiveCost] = useState(CHARNEL_HOUSE_COST_ONE);

  const selectBranch = (branch: string) => {
    setActiveBranch(branch);
  };

  useEffect(() => {
    if (activeBranch === BRANCHES[0]) {
      setActiveCost(CHARNEL_HOUSE_COST_ONE);
      return;
    }
    setActiveCost(CHARNEL_HOUSE_COST_TWO);
  }, [activeBranch]);

  return (
    <div className='sec !mb-0 bg-grey-cost'>
      <div className='sec-inner lmt lmtw !mb-[60px] !pb-0 !border-0 lg:!mb-[150px]'>
        <div className='sec-title'>
          <h2>봉안당 비용 안내</h2>
          <p>모든 비용은 VAT 포함 입니다.</p>
        </div>
        <div className='tab-container mt-[30px]'>
          {BRANCHES.map((branch, index) => (
            <div
              key={index}
              onClick={() => selectBranch(branch)}
              className={`tab-title ${activeBranch === branch ? 'active' : ''}`}>
              {branch}
            </div>
          ))}
        </div>
        <div className='mt-[45px]'>
          <div className='cost-container'>
            {activeCost.tables.map((table, index) => (
              <div key={index} className='cost-table'>
                <p className='cost-table-title'>{table.title}</p>
                {table.costs.map((cost, key) => (
                  <div key={key} className='cost-table-item'>
                    <p className='charnel-house-cost-content'>{cost.content}</p>
                    <p className='charnel-house-cost-price'>{cost.price}원</p>
                  </div>
                ))}
              </div>
            ))}
          </div>
          <div className='flex justify-center notes-container'>
            <ul className='mt-[45px] w-fit lg:mt-[90px]'>
              {activeCost.notes.map((note, index) => (
                <li key={index} className='li-dot'>
                  {note}
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CharnelHouseCost;
