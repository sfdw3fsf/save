import PieChart, { PieChartCategories } from '@/components/charts/PieChart';
import CustomText from '@/components/common/Text';
import { Legend } from '@/utils/types/legend.type';
import { useTranslation } from 'react-i18next';
import StatisticCard from '../../../components/StatisticCard';
import StatisticLegendItem from '../../../components/StatisticLegendItem';
import StatisticTotal from '../../../components/StatisticTotal';

type StatisticBasicTransportProps = {
  data: number[];
  categories: PieChartCategories;
  legends: Legend[];
  totalTransport?: number | string;
  total?: number | string;
  onDownloadExcel?: () => void;
};

const StatisticBasicTransport = ({
  data,
  categories,
  legends,
  totalTransport = 0,
  total = 0,
  onDownloadExcel,
}: StatisticBasicTransportProps) => {
  const { t: tStatistic } = useTranslation('statistic');

  return (
    <div className='flex-1 flex'>
      <StatisticCard title={tStatistic('basic.transport_title')} onDownloadExcel={onDownloadExcel}>
        <div className='flex mt-6 gap-[40px] max-md:flex-col-reverse'>
          <PieChart data={data} categories={categories} />
          <div className='flex flex-col justify-between'>
            <StatisticTotal title={tStatistic('basic.total_funeral')} value={total} type='large' />

            <div className='flex flex-col max-md:mt-6'>
              <CustomText variant='label-1-normal'>{tStatistic('basic.total_transport')}</CustomText>
              <CustomText variant='title-3' className='!text-brand-forest'>
                {totalTransport}
                {tStatistic('cases')}
              </CustomText>
            </div>

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

export default StatisticBasicTransport;
