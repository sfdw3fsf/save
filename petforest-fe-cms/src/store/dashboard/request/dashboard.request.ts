export type DashboardSummaryREQ = DashboardSummaryYearREQ | DashboardSummaryMonthREQ;

export type DashboardSummaryYearREQ = {
  year: string;
  filterBy: 'year';
};

export type DashboardSummaryMonthREQ = {
  month: string;
  year: string;
  filterBy: 'month';
};

export type DashboardReceivablesREQ = {
  month: string;
  year: string;
};

export type DashboardChartREQ = {
  month: string;
  year: string;
};

export type DashboardExcelREQ = {
  month: number;
  year: number;
};
