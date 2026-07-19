import '@/components/charnel-house-supplies/charnel-house-supplies.css';
import CharnelHouseSuppliesContent from '@/components/charnel-house-supplies/CharnelHouseSuppliesContent';
import PageContainer from '@/components/common/page-container/PageContainer';
import PageTitleBanner from '@/components/common/page-title-banner/PageTitleBanner';

const CharnelHouseSuppliesPage = () => {
  return (
    <PageContainer>
      <PageTitleBanner
        title='기억의 정원'
        subTitle='우리 아이의 마지막을 조금 더 오래 기억할 수 있도록, 하나하나 정성을 다해 준비했습니다. 펫포레스트가 마련한 푸른 정원
            속에서 내내 평안하기를 바랍니다.'
        img='/images/charnel-house/charnel-house-banner.jpg'
      />
      <CharnelHouseSuppliesContent />
    </PageContainer>
  );
};

export default CharnelHouseSuppliesPage;
