import { api } from '@/config/http';
import { BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { DashboardChartREQ, DashboardReceivablesREQ, DashboardSummaryREQ } from './request/dashboard.request';
import { DashboardLineChartRESP, DashboardPieChartItemRESP, DashboardSummaryRESP } from './response/dashboard.response';

export const dashboardSummaryKeys = initKeys('dashboard-summary');

export const getDashboardSummaryApi = async (
  params?: DashboardSummaryREQ,
): Promise<BaseResponse<BaseResponse<DashboardSummaryRESP>>> => {
  return api.get(`dashboards/summaries`, { params });
};

export const dashboardReceivablesKeys = initKeys('dashboard-receivables');

export const getDashboardReceivablesAPI = async (
  params?: DashboardReceivablesREQ,
): Promise<BaseResponse<BaseResponse<number>>> => {
  return api.get(`dashboards/money-receivable`, { params });
};

export const dashboardPieChartKeys = initKeys('dashboard-pie-chart');

export const getDashboardPieChartApi = async (
  params: DashboardChartREQ,
): Promise<BaseResponse<BaseResponse<DashboardPieChartItemRESP[]>>> => {
  return api.get(`dashboards/pie-chart`, { params });
};

export const dashboardLineChartKeys = initKeys('dashboard-line-chart');

export const getDashboardLineChartApi = async (
  params: DashboardChartREQ,
): Promise<BaseResponse<BaseResponse<DashboardLineChartRESP>>> => {
  return api.get(`dashboards/line-chart`, { params });
};
