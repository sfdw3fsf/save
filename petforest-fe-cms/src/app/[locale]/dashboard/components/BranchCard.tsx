import PieChart from '@/components/charts/PieChart';
import CustomText from '@/components/common/Text';
import { DashboardPieChartItemDTO } from '@/store/dashboard/dto/dashboard.dto';
import { helperFormatChartNumericData } from '@/utils/helpers/chart.helper';
import { Legend } from '@/utils/types/legend.type';
import { Card, Divider, Flex } from 'antd';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import StatisticLegendItem from '../../statistic/components/StatisticLegendItem';

type BranchCardProps = {
  data: DashboardPieChartItemDTO;
};

const BranchCard = ({ data }: BranchCardProps) => {
  const { t: tDashboard } = useTranslation('dashboard');
  const { t: tEnum } = useTranslation('enum');
  const { t: tCommon } = useTranslation('common');

  const legends = useMemo<Legend[]>(() => {
    return data.pieChartData.map((i) => ({
      label: i.label,
      value: tDashboard('case', { count: i.value }),
      color: i.color,
    }));
  }, [data.pieChartData, tDashboard]);

  const series = useMemo<number[]>(() => {
    return data.pieChartData.map((i) => i.value);
  }, [data.pieChartData]);

  return (
    <Card className='p-6 flex-1 md:min-w-[486px]'>
      <div className='px-[14px] py-2 rounded-full w-fit' style={{ backgroundColor: data.branchColor }}>
        <CustomText variant='headline-1' className='!text-white'>
          {data.branchName}
        </CustomText>
      </div>

      <div className='mt-6 flex gap-[40px] flex-wrap'>
        <div className='flex-1 flex-col min-w-[186px]'>
          <CustomText variant='label-1-normal'>{tDashboard('funeral_average')}</CustomText>
          <Flex wrap align='baseline' gap={4}>
            <CustomText variant='title-3' className='!text-primary-1000 whitespace-nowrap'>
              {helperFormatChartNumericData(data.totalFuneral)}
            </CustomText>
            <CustomText variant='label-1-normal' className='!text-neutral-70'>
              {tCommon('cases')}/
            </CustomText>
            <CustomText variant='heading-2' className='!text-primary-800 whitespace-nowrap'>
              {helperFormatChartNumericData(data.averageFuneralByDay)}
            </CustomText>
            <CustomText variant='label-1-normal' className='!text-neutral-70'>
              {tCommon('cases')}
            </CustomText>
          </Flex>
        </div>

        <div className='flex-1 flex-col min-w-[186px]'>
          <CustomText variant='label-1-normal'>{tDashboard('take')}</CustomText>
          <Flex wrap align='baseline' gap={4}>
            <CustomText variant='title-3' className='!text-primary-1000 whitespace-nowrap'>
              {helperFormatChartNumericData(data.totalSalesAmount)}
            </CustomText>
            <CustomText variant='label-1-normal' className='!text-neutral-70'>
              {tCommon('won')}
            </CustomText>
          </Flex>
        </div>
      </div>

      <Divider className='my-6' />

      <CustomText variant='caption-1' className='!text-neutral-70'>
        {tDashboard('funeral_detail')}
      </CustomText>

      <div className='flex items-center gap-10 max-sm:flex-col'>
        <PieChart showEmpty data={series} categories={data.pieChartData} width={218} />
        {!!legends.length && (
          <div className='flex flex-col gap-2'>
            {legends.map((item, index) => (
              <StatisticLegendItem key={index} label={item.label} value={item.value} color={item.color} />
            ))}
          </div>
        )}
      </div>

      <Divider className='my-6' />

      <CustomText variant='caption-1' className='!text-neutral-70'>
        {tDashboard('sale_detail')}
      </CustomText>

      <div className='flex gap-[40px]'>
        <div className='flex flex-col'>
          <CustomText variant='caption-1' className='!font-semibold'>
            <CustomText variant='caption-1'>･ {tDashboard('funeral_sales')}</CustomText>
            {` ` + helperFormatChartNumericData(data.funeralSales) + ` (${data.funeralSalesPercent.toFixed(2)}%)`}
          </CustomText>
          <CustomText variant='caption-1' className='!font-semibold'>
            <CustomText variant='caption-1'>･ {tDashboard('bongandang_sales')}</CustomText>
            {` ` + helperFormatChartNumericData(data.bongandangSales) + ` (${data.bongandangSalesPercent.toFixed(2)}%)`}
          </CustomText>
        </div>
        <div className='flex flex-col'>
          <CustomText variant='caption-1' className='!font-semibold'>
            <CustomText variant='caption-1'>･ {tDashboard('other_sales')}</CustomText>
            {` ` + helperFormatChartNumericData(data.otherSales) + ` (${data.otherSalesPercent.toFixed(2)}%)`}
          </CustomText>
          <CustomText variant='caption-1' className='!font-semibold'>
            <CustomText variant='caption-1'>･ {tDashboard('receivable')}</CustomText>
            {` ` + helperFormatChartNumericData(data.receivable) + ` (${data.receivablePercent.toFixed(2)}%)`}
          </CustomText>
        </div>
      </div>
    </Card>
  );
};

export default BranchCard;
