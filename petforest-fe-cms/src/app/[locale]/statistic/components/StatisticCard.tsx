import DownloadButton from '@/components/common/DownloadButton';
import CustomText from '@/components/common/Text';
import { ReactNode } from 'react';

type StatisticCard = {
  children: ReactNode;
  className?: string;
  title?: string;
  customTitle?: ReactNode;
  isDownload?: boolean;
  onDownloadExcel?: () => void;
};
const StatisticCard = ({ children, className, title, customTitle, isDownload = true, onDownloadExcel }: StatisticCard) => {
  return (
    <div className={`flex-1 border-[1px] border-neutral-30 rounded-lg p-6 max-md:p-3 ${className}`}>
      <div className='flex justify-between'>
        {customTitle && customTitle}
        {title && <CustomText variant='heading-2'>{title}</CustomText>}
        {isDownload && <DownloadButton onClick={onDownloadExcel} />}
      </div>
      {children}
    </div>
  );
};

export default StatisticCard;
