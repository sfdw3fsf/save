'use client';

import DownloadButton from '@/components/common/DownloadButton';
import PageTitle from '@/components/common/page/PageTitle';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { DashboardExcelREQ } from '@/store/dashboard/request/dashboard.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import dayjs, { Dayjs } from 'dayjs';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import DashboardChartGroup from './components/DashboardChartGroup';
import DashboardSummary from './components/DashboardSummary';

const page = () => {
  const { t: tDashboard } = useTranslation('dashboard');
  const [excelFilter, setExcelFilter] = useState<DashboardExcelREQ>({
    month: dayjs().month() + 1,
    year: dayjs().year(),
  });

  const { mutateDownloadExcel } = useDownloadExcel<DashboardExcelREQ>({
    fileName: EXCEL_FILE_NAME.DASHBOARD,
    url: EXCEL_URL.DASHBOARD,
  });

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter: excelFilter });
  };

  const onChangeExcelFilter = (date: Dayjs | null) => {
    if (!date?.year() || !date?.month()) return;
    setExcelFilter((pre) => ({ ...pre, month: date.month() + 1, year: date.year() }));
  };

  return (
    <div>
      <PageTitle title={tDashboard('title')} actions={<DownloadButton onClick={onDownloadExcel} />}></PageTitle>
      <DashboardSummary onChangeExcelFilter={onChangeExcelFilter} />
      <DashboardChartGroup />
    </div>
  );
};

export default page;
