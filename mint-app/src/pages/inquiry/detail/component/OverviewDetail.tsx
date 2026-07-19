import Badge from '@/components/core/badge/Badge';
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
  subject?: any;
  detail?: string;
  answerStatus?: string;
  status?: string;
  updatedAt?: number;
};

export const OverviewDetail = ({ subject, detail, answerStatus, status, className, updatedAt }: OverviewProps): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const [copiedText, copyToClipboard] = useCopyToClipboard();
  return (
    <div
      className={twMerge(
        'max-w-[1024px] w-full flex flex-col gap-12 bg-white rounded-lg p-6 border border-slate-200',
        className,
      )}>
      <div className='flex flex-col justify-between md:flex-row '>
        <div className='flex flex-col gap-6 max-w-[768px] w-full'>
          <h1 className='text-3xl font-medium'>
            <span className='text-slate-500 text-3xl pr-3'>Q</span>
            {subject}
          </h1>
          <p className='text-slate-500 font-normal text-lg md:text-xl '> {detail}</p>
          <div className='flex flex-row gap-2 items-center'>
            <span>
              <ScheduleIcon className='text-comp-disabled' />
            </span>
            <span className='text-base text-comp-disabled'>
              {' '}
              {tFaq('announcementDetail.postedOn', {
                date: toLocaleDateString(updatedAt ? new Date(updatedAt) : new Date()),
              })}
            </span>
          </div>
          <div className='flex flex-row gap-1'>
            <Badge color='neutral' shape='square-round' label={status} />
            <Badge shape='square-round' label={answerStatus} />
          </div>
        </div>
        <div className='mt-10 md:mt-0'>
          <Button
            onClick={() => {
              copyToClipboard(window.location.href);
              enqueueSnackbar({ message: tFaq('copy_success'), variant: 'info' });
            }}
            rightIcon={<ChainsIcon />}>
            {tFaq('announcementDetail.copy')}
          </Button>
        </div>
      </div>
    </div>
  );
};
