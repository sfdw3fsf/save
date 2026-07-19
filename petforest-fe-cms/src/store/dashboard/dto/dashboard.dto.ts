import { BRANCH_ENUMS } from '@/utils/enums/common.enums';

export type DashboardPieDTO = {
  label: string;
  color: string;
  textColor: string;
  value: number;
};

export type DashboardPieChartItemDTO = {
  branch: BRANCH_ENUMS | null;
  branchId: number;
  branchName: string;
  branchColor: string;
  branchChartColor: Record<number, string>;
  totalFuneral: number;
  averageFuneralByDay: number;
  totalSalesAmount: number;
  pieChartData: DashboardPieDTO[];
  funeralSales: number;
  funeralSalesPercent: number;
  bongandangSales: number;
  bongandangSalesPercent: number;
  otherSales: number;
  otherSalesPercent: number;
  receivable: number;
  receivablePercent: number;
};

export type DashboardLineChartItemDTO = {
  branchId: number;
  branchName: string;
  color: string;
  lineDataSales: number[];
  lineDataFuneral: number[];
  branch: BRANCH_ENUMS | null;
};

export type DashboardLineChartDTO = {
  lineCategories: string[];
  totalFuneralData: number;
  totalSalesData: number;
  lineData: DashboardLineChartItemDTO[];
};
