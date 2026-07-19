import { COLORS } from '@/theme';
import { ApexOptions } from 'apexcharts';
import ReactApexChart from 'react-apexcharts';

type StackedColumnChartProps = {
  height?: number;
  width?: number;
  categories: string[];
  data: number[][];
  names: string[];
};

const StackedColumnChart = ({ height, width, categories, data, names }: StackedColumnChartProps) => {
  const options: ApexOptions = {
    chart: {
      type: 'bar',
      height: height ?? 286,
      stacked: true,
      toolbar: {
        show: true,
        tools: {
          download: false,
        },
      },
      zoom: {
        enabled: false,
      },
    },
    dataLabels: {
      enabled: false,
    },
    responsive: [
      {
        breakpoint: 480,
        options: {
          legend: {
            position: 'bottom',
            offsetX: -10,
            offsetY: 0,
          },
        },
      },
    ],
    plotOptions: {
      bar: {
        horizontal: false,
        dataLabels: {
          total: {
            enabled: true,
            style: {
              fontSize: '12px',
              fontWeight: 400,
              color: COLORS.neutral[100],
            },
          },
        },
      },
    },
    xaxis: {
      labels: {
        rotate: -45,
        rotateAlways: true,
        style: {
          colors: COLORS.neutral[60],
        },
      },
      categories: categories,
    },
    yaxis: {
      labels: {
        style: {
          colors: COLORS.neutral[60],
        },
      },
    },
    legend: {
      show: false,
    },
    fill: {
      opacity: 1,
    },
    grid: {
      borderColor: COLORS.neutral[20],
    },
  };

  const series: ApexAxisChartSeries | ApexNonAxisChartSeries = names.map((_, index) => ({
    name: names[index],
    data: data[index],
    color: COLORS.primary[((10 - index) * 100) as unknown as keyof typeof COLORS.primary],
  }));

  return (
    <div>
      {series.length > 0 && <ReactApexChart options={options} series={series} type='bar' height={height ?? 286} width={width} />}
    </div>
  );
};

export default StackedColumnChart;
