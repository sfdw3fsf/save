import CustomText from '@/components/common/Text';
import { useTranslation } from 'react-i18next';

type StatisticTotalProps = {
  title: string;
  value?: string | number;
  type?: 'small' | 'medium' | 'large';
  color?: string;
  percent?: number;
  totalColor?: string;
  totalUnit?: 'cases' | 'won';
};
const StatisticTotal = ({
  title,
  value,
  type = 'small',
  color,
  percent,
  totalColor,
  totalUnit = 'cases',
}: StatisticTotalProps) => {
  const { t: tStatistic } = useTranslation('statistic');

  return (
    <div className='flex flex-col min-w-[64px] '>
      <div className='flex gap-1 items-center'>
        {color && <div className={`w-3 h-3 rounded-full`} style={{ backgroundColor: color }} />}
        <CustomText variant='label-1-normal' className='!font-medium'>
          {title}
        </CustomText>
      </div>
      <CustomText
        variant={type === 'small' ? 'title-3' : 'title-1'}
        className={`${color ? '!text-neutral-strong' : !totalColor && '!text-blue-600'}`}
        style={{ color: totalColor }}>
        {value || 0}
        {tStatistic(totalUnit as any)}
      </CustomText>

      {percent && (
        <div className='flex self-start px-2 py-[2px] bg-neutral-20 rounded-lg'>
          <CustomText variant='label-1-normal'>{percent}%</CustomText>
        </div>
      )}
    </div>
  );
};

export default StatisticTotal;
