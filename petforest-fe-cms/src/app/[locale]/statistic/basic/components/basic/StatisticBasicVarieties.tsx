import ColumnChart from '@/components/charts/ColumnChart';
import { useSettingVarieties } from '@/hook/setting/useSettingVarieties';
import { StatisticBasicREQ } from '@/store/statistic/request/statistic.request';
import { Checkbox } from 'antd';
import { useTranslation } from 'react-i18next';
import StatisticCard from '../../../components/StatisticCard';
import StatisticTotal from '../../../components/StatisticTotal';

type StatisticBasicVarietiesProps = {
  total?: string;
  data: number[];
  categories: (string | string[])[];
  filter: StatisticBasicREQ;
  onFilterChange: (filter: StatisticBasicREQ) => void;
  onDownloadExcel?: () => void;
};

const StatisticBasicVarieties = ({
  categories,
  data,
  total = '0',
  filter,
  onFilterChange,
  onDownloadExcel,
}: StatisticBasicVarietiesProps) => {
  const { t: tStatistic } = useTranslation('statistic');

  const { petTypeOptions } = useSettingVarieties({});
  return (
    <div className='flex-1'>
      <StatisticCard title={tStatistic('basic.varieties')} onDownloadExcel={onDownloadExcel}>
        <div className='mt-6 flex items-end justify-between'>
          <StatisticTotal title={tStatistic('basic.total_funeral')} value={total} />
          <div className='flex gap-6 flex-wrap max-w-[80%]'>
            {petTypeOptions?.map((item) => (
              <Checkbox
                key={item.value}
                checked={filter?.petTypeIds?.includes(item.value)}
                onClick={() => {
                  if (filter?.petTypeIds?.includes(item.value)) {
                    onFilterChange({
                      ...filter,
                      petTypeIds: filter?.petTypeIds?.filter((id) => id !== item.value),
                    });
                  } else {
                    onFilterChange({
                      ...filter,
                      petTypeIds: filter?.petTypeIds ? [...(filter?.petTypeIds || []), item.value] : [item.value],
                    });
                  }
                }}>
                {item.label}
              </Checkbox>
            ))}
          </div>
        </div>

        <div className='mt-6'>
          <ColumnChart categories={categories} data={data} />
        </div>
      </StatisticCard>
    </div>
  );
};

export default StatisticBasicVarieties;
