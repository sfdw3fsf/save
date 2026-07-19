import LineChart from '@/components/charts/LineChart';
import { COLORS } from '@/theme';
import { useTranslation } from 'react-i18next';
import StatisticCard from '../../../components/StatisticCard';
import StatisticTotal from '../../../components/StatisticTotal';

type StatisticBasicConnectedHospitalProps = {
  data: number[];
  categories: string[];
  total?: number | string;
  totalConnections?: number | string;
  onDownloadExcel?: () => void;
};

const StatisticBasicConnectedHospital = ({
  data,
  categories,
  total = 0,
  totalConnections = 0,
  onDownloadExcel,
}: StatisticBasicConnectedHospitalProps) => {
  const { t: tStatistic } = useTranslation('statistic');

  return (
    <div className='flex-1'>
      <StatisticCard title={tStatistic('basic.connected_hospital')} onDownloadExcel={onDownloadExcel}>
        <div className='mt-6 flex items-center'>
          <div className='flex-1'>
            <StatisticTotal title={tStatistic('basic.total_funeral')} value={total} />
          </div>
          <div className='flex-1'>
            <StatisticTotal
              totalColor={COLORS.brand.forest}
              title={tStatistic('basic.total_connected_hospital')}
              value={totalConnections}
            />
          </div>
        </div>

        <div className='mt-6'>
          <LineChart data={[data]} categories={categories} dotColor={[COLORS.primary[800]]} lineColor={[COLORS.primary[600]]} />
        </div>
      </StatisticCard>
    </div>
  );
};

export default StatisticBasicConnectedHospital;
