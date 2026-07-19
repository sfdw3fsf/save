import LineChart from '@/components/charts/LineChart';
import CustomText from '@/components/common/Text';
import { helperFormatChartNumericData } from '@/utils/helpers/chart.helper';
import { Card, Flex } from 'antd';
import { useTranslation } from 'react-i18next';

type MonthlyCardProps = {
  total?: number;
  data: number[][];
  categories: string[];
  colors: string[];
  type: 'sale' | 'case';
};
const MonthlyCard = ({ total, data, categories, colors, type }: MonthlyCardProps) => {
  const { t: tDashboard } = useTranslation('dashboard');
  const { t: tCommon } = useTranslation('common');

  return (
    <Card className='p-6 flex-1 mt-4 min-w-[350px]'>
      <div className='flex justify-between'>
        <CustomText variant='heading-2'>{type === 'sale' ? tDashboard('monthly_sales') : tDashboard('monthly_cases')}</CustomText>
        <Flex wrap align='baseline' gap={4}>
          <CustomText variant='title-3' className='!text-primary-1000 whitespace-nowrap'>
            {helperFormatChartNumericData(total)}
          </CustomText>
          <CustomText variant='label-1-normal' className='!text-neutral-70'>
            {type === 'sale' ? tCommon('won') : tCommon('cases')}
          </CustomText>
        </Flex>
      </div>

      <LineChart
        data={data}
        categories={categories}
        dotColor={colors}
        lineColor={colors}
        tooltip={{
          y: {
            formatter: (value, { dataPointIndex, seriesIndex, series }) => {
              if (dataPointIndex === 0) return `${value}`;
              const previousValue = series[seriesIndex][dataPointIndex - 1];
              if (previousValue === 0) return `${value}`;
              const difference = value - previousValue;
              const percentage = ((difference / previousValue) * 100).toFixed(2);
              const sign = difference >= 0 ? '+' : '';
              return `${value} (${sign}${percentage}%)`;
            },
          },
        }}
      />
    </Card>
  );
};

export default MonthlyCard;
