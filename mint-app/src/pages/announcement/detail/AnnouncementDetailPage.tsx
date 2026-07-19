import Button from '@/components/core/button/Button';
import PageContainer from '@/components/core/page-container/PageContainer';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import ArrowLeftIcon from '@/components/icons/ArrowLeftIcon';
import { getAnnouncementById } from '@/data/announcement/announcement.api';
import { ROUTES } from '@/router/routes.constant';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { useQuery } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';
import { useNavigate, useParams } from 'react-router-dom';
import { SidebarDetail } from '../../../components/core/faq/SidebarDetail';
import { ContentDetail } from './components/ContentDetail';
import { OverviewDetail } from './components/OverviewDetail';

const AnnouncementDetailPage = PermissionAccept((): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const { id } = useParams();
  const { data: announcements } = useQuery({
    queryKey: [QUERY_KEYS.ANNOUNCEMENTS, id],
    queryFn: () => getAnnouncementById({ id: +id! }),
    enabled: !!id,
  });
  const navigate = useNavigate();
  return (
    <PageContainer title={tFaq('announcement_detail_title')} isFullScreen withFooter className='pb-0'>
      <div className='max-xl:px-4  max-w-[1440px] w-full'>
        <div className=' flex flex-row   w-full pt-16  xl:pb-24 md:pb-12 xl:px-28 '>
          <OverviewDetail
            title={announcements?.data.title}
            titleContent={announcements?.data.titleContent}
            updatedAt={announcements?.data.updatedAt}
          />
        </div>
        <div className='flex lg:flex-row  flex-col-reverse pt-0 xl:px-52 md:pb-24 gap-6 md:px-10'>
          <SidebarDetail
            isTableContent
            attachments={announcements?.data.attachments}
            tableOfContents={announcements?.data.tableOfContents}
          />
          <ContentDetail
            mainContent={
              announcements?.data?.mainContent && (
                <div className='newEdit' dangerouslySetInnerHTML={{ __html: announcements?.data.mainContent }} />
              )
            }
          />
        </div>
        <div className='flex justify-center gap-10 pt-6 md:px-52 pb-12 '>
          <Button
            onClick={() => navigate(ROUTES.ANNOUNCEMENT.self)}
            className='max-w-[240px] w-full  rounded-lg bg-primary-500 '
            variant='primary'
            // leftIcon={}
          >
            <div className='text-white text-base font-medium w-full flex justify-center items-center flex-row gap-2'>
              <ArrowLeftIcon className='text-white' />
              {tFaq('announcementDetail.buttonToList')}
            </div>
          </Button>
        </div>
      </div>
    </PageContainer>
  );
});

export default AnnouncementDetailPage;
