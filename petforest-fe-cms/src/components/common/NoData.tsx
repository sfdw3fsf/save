import Image from 'next/image';
import CustomText from './Text';

type NoDataProps = {
  label: string;
  className?: string;
};

export const NoData = ({ label, className }: NoDataProps) => {
  return (
    <div className={`flex items-center flex-col gap-2 ${className}`}>
      <Image src='/images/noData.svg' alt='noDta' width={120} height={120} />
      <CustomText variant='label-1-normal' className='!text-neutral-80'>
        {label}
      </CustomText>
    </div>
  );
};
