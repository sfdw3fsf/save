import CustomText from '@/components/common/Text';

export const FRD_TitleSectionFormTitle = ({ title }: { title: string }) => {
  return (
    <div className='w-full bg-primary-1000 text-center rounded-lg py-1.5 px-2'>
      <CustomText variant='headline-2' className='!text-white leading-[24px] !font-semibold'>
        {title}
      </CustomText>
    </div>
  );
};
