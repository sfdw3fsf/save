import Button from '@/components/core/button/Button';
import ChainsIcon from '@/components/icons/ChainsIcon';
import ScheduleIcon from '@/components/icons/ScheduleIcon';
import { toLocaleDateString } from '@/utils/helpers/datetime.helper';
import { useCopyToClipboard } from '@uidotdev/usehooks';
import { enqueueSnackbar } from 'notistack';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

type OverviewProps = {
  className?: string;
  title?: string;
  titleContent?: string;
  updatedAt?: number;
};

export const OverviewDetail = ({ className, title, titleContent, updatedAt }: OverviewProps): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const [copiedText, copyToClipboard] = useCopyToClipboard();

  return (
    <div className={twMerge('max-w-[1216px] w-full flex flex-col gap-12', className)}>
      <div className='flex flex-col  max-w-[1216px] justify-between md:flex-row  '>
        <div className='flex flex-col gap-6 max-w-[768px] w-full'>
          <div className='newEdit' dangerouslySetInnerHTML={{ __html: title ?? '' }}></div>
          <div dangerouslySetInnerHTML={{ __html: titleContent ?? '' }}></div>

          <div className='flex flex-row gap-2 items-center'>
            <span>
              <ScheduleIcon className='text-comp-disabled' />
            </span>
            <span className='text-base text-comp-disabled'>
              {tFaq('announcementDetail.postedOn', {
                date: toLocaleDateString(updatedAt ? new Date(updatedAt) : new Date()),
              })}
            </span>
          </div>
        </div>
        <div className='mt-10 max-lg:mb-10 md:mt-0'>
          <Button
            rightIcon={<ChainsIcon />}
            onClick={() => {
              copyToClipboard(window.location.href);
              enqueueSnackbar({ message: tFaq('copy_success'), variant: 'info' });
            }}>
            {tFaq('announcementDetail.copy')}
          </Button>
        </div>
      </div>
    </div>
  );
};
