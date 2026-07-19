import Button from '@/components/core/button/Button';
import { SidebarDetail } from '@/components/core/faq/SidebarDetail';
import PageContainer from '@/components/core/page-container/PageContainer';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import ArrowLeftIcon from '@/components/icons/ArrowLeftIcon';
import { getInquiryById } from '@/data/inquiry/inquiry.api';
import { ContentDetail } from '@/pages/announcement/detail/components/ContentDetail';
import { ROUTES } from '@/router/routes.constant';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { useQuery } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';
import { useNavigate, useParams } from 'react-router-dom';
import { OverviewDetail } from './component/OverviewDetail';

const InquiryDetailPage = PermissionAccept((): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const navigate = useNavigate();
  const { id } = useParams();
  const { data: inquiries } = useQuery({
    queryKey: [QUERY_KEYS.INQUIRIES, id],
    queryFn: () => getInquiryById({ id: +id! }),
    enabled: !!id,
  });

  return (
    <PageContainer title={tFaq('inquiry_detail_title')} isFullScreen withFooter className='pb-0'>
      <div className='px-4 md:px-4 max-w-[1440px] w-full '>
        <div className=' flex flex-row justify-between  pt-12 xl:px-52 pb-12 xl:pb-6 '>
          <OverviewDetail
            subject={inquiries?.data.subject}
            detail={inquiries?.data.detail}
            answerStatus={inquiries?.data.status}
            status={inquiries?.data.answerStatus}
            updatedAt={inquiries?.data.updatedAt}
          />
        </div>
        <div className='flex lg:flex-row  flex-col-reverse gap-6  pt-0 xl:px-52 md:pb-24 '>
          <SidebarDetail attachments={inquiries?.data.attachment} />
          <ContentDetail
            mainContent={inquiries?.data?.answer && <div dangerouslySetInnerHTML={{ __html: inquiries?.data.answer }} />}
          />
        </div>
        <div className='flex justify-center gap-10 pt-6 md:px-52 pb-12 '>
          <Button
            onClick={() => navigate(ROUTES.INQUIRY.self)}
            className='py-2.5 px-24  flex justify-center md:px-20 rounded-lg bg-primary-500 sizing-md'
            variant='primary'
            leftIcon={<ArrowLeftIcon />}>
            <span className='text-white text-base font-medium'>{tFaq('announcementDetail.buttonToList')}</span>
          </Button>
        </div>
      </div>
    </PageContainer>
  );
});

export default InquiryDetailPage;
