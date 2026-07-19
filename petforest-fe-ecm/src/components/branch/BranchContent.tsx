'use client';

import {
  BRANCH_1_DETAIL,
  BRANCH_2_DETAIL,
  BRANCH_HASH_TAG,
  BranchDetailHashtagType,
  BranchDetailType,
  HERB_CENTER_DETAIL,
} from '@/utils/constant/branch/branch-detail.constant';
import { useDisclosure } from '@chakra-ui/react';
import { useParams } from 'next/navigation';
import { useEffect, useState } from 'react';
import Slider, { Settings } from 'react-slick';
import BranchCertModal from './BranchCertModal';

const BranchContent = () => {
  const { branchId } = useParams();
  const { isOpen, onOpen, onClose } = useDisclosure();

  const [branchDetail, setBranchDetail] = useState<BranchDetailType | null>(null);
  const [activeHashtag, setActiveHashTag] = useState<BranchDetailHashtagType | null>(null);

  const settings: Settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    appendDots: (dots) => (
      <div style={{ position: 'absolute', top: '0', height: 'fit-content', marginTop: '62%' }}>
        <ul>{dots}</ul>
      </div>
    ),
  };

  const selectHashtag = (hashTag: BranchDetailHashtagType) => {
    setActiveHashTag(hashTag);
  };

  useEffect(() => {
    if (branchId === 'branch-1') {
      setBranchDetail(BRANCH_1_DETAIL);
      setActiveHashTag(BRANCH_HASH_TAG[0]);
    } else if (branchId === 'branch-2') {
      setBranchDetail(BRANCH_2_DETAIL);
      setActiveHashTag(BRANCH_HASH_TAG[0]);
      // } else if (branchId === 'branch-3') {
      //   setBranchDetail(BRANCH_3_DETAIL);
      //   setActiveHashTag(BRANCH_HASH_TAG[0]);
    } else {
      setBranchDetail(HERB_CENTER_DETAIL);
      setActiveHashTag(HERB_CENTER_DETAIL?.hashTags?.[0] || null);
    }
  }, [branchId]);

  return branchDetail ? (
    <>
      <div className='mt-[100px] mb-10 text-center branch-title'>
        <p className='text-[36px] text-green-main leading-[1.5] font-bold mb-[22px]'>{branchDetail.name}</p>
        {branchDetail.name !== HERB_CENTER_DETAIL.name && (
          <p className='btn-branch-certificate' onClick={onOpen}>
            동물장묘업 등록증
          </p>
        )}
      </div>
      <div className='sec'>
        <div className='lmt maxw'>
          <div className='flex justify-between branch-content-container'>
            <div className='w-[64%] slider-container'>
              <Slider {...settings}>
                {branchDetail.introImages.map((img, index) => (
                  <div key={index} className='!select-none !outline-none'>
                    <img src={img.link} alt='' />
                    <p className='pt-[6px] text-[13px] text-[#555] text-center slider-title'>{img.title}</p>
                  </div>
                ))}
              </Slider>
            </div>
            <div className='branch-detail-content'>
              <div className='branch-detail-info'>
                <div className='detail-item'>
                  <span className='info-title'>지점 주소</span>
                  <span className='flex'>
                    {branchDetail.address}
                    <a
                      href={branchDetail.addressLink}
                      target='_blank'
                      className='btn-branch-certificate ml-3 md:ml-2'
                      rel='noreferrer'>
                      길 찾기
                    </a>
                  </span>
                </div>
                {branchDetail.openHour && (
                  <div className='detail-item'>
                    <span className='info-title'>운영 시간</span>
                    {branchDetail.openHour}
                  </div>
                )}
                <div className='detail-item'>
                  <span className='info-title'>문의 전화</span>
                  {branchDetail.phone}
                </div>
              </div>
              <a
                href={branchDetail.link}
                target={branchDetail.isOpenNewTab === true ? '_blank' : undefined}
                className='btn-branch-reservation'
                rel='noreferrer'>
                {branchDetail.reservationText}
              </a>
              <div className='branch-hashtag-list overflow-hidden'>
                {(branchDetail.name !== HERB_CENTER_DETAIL.name
                  ? (BRANCH_HASH_TAG ?? [])
                  : (HERB_CENTER_DETAIL.hashTags ?? [])
                ).map((hashtag, index) => {
                  const isActive = branchDetail.name === HERB_CENTER_DETAIL.name || hashtag.title === activeHashtag?.title;

                  return (
                    <div
                      key={index}
                      onClick={() => branchDetail.name !== HERB_CENTER_DETAIL.name && selectHashtag(hashtag)}
                      className={`branch-hashtag-item ${isActive ? 'active' : ''}`}>
                      {hashtag.title}
                    </div>
                  );
                })}
              </div>

              {branchDetail.name !== HERB_CENTER_DETAIL.name && (
                <div className='branch-hashtag-desc'>
                  {activeHashtag?.image && <img className='branch-hashtag-img' src={activeHashtag?.image} alt='' />}
                  <p className='branch-hashtag-content !text-grey-dark'>{activeHashtag?.content}</p>
                  <a href={activeHashtag?.link} className='btn-learn-more'>
                    더 알아보기 {'>'}
                  </a>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
      <BranchCertModal branchDetail={branchDetail} isOpen={isOpen} onClose={onClose} />
    </>
  ) : (
    <div></div>
  );
};

export default BranchContent;
