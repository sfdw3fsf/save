import PageContainer from '@/components/core/page-container/PageContainer';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import { useTranslation } from 'react-i18next';
import Advantage from './components/Advantage';
import Advantage1 from './components/Advantage1';
import Banner from './components/Banner';
import Board from './components/Board';
import Capabilities from './components/Capabilities';
import CarouselBanner from './components/CarouselBanner';
import CoreValue from './components/CoreValue';
import InquiryRequestButton from './components/InquiryRequestButton';
import News from './components/News';
import Partners from './components/Partners';
import Process from './components/Process';

const HomePage = PermissionAccept((): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  return (
    <PageContainer title={tCommon('title.home_page')} isFullScreen hiddenScroll withFooter className='bg-white'>
      <CarouselBanner />
      <Banner />
      <Advantage />
      <Advantage1 />
      <Capabilities />
      <CoreValue />
      <Process />
      <Board />
      <Partners />
      <News />
      <InquiryRequestButton />
    </PageContainer>
  );
});

export default HomePage;
