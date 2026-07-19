export type DashboardSummaryRESP = {
  totalFuneral: number;
  averageFuneralByDay: number;
  totalSales: number;
  averageSalesByDay: number;
};

export type DashboardLineChartItemRESP = {
  branchId: number;
  branchName: string;
  color: string | null;
  lineDataSales: number[];
  lineDataFuneral: number[];
};

export type DashboardLineChartRESP = {
  lineCategories: string[];
  lineData: DashboardLineChartItemRESP[];
};

export type DashboardPieChartItemRESP = {
  branchId: number;
  branchName: string;
  branchColor: string | null;
  totalFuneral: number;
  averageFuneralByDay: number;
  totalSalesAmount: number;
  pieChartData: { label: string; value: number }[] | null;
  funeralSales: number;
  funeralSalesPercent: number;
  bongandangSales: number;
  bongandangSalesPercent: number;
  otherSales: number;
  otherSalesPercent: number;
  receivable: number;
  receivablePercent: number;
};
