import { JOURNAL_TEXT } from '@/utils/constant/customer-service/customer-service.constant';
import { JournalInfoBannerType } from '@/utils/types/journal-banner.type';

type JournalInfoBannerProps = JournalInfoBannerType;

const JournalInfoBanner = ({ image = '', category = null, text = '', id }: JournalInfoBannerProps) => {
  return (
    <a href={`/about/journal/journal-detail/${id}`}>
      <img alt={`banner-journal-detail-${id}`} src={image} loading='lazy' />
      <h6 className='text-[13px] md:mt-[16px] md:text-[14px] whitespace-nowrap'>
        {JOURNAL_TEXT[category as keyof typeof JOURNAL_TEXT]}
      </h6>
      <h3 className='text-[14px] text-grey-green font-semibold mt-[17px] md:text-[16px]  md:mt-2'>{text}</h3>
    </a>
  );
};

export default JournalInfoBanner;
