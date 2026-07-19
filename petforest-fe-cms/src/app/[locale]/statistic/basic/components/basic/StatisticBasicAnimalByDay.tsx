import StackedColumnChart from '@/components/charts/StackedColumnChart';
import { StatisticBasicAnimalDTO } from '@/store/statistic/dto/statistic.dto';
import { COLORS } from '@/theme';
import { useTranslation } from 'react-i18next';
import StatisticCard from '../../../components/StatisticCard';
import StatisticTotal from '../../../components/StatisticTotal';

type StatisticBasicAnimalByDayProps = {
  data?: StatisticBasicAnimalDTO;
  onDownloadExcel: () => void;
};

const StatisticBasicAnimalByDay = ({ data, onDownloadExcel }: StatisticBasicAnimalByDayProps) => {
  const { t: tStatistic } = useTranslation('statistic');

  return (
    <StatisticCard className='pb-0 mt-6' title={tStatistic('basic.by_day')} onDownloadExcel={onDownloadExcel}>
      <div className='flex mt-6 justify-between  gap-4 flex-wrap'>
        <StatisticTotal title={tStatistic('basic.total_funeral')} value={data?.total} />
        {data?.totalByPetType.map((item) => (
          <StatisticTotal
            key={item.petType}
            title={item.petType}
            value={item.count}
            percent={item.percentage}
            color={COLORS.primary[1000]}
          />
        ))}
      </div>
      <StackedColumnChart categories={data?.data.categories || []} data={data?.data.data || []} names={data?.data.names || []} />
    </StatisticCard>
  );
};

export default StatisticBasicAnimalByDay;
