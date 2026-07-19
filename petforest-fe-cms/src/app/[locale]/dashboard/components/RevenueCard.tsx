import CustomText from '@/components/common/Text';
import { helperFormatChartNumericData } from '@/utils/helpers/chart.helper';
import { Card, Flex } from 'antd';
import { useTranslation } from 'react-i18next';

type RevenueCardProps = {
  totalNumberSales?: number;
  averageNumberSales?: number;
  totalSales?: number;
  averageSales?: number;
};
const RevenueCard = ({ totalNumberSales, averageNumberSales, totalSales, averageSales }: RevenueCardProps) => {
  const { t: tDashboard } = useTranslation('dashboard');
  const { t: tCommon } = useTranslation('common');

  return (
    <Card className={`p-6`}>
      <div className='flex gap-[40px] self-start flex-wrap'>
        <div className='flex flex-col gap-3 min-w-[186px]'>
          <CustomText variant='label-1-normal'>{tDashboard('total_funeral')}</CustomText>
          <Flex wrap align='baseline' gap={4}>
            <CustomText variant='title-3' className='!text-primary-1000 whitespace-nowrap'>
              {helperFormatChartNumericData(totalNumberSales)}
            </CustomText>
            <CustomText variant='label-1-normal' className='!text-neutral-70'>
              {tCommon('cases')}/
            </CustomText>
            <CustomText variant='heading-2' className='!text-primary-800 whitespace-nowrap'>
              {helperFormatChartNumericData(averageNumberSales)}
            </CustomText>
            <CustomText variant='label-1-normal' className='!text-neutral-70'>
              {tCommon('cases')}
            </CustomText>
          </Flex>
        </div>
        <div className='flex flex-col gap-3 min-w-[186px]'>
          <CustomText variant='label-1-normal'>{tDashboard('total_sales')}</CustomText>
          <Flex wrap align='baseline' gap={4}>
            <CustomText variant='title-3' className='!text-primary-1000 whitespace-nowrap'>
              {helperFormatChartNumericData(totalSales)}
            </CustomText>
            <CustomText variant='label-1-normal' className='!text-neutral-70'>
              {tCommon('won')}/
            </CustomText>
            <CustomText variant='heading-2' className='!text-primary-800 whitespace-nowrap'>
              {helperFormatChartNumericData(averageSales)}
            </CustomText>
            <CustomText variant='label-1-normal' className='!text-neutral-70'>
              {tCommon('won')}
            </CustomText>
          </Flex>
        </div>
      </div>
    </Card>
  );
};

export default RevenueCard;
