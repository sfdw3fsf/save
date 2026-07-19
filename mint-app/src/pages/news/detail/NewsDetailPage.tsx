import Button from '@/components/core/button/Button';
import Divider from '@/components/core/divider/Divider';
import PageContainer from '@/components/core/page-container/PageContainer';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import ArrowLeftIcon from '@/components/icons/ArrowLeftIcon';
import LinkIcon from '@/components/icons/LinkIcon';
import ScheduleIcon from '@/components/icons/ScheduleIcon';
import { getNewsAPI } from '@/data/news/news.api';
import { ROUTES } from '@/router/routes.constant';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { toLocaleDateString } from '@/utils/helpers/datetime.helper';
import { useQuery } from '@tanstack/react-query';
import { useCopyToClipboard } from '@uidotdev/usehooks';
import { enqueueSnackbar } from 'notistack';
import { useTranslation } from 'react-i18next';
import { useNavigate, useParams } from 'react-router-dom';
import DownloadItem from '../components/DownloadItem';

function normalizeBrTags(html: string) {
  return html.replace(/<br\s*\/?>/g, '<div style="height:10px""></div>');
}

const NewsDetailPage = PermissionAccept(() => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { t: tFaq } = useTranslation('faq');
  const [copiedText, copyToClipboard] = useCopyToClipboard();
  const { data: news } = useQuery({
    queryKey: [QUERY_KEYS.NEWS, id],
    queryFn: () => getNewsAPI({ id: +id! }),
    enabled: !!id,
  });

  return (
    <PageContainer title={tFaq('new_detail_title')} isFullScreen hiddenScroll withFooter className='bg-slate-50'>
      <div className='flex flex-col  w-full justify-center items-center'>
        <div className=' pt-16 md:pb-12 pb-12 flex lg:flex-row flex-col justify-between max-w-[1216px] max-lg:gap-10 w-full max-xl:px-4 '>
          <div className='flex flex-col gap-6 '>
            <div
              className='newEdit test'
              dangerouslySetInnerHTML={{
                __html: news?.data.titleContent ? news?.data.titleContent : '',
              }}
            />

            <div dangerouslySetInnerHTML={{ __html: news?.data?.shortDescription ?? '' }}></div>
            <div className='flex flex-row gap-2 items-center'>
              <span>
                <ScheduleIcon className='text-comp-disabled' />
              </span>
              <span className='text-base text-comp-disabled'>
                {' '}
                {tFaq('announcementDetail.postedOn', {
                  date: toLocaleDateString(news?.data.updatedAt ? new Date(news.data.updatedAt) : new Date()),
                })}
              </span>
            </div>
          </div>

          <Button
            className='border border-slate-200  rounded-lg h-fit  w-fit'
            rightIcon={<LinkIcon />}
            onClick={() => {
              copyToClipboard(window.location.href);
              enqueueSnackbar({ message: tFaq('copy_success'), variant: 'info' });
            }}>
            {tFaq('announcementDetail.copy')}
          </Button>
        </div>
        <div className='flex items-start gap-16 xl:px-52 md:px-10 px-4 md:pb-24 pb-12 justify-center'>
          <div className='flex md:flex-row flex-col-reverse  gap-6 w-full max-w-[1024px]'>
            <div className='flex flex-col rounded-lg p-6 gap-8 border bg-white h-fit w-full max-w-[280px]'>
              <div>
                <p className='text-md font-medium text-primary-500 mb-4'>{tFaq('announcementDetail.tableOfContentsTitle')}</p>
                <div className='flex flex-col gap-3'>
                  {news?.data?.tableOfContents?.map((titleContent, titleContentIndex) => (
                    <a
                      onClick={(e) => {
                        e.preventDefault();
                        const targetElement = document.querySelector(titleContent?.anchorValue);
                        if (targetElement) {
                          const yOffset = -80;
                          const yPosition = targetElement.getBoundingClientRect().top + window.scrollY + yOffset;
                          window.scrollTo({ top: yPosition, behavior: 'smooth' });
                        }
                      }}
                      href={titleContent?.anchorValue}
                      className='text-md font-normal text-slate-500'
                      key={titleContentIndex}>
                      {titleContent?.title}
                    </a>
                  ))}
                </div>
              </div>
              <Divider />
              <div className=''>
                <p className='text-md font-medium text-primary-500 mb-4'>{tFaq('announcementDetail.attachmentTitle')}</p>
                <div className='flex flex-col gap-3'>
                  {news?.data?.attachments?.map((attachment, attachmentIndex) => (
                    <DownloadItem attachement={attachment} key={attachmentIndex} />
                  ))}
                </div>
              </div>
            </div>
            {news?.data?.mainContent && (
              <div className='flex flex-col rounded-lg p-6 gap-8 border bg-white w-full'>
                <div
                  className='newEdit'
                  dangerouslySetInnerHTML={{
                    __html: news?.data.mainContent ? normalizeBrTags(news?.data.mainContent) : '',
                  }}
                />
              </div>
            )}
          </div>
        </div>
        <div className='flex pt-6 px-4 pb-12 justify-center'>
          <Button
            onClick={() => navigate(ROUTES.NEWS.self)}
            variant='primary'
            // leftIcon={<ArrowLeftIcon />}
            className='text-md font-medium sm:w-60 w-full '>
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

export default NewsDetailPage;
