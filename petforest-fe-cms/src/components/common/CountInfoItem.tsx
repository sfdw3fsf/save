import CustomText from '@/components/common/Text';

type CountInfoItemProps = {
  title: string;
  value: number | string;
  isSelected?: boolean;
  onSelectProcess?: () => void;
};
export const CountInfoItem = ({ title, value, isSelected, onSelectProcess }: CountInfoItemProps) => {
  return (
    <div
      onClick={onSelectProcess}
      className={`flex px-[12px] h-[40px] items-center justify-between min-w-[100px] md:min-w-[140px] bg-neutral-20 rounded-[8px] lg:h-[50px] cursor-pointer ${isSelected ? 'border-[1px] border-primary-900' : ''}`}>
      <CustomText variant='label-1-normal' className='mr-1.5'>
        {title}
      </CustomText>
      <CustomText variant='headline-1' className='!text-neutral-100'>
        {value}
      </CustomText>
    </div>
  );
};
