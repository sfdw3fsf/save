'use client';
import PageContainer from '@/components/common/page-container/PageContainer';
import PageTitleBanner from '@/components/common/page-title-banner/PageTitleBanner';
import '@/components/funeral/funeral.css';
import FuneralAfter from '@/components/funeral/FuneralAfter';
import FuneralExpense from '@/components/funeral/FuneralExpense';
import FuneralProcess from '@/components/funeral/FuneralProcess';
import FuneralSpecialService from '@/components/funeral/FuneralSpecialService';
import { useSearchParams } from 'next/navigation';
import { Suspense, useEffect } from 'react';

const FuneralPageContent = () => {
  const searchParams = useSearchParams();

  useEffect(() => {
    const idParam = searchParams.get('id')?.toString();
    if (idParam) {
      const element = document.getElementById(idParam);
      if (element) {
        window.scrollBy({
          top: element.offsetTop - window.scrollY + 8,
          behavior: 'smooth',
        });
      }
    }
  }, [searchParams]);

  return (
    <>
      <PageTitleBanner
        title='장례 서비스'
        subTitle={
          <>
            진실된 마음으로 보호자님과 반려동물을 위로하고, 정직한 손길로 믿을 수 있는 장례 의전을
            <br />
            진행합니다.
          </>
        }
        img='/images/funeral/funeral-top-banner.jpg'
      />
      <FuneralProcess />
      <FuneralAfter />
      <FuneralExpense />
      <FuneralSpecialService />
    </>
  );
};

const FuneralPage = () => (
  <PageContainer>
    <Suspense>
      <FuneralPageContent />
    </Suspense>
  </PageContainer>
);

export default FuneralPage;
