import CustomText from '@/components/common/Text';
import { Legend } from '@/utils/types/legend.type';

type StatisticPieLegendProps = Legend;

const StatisticLegendItem = ({ label, value, color }: StatisticPieLegendProps) => {
  return (
    <div className='flex gap-1'>
      <div className='size-3 rounded-full shrink-0' style={{ backgroundColor: color }} />
      <CustomText variant='caption-1' className='!font-semibold'>
        <CustomText variant='caption-1'>{label}</CustomText>
        {` ` + value}
      </CustomText>
    </div>
  );
};

export default StatisticLegendItem;
