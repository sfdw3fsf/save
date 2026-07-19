import { DashboardPieDTO } from '@/store/dashboard/dto/dashboard.dto';
import { COLORS } from '@/theme';
import { isNil } from 'lodash';
import { numericFormatter } from 'react-number-format';
import { CHART_COLORS } from '../constants/post/chart-color.constant';
import { BRANCH_ENUMS } from '../enums/common.enums';

export const helperFormatChartNumericData = (value?: number | null, fallback: string = '-') => {
  if (isNil(value)) return fallback;
  return numericFormatter(value.toString(), { thousandSeparator: true, decimalScale: 3 });
};

const helperConvertHexToRgb = (hex?: string) => {
  if (!hex || !/^#[a-fA-F0-9]{6}$/.test(hex)) return;
  hex = hex.replace('#', '');
  const r = parseInt(hex.substring(0, 2), 16);
  const g = parseInt(hex.substring(2, 4), 16);
  const b = parseInt(hex.substring(4, 6), 16);
  return { r, g, b };
};

export const helperGeneratePies = (
  colors: string[],
  branch: BRANCH_ENUMS | null,
  data: { label: string; value: number }[],
): DashboardPieDTO[] => {
  const validColors =
    data.length > colors.length ? helperGenerateColors(data.length, { start: colors.at(0), end: colors.at(-1) }) : colors;
  const chartColor = branch ? CHART_COLORS[branch] : null;

  return data.map((item, index) => {
    return {
      label: item.label,
      value: item.value,
      color:
        chartColor && !!chartColor[item.label as keyof typeof chartColor]
          ? chartColor[item.label as keyof typeof chartColor]
          : validColors[index],
      textColor: helperGenerateTextColor(
        chartColor && !!chartColor[item.label as keyof typeof chartColor]
          ? chartColor[item.label as keyof typeof chartColor]
          : validColors[index],
      ),
    };
  });
};

const helperGenerateColors = (steps: number, scale: { start?: string; end?: string }) => {
  const validScale = scale.start && scale.end && scale.start !== scale.end ? scale : { start: COLORS.black, end: COLORS.white };

  const startRgb = helperConvertHexToRgb(validScale.start);
  const endRgb = helperConvertHexToRgb(validScale.end);

  if (!startRgb || !endRgb) return [];

  const colors = [];

  for (let i = 0; i < steps; i++) {
    const factor = i / (steps - 1);

    const r = Math.round(startRgb.r + factor * (endRgb.r - startRgb.r));
    const g = Math.round(startRgb.g + factor * (endRgb.g - startRgb.g));
    const b = Math.round(startRgb.b + factor * (endRgb.b - startRgb.b));

    const color = `#${((1 << 24) | (r << 16) | (g << 8) | b).toString(16).slice(1).toUpperCase()}`;
    colors.push(color);
  }

  return colors;
};

const helperGenerateTextColor = (color: { r: number; g: number; b: number } | string) => {
  const validColor = typeof color === 'string' ? helperConvertHexToRgb(color) : color;
  if (!validColor) return COLORS.black;
  const { r, g, b } = validColor;
  const luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;
  return luminance > 128 ? COLORS.black : COLORS.white;
};
