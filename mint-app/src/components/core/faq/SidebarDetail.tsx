import FileCard from '@/components/core/file-card/FileCard';
import DownloadIcon from '@/components/icons/DownloadIcon';
import { useDownloadLink } from '@/hooks/useDownloadLink';
import { ANNOUNCEMENT_ATTACHMENT_TYPE } from '@/utils/enums/data/announcement-type.enum';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

type Attachment = {
  key: string;
  name: string;
  size: number;
  type: ANNOUNCEMENT_ATTACHMENT_TYPE;
  url: string;
};

type SidebarProps = {
  className?: string;
  isTableContent?: boolean;
  attachments?: Attachment[];
  tableOfContents?: any;
};

export const SidebarDetail = ({ className, isTableContent = false, attachments, tableOfContents }: SidebarProps): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const onDownload = useDownloadLink;

  return (
    <div
      className={twMerge(
        'lg:max-w-[280px] w-full bg-white h-fit p-6 py-12 md:py-6 rounded-lg border border-slate-200 flex flex-col gap-8',
        className,
      )}>
      {isTableContent && (
        <div className='flex flex-col gap-4 pb-6 border-b border-slate-200'>
          <p className='font-medium text-base text-primary-500'>{tFaq('announcementDetail.tableOfContentsTitle')}</p>
          <div className='flex flex-col gap-3'>
            <div className='flex flex-col gap-3'>
              {tableOfContents?.map((titleContent: any, titleContentIndex: any) => (
                <a
                  onClick={(e) => {
                    e.preventDefault();
                    const targetElement = document.querySelector(titleContent?.anchorValue);
                    if (targetElement) {
                      const yOffset = -80;
                      const yPosition = targetElement.getBoundingClientRect().top + window.scrollY + yOffset;
                      window.scrollTo({ top: yPosition });
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
        </div>
      )}
      <div className='flex flex-col gap-4'>
        <p className='text-base font-medium text-primary-500'>{tFaq('announcementDetail.attachmentTitle')}</p>
        <div className='flex flex-col gap-3'>
          {!!attachments &&
            attachments?.length > 0 &&
            attachments.map((attachment: any, index: number) => (
              <FileCard
                key={index}
                file={attachment}
                buttonProps={{
                  icon: <DownloadIcon />,
                  variant: 'greyGhost',
                  sizing: 'xs',
                  onClick: () => {
                    onDownload(attachment.url, attachment.name);
                  },
                }}
              />
            ))}
        </div>
      </div>
    </div>
  );
};
