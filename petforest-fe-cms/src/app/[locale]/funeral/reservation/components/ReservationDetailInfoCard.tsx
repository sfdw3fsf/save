import CustomText from '@/components/common/Text';
import { Card } from 'antd';
import { ReactNode } from 'react';

type MFS_InfoCardProps = {
  title: string;
  value: ReactNode;
  className?: string;
};
const ReservationDetailInfoCard = ({ title, value, className }: MFS_InfoCardProps) => {
  return (
    <Card className={`p-3 flex flex-col min-w-[300px] ${className}`}>
      <div>
        <CustomText variant='label-1-normal'>{title}</CustomText>
      </div>
      <div className='mt-[6px]'>{value}</div>
    </Card>
  );
};

export default ReservationDetailInfoCard;
