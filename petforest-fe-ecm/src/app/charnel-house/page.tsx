'use client';
import '@/components/charnel-house/charnel-house.css';
import CharnelHouseContent from '@/components/charnel-house/CharnelHouseContent';
import CharnelHouseCost from '@/components/charnel-house/CharnelHouseCost';
import PageContainer from '@/components/common/page-container/PageContainer';
import PageTitleBanner from '@/components/common/page-title-banner/PageTitleBanner';
import { BRANCHES } from '@/utils/constant/branch.constant';
import { useState } from 'react';

const CharnelHousePage = () => {
  const [activeBranch, setActiveBranch] = useState(BRANCHES[0]);
  return (
    <PageContainer>
      <PageTitleBanner
        title='기억의 정원'
        subTitle={
          <>
            우리 아이의 마지막을 오래 기억할 수 있도록, 하나하나 정성을 다해 준비했습니다.
            {` `}
            펫포레스트가 마련한 기억의 정원에서 평안하기를 바랍니다.
          </>
        }
        img='/images/charnel-house/charnel-house-banner.jpg'
      />
      <CharnelHouseContent activeBranch={activeBranch} setActiveBranch={setActiveBranch} />
      <CharnelHouseCost activeBranch={activeBranch} setActiveBranch={setActiveBranch} />
    </PageContainer>
  );
};

export default CharnelHousePage;
