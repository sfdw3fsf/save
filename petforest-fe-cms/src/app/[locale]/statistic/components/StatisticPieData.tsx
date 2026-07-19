import PieChart, { PieChartCategories } from '@/components/charts/PieChart';
import { Legend } from '@/utils/types/legend.type';
import StatisticCard from './StatisticCard';
import StatisticLegendItem from './StatisticLegendItem';
import StatisticTotal from './StatisticTotal';

type StatisticPieDataProps = {
  data: number[];
  categories: PieChartCategories;
  legends: Legend[];
  title: string;
  total?: number | string;
  totalTitle?: string;
  totalUnit?: 'won' | 'cases';
  onDownloadExcel?: () => void;
};

const StatisticPieData = ({
  data,
  categories,
  legends,
  title,
  total = 0,
  totalTitle,
  totalUnit = 'cases',
  onDownloadExcel,
}: StatisticPieDataProps) => {
  return (
    <div className='flex-1 flex'>
      <StatisticCard title={title} onDownloadExcel={onDownloadExcel}>
        <div className='flex mt-6 gap-[40px] max-md:flex-col-reverse'>
          <PieChart data={data} categories={categories} />
          <div className='flex flex-col justify-between ga'>
            <StatisticTotal title={totalTitle ?? 'Total'} value={total} type='large' totalUnit={totalUnit} />
            <div className='flex flex-col gap-2 max-md:mt-6'>
              {legends.map((item) => (
                <StatisticLegendItem key={item.value} label={item.label} value={item.value} color={item.color} />
              ))}
            </div>
          </div>
        </div>
      </StatisticCard>
    </div>
  );
};

export default StatisticPieData;
