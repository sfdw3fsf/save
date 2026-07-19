import CustomText from '@/components/common/Text';

type CountInfoItemProps = {
  title: string;
  value: number | string;
};
export const CDPM_SummaryCountInfoItem = ({ title, value }: CountInfoItemProps) => {
  return (
    <div className='flex flex-col px-[12px] p-2 md:items-center justify-between md:min-w-[140px] md:flex-row bg-neutral-20 rounded-[8px] whitespace-nowrap'>
      <CustomText variant='label-1-normal' className='mr-1.5 whitespace-nowrap'>
        {title}
      </CustomText>
      <CustomText variant='headline-1' className='!text-neutral-100'>
        {value}
      </CustomText>
    </div>
  );
};
