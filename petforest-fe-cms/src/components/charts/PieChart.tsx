import { COLORS } from '@/theme';
import { Empty } from 'antd';
import { ApexOptions } from 'apexcharts';
import ReactApexChart from 'react-apexcharts';

export type PieChartCategories = { label: string; color: string; textColor?: string }[];

type PieChartProps = {
  height?: number;
  width?: number;
  categories: PieChartCategories;
  data: number[];
  showEmpty?: boolean;
};

const PieChart = ({ height, categories, data, showEmpty = false, width }: PieChartProps) => {
  const series = data;
  const options: ApexOptions = {
    chart: {
      type: 'pie',
    },
    labels: categories.map((item) => item.label),
    stroke: {
      width: 0,
    },
    dataLabels: {
      enabled: true,

      style: {
        fontSize: '14px',
        fontWeight: 600,
        colors: categories.map((item) => item.textColor ?? COLORS.primary[1200]),
      },
      dropShadow: {
        enabled: false,
      },
    },
    plotOptions: {
      pie: {
        dataLabels: {
          offset: -30,
        },
      },
    },
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: {
            width: 262.22,
          },
          legend: {
            position: 'bottom',
          },
        },
      },
    ],
    colors: categories.map((item) => item.color),
    legend: {
      show: false,
    },
  };

  if (showEmpty && !series.length) {
    return <Empty className='block mx-auto' image={Empty.PRESENTED_IMAGE_SIMPLE} />;
  }

  return <ReactApexChart options={options} series={series} type='pie' height={height ?? 288} width={width} />;
};

export default PieChart;
