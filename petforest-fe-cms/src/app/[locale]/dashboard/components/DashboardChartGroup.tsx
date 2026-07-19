'use client';
import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomText from '@/components/common/Text';
import {
  dashboardLineChartKeys,
  dashboardPieChartKeys,
  getDashboardLineChartApi,
  getDashboardPieChartApi,
} from '@/store/dashboard/dashboard.api';
import { dashboardLineChartRespToDto, dashboardPieChartItemRespToDto } from '@/store/dashboard/dashboard.service';
import { DashboardChartREQ } from '@/store/dashboard/request/dashboard.request';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import dayjs, { Dayjs } from 'dayjs';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import BranchCard from './BranchCard';
import MonthlyCard from './MonthlyCard';

const DashboardChartGroup = () => {
  const { t: tDashboard } = useTranslation('dashboard');
  const { t: tCommon } = useTranslation('common');

  const [chartDate, setChartDate] = useState<Dayjs | null>(dayjs());

  const chartDateRequest = useMemo<DashboardChartREQ | null>(() => {
    if (!chartDate) return null;
    const [year, month] = chartDate.format('YYYY-MM').split('-');
    return { year, month };
  }, [chartDate]);

  const { data: pieChartData } = useQuery({
    queryKey: dashboardPieChartKeys.list(chartDateRequest!),
    queryFn: () => getDashboardPieChartApi(chartDateRequest!),
    select: ({ data }) => {
      return data?.data && data?.data.sort((a, b) => a.branchId - b.branchId)?.map((i) => dashboardPieChartItemRespToDto(i));
    },
    enabled: chartDateRequest !== null,
    placeholderData: keepPreviousData,
  });

  const { data: lineChartData } = useQuery({
    queryKey: dashboardLineChartKeys.list(chartDateRequest!),
    queryFn: () => getDashboardLineChartApi(chartDateRequest!),
    select: ({ data }) => {
      return data?.data && dashboardLineChartRespToDto(data?.data);
    },
    enabled: chartDateRequest !== null,
    placeholderData: keepPreviousData,
  });

  const lineCategories = useMemo(() => {
    return lineChartData?.lineCategories || [];
  }, [lineChartData?.lineCategories]);

  const colors = useMemo(() => {
    return lineChartData?.lineData?.map((i) => i.color) || [];
  }, [lineChartData?.lineData]);

  const lineFuneralData = useMemo(() => {
    return lineChartData?.lineData?.map((i) => i.lineDataFuneral) || [];
  }, [lineChartData?.lineData]);

  const lineSalesData = useMemo(() => {
    return lineChartData?.lineData?.map((i) => i.lineDataSales) || [];
  }, [lineChartData?.lineData]);

  return (
    <>
      <div className='flex items-center gap-4 mt-[40px]'>
        <CustomText variant='title-3'>{tDashboard('funeral_and_sales')}</CustomText>
        <CustomDatePicker allowClear={false} picker='month' onChange={setChartDate} value={chartDate} />
      </div>

      <div className='mt-[40px] flex gap-8 flex-col md:flex-row flex-wrap'>
        {pieChartData?.map((item, index) => <BranchCard key={index} data={item} />)}
      </div>

      <div className='mt-[40px] flex gap-6 flex-wrap'>
        {lineChartData?.lineData?.map((item, index) => (
          <div className='flex items-center gap-2' key={index}>
            <div className='size-[14px] shrink-0 rounded' style={{ backgroundColor: item.color }} />
            <CustomText variant='label-1-normal'>{item.branchName}</CustomText>
          </div>
        ))}
      </div>

      <div className='flex gap-8 flex-wrap'>
        <MonthlyCard
          total={lineChartData?.totalSalesData}
          data={lineSalesData}
          categories={lineCategories}
          colors={colors}
          type='sale'
        />
        <MonthlyCard
          total={lineChartData?.totalFuneralData}
          data={lineFuneralData}
          categories={lineCategories}
          colors={colors}
          type='case'
        />
      </div>
    </>
  );
};

export default DashboardChartGroup;
