'use client';
import CustomDatePicker from '@/components/common/CustomDatePicker';
import {
  dashboardReceivablesKeys,
  dashboardSummaryKeys,
  getDashboardReceivablesAPI,
  getDashboardSummaryApi,
} from '@/store/dashboard/dashboard.api';
import { DashboardSummaryMonthREQ, DashboardSummaryYearREQ } from '@/store/dashboard/request/dashboard.request';
import { DownOutlined } from '@ant-design/icons';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import dayjs, { Dayjs } from 'dayjs';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import ReceivableCard from './ReceivableCard';
import RevenueCard from './RevenueCard';

const DashboardSummary = ({ onChangeExcelFilter }: { onChangeExcelFilter: (date: Dayjs | null) => void }) => {
  const { i18n } = useTranslation();

  const [summaryDate, setSummaryDate] = useState<Dayjs | null>(dayjs());

  const summaryMonthDateRequest = useMemo<DashboardSummaryMonthREQ | null>(() => {
    if (!summaryDate) return null;
    const [year, month] = summaryDate.format('YYYY-MM').split('-');
    return { year, month, filterBy: 'month' };
  }, [summaryDate]);

  const summaryYearDateRequest = useMemo<DashboardSummaryYearREQ | null>(() => {
    if (!summaryDate) return null;
    const year = summaryDate.format('YYYY');
    return { year, filterBy: 'year' };
  }, [summaryDate]);

  const { data: summaryYearData } = useQuery({
    queryKey: dashboardSummaryKeys.list(summaryYearDateRequest!),
    queryFn: () => getDashboardSummaryApi(summaryYearDateRequest!),
    select: ({ data }) => {
      return data.data;
    },
    enabled: summaryYearDateRequest !== null,
    placeholderData: keepPreviousData,
  });

  const { data: summaryMonthData } = useQuery({
    queryKey: dashboardSummaryKeys.list(summaryMonthDateRequest!),
    queryFn: () => getDashboardSummaryApi(summaryMonthDateRequest!),
    select: ({ data }) => {
      return data.data;
    },
    enabled: summaryMonthDateRequest !== null,
    placeholderData: keepPreviousData,
  });

  const { data: receivablesData } = useQuery({
    queryKey: dashboardReceivablesKeys.list(summaryMonthDateRequest!),
    queryFn: () =>
      getDashboardReceivablesAPI({
        year: summaryMonthDateRequest!.year,
        month: summaryMonthDateRequest!.month,
      }),
    select: ({ data }) => {
      return data.data;
    },
    enabled: summaryMonthDateRequest !== null,
    placeholderData: keepPreviousData,
  });

  const formatSummaryDate = (picker: 'year' | 'month', language: string) => {
    switch (picker) {
      case 'year': {
        switch (language) {
          case 'en':
            return 'YYYY';
          case 'ko':
            return 'YYYY년';
        }
        return;
      }
      case 'month': {
        switch (language) {
          case 'en':
            return 'MMM';
          case 'ko':
            return 'MM월';
        }
        return;
      }
    }
  };

  useEffect(() => {
    onChangeExcelFilter(summaryDate);
  }, [summaryDate]);

  return (
    <div className='flex gap-4 flex-wrap mt-8'>
      <div className='flex-1 flex flex-col gap-4'>
        <CustomDatePicker
          allowClear={false}
          picker='year'
          suffixIcon={<DownOutlined className='text-primary-1000' />}
          classNameInput='rounded-full text-primary-1000 border-primary-1000 !h-auto !w-[97px]'
          format={formatSummaryDate('year', i18n.language)}
          value={summaryDate}
          onChange={setSummaryDate}
        />
        <RevenueCard
          totalNumberSales={summaryYearData?.totalFuneral}
          averageNumberSales={summaryYearData?.averageFuneralByDay}
          totalSales={summaryYearData?.totalSales}
          averageSales={summaryYearData?.averageSalesByDay}
        />
      </div>
      <div className='flex-1 flex flex-col gap-4'>
        <CustomDatePicker
          allowClear={false}
          picker='month'
          suffixIcon={<DownOutlined className='text-primary-1000' />}
          classNameInput='rounded-full text-primary-1000 border-primary-1000 !h-auto !w-[97px]'
          format={formatSummaryDate('month', i18n.language)}
          value={summaryDate}
          onChange={setSummaryDate}
        />
        <RevenueCard
          totalNumberSales={summaryMonthData?.totalFuneral}
          averageNumberSales={summaryMonthData?.averageFuneralByDay}
          totalSales={summaryMonthData?.totalSales}
          averageSales={summaryMonthData?.averageSalesByDay}
        />
      </div>

      <div className='flex-1 flex items-end'>
        <ReceivableCard total={receivablesData} />
      </div>
    </div>
  );
};

export default DashboardSummary;
