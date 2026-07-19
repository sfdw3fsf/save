import { StatisticBasicManagerDTO } from '@/store/statistic/dto/statistic.dto';
import { TableColumnsType } from 'antd';
import { TFunction } from 'i18next';

type StatisticBasicFuneralColumnsProps = {
  tStatistic: TFunction<'statistic', undefined>;
};

export const StatisticBasicFuneralColumns = ({
  tStatistic,
}: StatisticBasicFuneralColumnsProps): TableColumnsType<StatisticBasicManagerDTO> => {
  return [
    {
      title: tStatistic('basic.table.person'),
      dataIndex: 'managerName',
      width: 280,
    },
    {
      title: tStatistic('basic.table.funeral'),
      dataIndex: 'count',
      width: 280,
    },
    {
      title: tStatistic('basic.table.ratio'),
      dataIndex: 'percentage',
      width: 80,
    },
  ];
};
