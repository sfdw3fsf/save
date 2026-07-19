import { branchChartColor, branchColor } from '@/utils/constants/options-color.constant';
import { BRANCH_ENUMS } from '@/utils/enums/common.enums';
import { helperGeneratePies } from '@/utils/helpers/chart.helper';
import { helperGetBranchEnum } from '@/utils/helpers/common.helper';
import { DashboardLineChartDTO, DashboardLineChartItemDTO, DashboardPieChartItemDTO } from './dto/dashboard.dto';
import { DashboardLineChartItemRESP, DashboardLineChartRESP, DashboardPieChartItemRESP } from './response/dashboard.response';

export const dashboardPieChartItemRespToDto = (resp: DashboardPieChartItemRESP): DashboardPieChartItemDTO => {
  const dtoBranch: BRANCH_ENUMS | null = helperGetBranchEnum(resp.branchId);

  const dtoBranchColor =
    resp.branchColor ??
    ((branch: BRANCH_ENUMS | null) => {
      if (!branch || !Object.values(BRANCH_ENUMS).includes(branch)) return branchColor.default;
      return branchColor[branch];
    })(dtoBranch);

  const dtoBranchChartColor = ((branch: BRANCH_ENUMS | null) => {
    if (!branch || !Object.values(BRANCH_ENUMS).includes(branch)) return branchChartColor.default;
    return branchChartColor[branch];
  })(dtoBranch);

  const dtoPieChartData = helperGeneratePies(Object.values(dtoBranchChartColor), dtoBranch, resp.pieChartData ?? []);

  const dto: DashboardPieChartItemDTO = {
    ...resp,
    branch: dtoBranch,
    branchColor: dtoBranchColor,
    branchChartColor: dtoBranchChartColor,
    pieChartData: dtoPieChartData,
  };
  return dto;
};

export const dashboardLineChartItemRespToDto = (resp: DashboardLineChartItemRESP): DashboardLineChartItemDTO => {
  const dtoBranch: BRANCH_ENUMS | null = helperGetBranchEnum(resp.branchId);

  const dtoColor =
    resp.color ??
    ((branch: BRANCH_ENUMS | null) => {
      if (!branch || !Object.values(BRANCH_ENUMS).includes(branch)) return branchColor.default;
      return branchColor[branch];
    })(dtoBranch);

  const dto: DashboardLineChartItemDTO = {
    ...resp,
    branch: dtoBranch,
    color: dtoColor,
  };
  return dto;
};

export const dashboardLineChartRespToDto = (resp: DashboardLineChartRESP): DashboardLineChartDTO => {
  const lineData = resp.lineData.sort((a, b) => a.branchId - b.branchId);
  const dtoLineData = lineData.map(dashboardLineChartItemRespToDto);

  const dtoTotalFuneralData = lineData.reduce(
    (totalAmount, amount) =>
      totalAmount + amount.lineDataFuneral.reduce((itemTotalAmount, itemAmount) => itemTotalAmount + itemAmount, 0),
    0,
  );
  const dtoTotalSalesData = lineData.reduce(
    (totalAmount, amount) =>
      totalAmount + amount.lineDataSales.reduce((itemTotalAmount, itemAmount) => itemTotalAmount + itemAmount, 0),
    0,
  );

  const dto: DashboardLineChartDTO = {
    ...resp,
    lineData: dtoLineData,
    totalFuneralData: dtoTotalFuneralData,
    totalSalesData: dtoTotalSalesData,
  };
  return dto;
};
