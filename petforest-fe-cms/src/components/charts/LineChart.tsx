import { COLORS } from '@/theme';
import { Legend } from '@/utils/types/legend.type';
import { ApexOptions } from 'apexcharts';
import { useMemo } from 'react';
import ReactApexChart from 'react-apexcharts';

type LineChartProps = {
  height?: number;
  width?: number;
  categories: string[];
  data: number[][] | number[];
  dotColor?: string[];
  lineColor?: string[];
  tooltip?: ApexOptions['tooltip'];
  legends?: Legend[];
};

const LineChart = ({ height, width, categories, data, dotColor, lineColor, tooltip, legends }: LineChartProps) => {
  const options: ApexOptions = useMemo(
    () => ({
      chart: {
        height: height ?? 288,
        type: 'line',
        toolbar: {
          tools: {
            download: false,
          },
        },
      },
      markers: {
        size: 6,
        shape: 'circle',
        colors: dotColor ?? [COLORS.primary[800]],
      },
      dataLabels: {
        enabled: false,
      },
      stroke: {
        curve: 'straight',
        colors: lineColor ?? [COLORS.primary[600]],
      },
      grid: {
        borderColor: COLORS.neutral[20],
      },
      xaxis: {
        categories: categories,
        labels: {
          rotate: -45,
          rotateAlways: true,
          style: {
            colors: COLORS.neutral[60],
          },
        },
        tickPlacement: 'between',
      },
      yaxis: {
        labels: {
          style: {
            colors: COLORS.neutral[60],
          },
        },
      },
      tooltip: {
        theme: 'dark',
        marker: {
          fillColors: dotColor ?? [COLORS.primary[800]],
        },
        ...tooltip,
      },
      legend: {
        show: false,
      },
    }),
    [height, dotColor, lineColor, categories, tooltip],
  );

  const series = data.map((item, index) => ({ name: legends ? legends[index].label : '값', data: item }));

  return <ReactApexChart options={options} series={series as any} type='line' height={height ?? 288} />;
};

export default LineChart;
