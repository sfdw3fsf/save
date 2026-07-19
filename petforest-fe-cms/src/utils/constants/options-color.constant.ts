import { COLORS } from '@/theme';
import { BRANCH_ENUMS } from '../enums/common.enums';

export const branchColor = {
  [BRANCH_ENUMS.GYEONGGI_GWANGJU]: COLORS.branch['gyeonggi-gwangju'],
  [BRANCH_ENUMS.GIMPO]: COLORS.branch.gimpo,
  [BRANCH_ENUMS.NAMYANGJU]: COLORS.branch.namyangju,
  ['default']: COLORS.branch.default,
};

export const branchChartColor = {
  [BRANCH_ENUMS.GYEONGGI_GWANGJU]: COLORS.chart['gyeonggi-gwangju'],
  [BRANCH_ENUMS.GIMPO]: COLORS.chart.gimpo,
  [BRANCH_ENUMS.NAMYANGJU]: COLORS.chart.namyangju,
  ['default']: COLORS.chart.default,
};
