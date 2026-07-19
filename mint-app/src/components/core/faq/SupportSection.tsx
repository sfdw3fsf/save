import Button from '@/components/core/button/Button';
import ArrowRightIcon from '@/components/icons/ArrowRightIcon';
import MailIcon from '@/components/icons/MailIcon';
import PhoneIcon from '@/components/icons/PhoneIcon';
import { FORM_POSITION } from '@/pages/inquiry/InquiryPage';
import { ROUTES } from '@/router/routes.constant';
import { PARAM_SCROLL_POSITION } from '@/utils/constants/data.constant';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import { InquiryAssistant } from './InquiryAssistant';
import { PhoneAssistant } from './PhoneAssistant';

interface SupportSectionProps {
  className?: string;
}
export const SupportSection = ({ className }: SupportSectionProps): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const navigate = useNavigate();

  return (
    <div className='w-full h-full flex flex-col items-center gap-10 mt-10 mb-12'>
      <h1 className='text-xl md:text-3xl font-medium'>{tFaq('stillHaveQuestion')}</h1>

      <div className={twMerge('flex flex-col md:flex-row w-full gap-4 max-w-[768px] ', className)}>
        <PhoneAssistant
          icon={<PhoneIcon className='text-primary-500 w-4 h-full items-center flex mb-2' />}
          title={tFaq('telephoneInquiry')}
          email={'general@mintmd.bio'}
          timeType={tFaq('operatingTime')}
          time={'9:00 - 18:00'}
        />

        <InquiryAssistant
          icon={<MailIcon className='text-primary-500' />}
          title={tFaq('customerServiveCenter')}
          content={`1:1 ${tFaq('inquiry')}`}
          button={
            <Button
              onClick={() => {
                navigate(`${ROUTES.INQUIRY.self}?${PARAM_SCROLL_POSITION}=${FORM_POSITION}`);
              }}
              sizing='2sm'
              className='bg-primary-500 py-1 px-2 '
              variant='primary'
              rightIcon={<ArrowRightIcon />}>
              <span className='text-white text-sm'>{tFaq('goTo')}</span>
            </Button>
          }
          timeInfor={tFaq('available_24_hours')}
          className='h-full '
        />
      </div>
    </div>
  );
};
