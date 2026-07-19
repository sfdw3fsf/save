import LineChart from '@/components/charts/LineChart';
import { Legend } from '@/utils/types/legend.type';
import { useTranslation } from 'react-i18next';
import StatisticCard from '../../components/StatisticCard';
import StatisticLegendItem from '../../components/StatisticLegendItem';
import StatisticTotal from '../../components/StatisticTotal';
type StatisticRegionAreaDetailsProps = {
  data: number[][];
  categories: string[];
  legends: Legend[];
  total?: string;
  onDownloadExcel?: () => void;
};
const StatisticRegionAreaDetails = ({ data, categories, legends, total, onDownloadExcel }: StatisticRegionAreaDetailsProps) => {
  const { t: tStatistic } = useTranslation('statistic');

  return (
    <div className='flex-1'>
      <StatisticCard title={tStatistic('region.area_details')} onDownloadExcel={onDownloadExcel} isDownload={data.length > 0}>
        <div className='flex justify-between items-center mt-6'>
          <StatisticTotal title={tStatistic('total')} value={total} />
          <div className='flex flex-col gap-2'>
            {legends.length > 0 &&
              legends.map((item) => (
                <StatisticLegendItem key={item.value} label={item.label} value={item.value} color={item.color} />
              ))}
          </div>
        </div>
        <LineChart
          data={data}
          categories={categories}
          dotColor={legends.map((item) => item.color)}
          lineColor={legends.map((item) => item.color)}
          legends={legends}
        />
      </StatisticCard>
    </div>
  );
};

export default StatisticRegionAreaDetails;
