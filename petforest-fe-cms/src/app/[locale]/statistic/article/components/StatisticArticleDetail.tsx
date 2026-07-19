import PieChart, { PieChartCategories } from '@/components/charts/PieChart';
import CustomSelect from '@/components/common/CustomSelect';
import { useMaterial } from '@/hook/material/useMaterial';
import { StatisticArticleREQ } from '@/store/statistic/request/statistic.request';
import { Legend } from '@/utils/types/legend.type';
import { useTranslation } from 'react-i18next';
import StatisticCard from '../../components/StatisticCard';
import StatisticLegendItem from '../../components/StatisticLegendItem';
import StatisticTotal from '../../components/StatisticTotal';

type StatisticArticleDetailProps = {
  data: number[];
  categories: PieChartCategories;
  legends: Legend[];
  total?: string;
  onChangeMaterialType: (type: string) => void;
  filter: StatisticArticleREQ;
  onDownloadExcel?: () => void;
};
const StatisticArticleDetail = ({
  data,
  categories,
  legends,
  total,
  onChangeMaterialType,
  filter,
  onDownloadExcel,
}: StatisticArticleDetailProps) => {
  const { t: tStatistic } = useTranslation('statistic');
  const { materialTypeOptions } = useMaterial();

  return (
    <div className='flex-1 flex'>
      <StatisticCard title={tStatistic('article.detail_item')} onDownloadExcel={onDownloadExcel}>
        <div className='mt-6'>
          <CustomSelect
            value={filter.type}
            options={materialTypeOptions}
            className='flex-1'
            onChange={(value) => {
              onChangeMaterialType(value);
            }}
          />
        </div>
        <div className='flex mt-6 gap-[40px]'>
          <PieChart data={data} categories={categories} />
          <div className='flex flex-col justify-between '>
            <StatisticTotal title={filter.type || ''} value={total} type='large' />
            <div className='flex flex-col gap-2'>
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

export default StatisticArticleDetail;
