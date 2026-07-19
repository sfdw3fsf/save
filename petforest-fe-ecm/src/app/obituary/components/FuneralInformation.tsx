'use client';
import { ObituaryDTO } from '@/store/obituary/dto/obituary.dto';
import { BRANCHES } from '@/utils/constant/branch.constant';
import { BRANCH_1_DETAIL, BRANCH_2_DETAIL, BranchDetailType } from '@/utils/constant/branch/branch-detail.constant';
import {
  CHARNEL_HOUSE_BRANCHES_ONE,
  CHARNEL_HOUSE_BRANCHES_TWO,
} from '@/utils/constant/charnel-house/charnel-house-branch.constant';
import { PhoneIcon } from '@chakra-ui/icons';
import { useEffect, useState } from 'react';
import ObituarySlider from './ObituarySlider';

type FuneralInformationProps = {
  data?: ObituaryDTO;
};

const FuneralInformation = ({ data }: FuneralInformationProps) => {
  const [activeSlider, setActiveSlider] = useState(CHARNEL_HOUSE_BRANCHES_ONE);
  const [branchDetail, setBranchDetail] = useState<BranchDetailType | null>(null);

  useEffect(() => {
    if (data?.guardianInformation.branch === BRANCHES[0]) {
      setActiveSlider(CHARNEL_HOUSE_BRANCHES_ONE);
      setBranchDetail(BRANCH_1_DETAIL);
      return;
    }
    setActiveSlider(CHARNEL_HOUSE_BRANCHES_TWO);
    setBranchDetail(BRANCH_2_DETAIL);
  }, [data?.guardianInformation.branch]);

  return (
    <div className='mt-[60px] flex flex-col items-center text-center'>
      <div className='btn-rouneded-obituary px-5 py-[6px] bg-green-main !text-white'>장례 정보</div>
      <div className='mt-5 text-[20px] font-extrabold '>
        <div>{data?.guardianInformation.funeralDate}</div>
        {/* <div>오후 2시</div> */}
      </div>
      <div className='mt-[20px] w-full'>
        <ObituarySlider data={activeSlider} />
      </div>

      <div className='text-[18px] font-medium mt-5 flex flex-col items-center'>
        <div>{branchDetail?.name}</div>
        <div className='font-bold'>{branchDetail?.address}</div>

        <div className='flex items-center gap-1 mt-2'>
          <PhoneIcon w={5} h={5} />
          <a href={`tel:${branchDetail?.phone}`} className='text-[16px] font-normal underline cursor-pointer'>
            {branchDetail?.phone}
          </a>
        </div>
      </div>

      <div className='mt-[60px] flex flex-col items-center'>
        <div className='text-[15px] font-medium text-green-bright'>장례식장 오시는길</div>
        <div className='flex gap-[44px] mt-[20px]'>
          <div className='map-container'>
            <a target='blank' href={branchDetail?.addressLink}>
              <img className='map-icon' src='/images/obituary/map-naver.png' alt='map' />
            </a>
            <div className='map-text'>네이버 지도</div>
          </div>
          <div className='map-container'>
            <a target='blank' href={branchDetail?.addressLinkKakao}>
              <img className='map-icon' src='/images/obituary/map-kakao.png' alt='map' />
            </a>
            <div className='map-text'>카카오 맵</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FuneralInformation;
