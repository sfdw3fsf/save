import { COLORS } from '@/theme';
import { ApexOptions } from 'apexcharts';
import { useState } from 'react';
import ReactApexChart from 'react-apexcharts';

type ColumnChartProps = {
  height?: number;
  width?: number;
  categories: (string | string[])[];
  data: number[];
  unit?: string;
  names?: string[];
};

const ColumnChart = ({ height, width, categories, data, unit }: ColumnChartProps) => {
  const [dataIndex, setDataIndex] = useState<number | null>(null);
  const options: ApexOptions = {
    colors: [
      function ({ dataPointIndex }: any) {
        if (dataPointIndex == dataIndex) {
          return COLORS.green.emarald;
        } else {
          return COLORS.neutral[60];
        }
      },
    ],
    tooltip: {
      enabled: true,
      custom: function ({ series, seriesIndex, dataPointIndex, w }) {
        const category = categories[dataPointIndex];
        const value = series[seriesIndex][dataPointIndex];
        return `<div style="padding: 10px; background-color: ${COLORS.white}; border-radius: 5px; color: ${COLORS.black}; font-size: 14px; ">
                    <strong>${category}</strong> : ${value.toLocaleString()} ${unit || ''}
                  </div>`;
      },
    },
    chart: {
      height: height ?? 236,
      type: 'bar',
      events: {
        dataPointSelection: (event, chartContext, config) => {
          setDataIndex(config.dataPointIndex);
        },
      },
      animations: {
        enabled: false,
      },
      toolbar: {
        tools: {
          download: false,
        },
      },
    },
    plotOptions: {
      bar: {
        dataLabels: {
          position: 'top',
        },
      },
    },
    dataLabels: {
      enabled: true,
      offsetY: -20,
      style: {
        fontSize: '12px',
        colors: [COLORS.black],
      },
      formatter: function (val) {
        return Number(val).toLocaleString();
      },
    },
    xaxis: {
      categories: categories,
      position: 'bottom',
      tickAmount: categories.length,
      axisBorder: {
        show: false,
      },
      axisTicks: {
        show: false,
      },
      tooltip: {
        enabled: true,
      },
      labels: {
        rotate: 0,
        style: {
          colors: COLORS.neutral[60],
        },
      },
    },

    responsive: [
      {
        breakpoint: 480,
        options: {
          xaxis: {
            labels: {
              rotate: -45,
            },
          },
        },
      },
    ],

    yaxis: {
      axisBorder: {
        show: false,
      },
      axisTicks: {
        show: false,
      },
      labels: {
        style: {
          colors: COLORS.neutral[60],
        },
        formatter: (val: number) => `${val}${unit ?? ''}`,
      },
    },
    grid: {
      borderColor: COLORS.neutral[20],
    },
  };

  const series = [
    {
      name: '값',
      data: data,
    },
  ];

  return (
    <div>
      <ReactApexChart key={dataIndex} options={options} series={series} type='bar' height={height ?? 236} width={width} />
    </div>
  );
};

export default ColumnChart;
