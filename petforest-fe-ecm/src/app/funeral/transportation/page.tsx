import PageContainer from '@/components/common/page-container/PageContainer';
import '@/components/funeral-transportation/funeral-transportation.css';
import FuneralTransportationContent from '@/components/funeral-transportation/FuneralTransportationContent';
import { Suspense } from 'react';

const FuneralTransportation = () => {
  return (
    <PageContainer>
      {/* <PageTitleBanner
        title='차량 서비스'
        subTitle='장례식장에 들어서는 모든 발걸음을 기억합니다. 장례식장의 방문이 힘든 보호자를 위해, 슬픔이 머문 자리마다 펫포레스트가
            먼저 달려가겠습니다.'
        img='/images/funeral/transportation/transportation-banner.png'
      /> */}
      <Suspense>
        <FuneralTransportationContent />
      </Suspense>
    </PageContainer>
  );
};

export default FuneralTransportation;
